package org.travis4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.travis4j.api.BuildsResource;
import org.travis4j.api.JobsResource;
import org.travis4j.api.LogsResource;
import org.travis4j.api.RepositoriesResource;
import org.travis4j.api.Travis;
import org.travis4j.api.UsersResource;
import org.travis4j.model.Build;
import org.travis4j.model.EntityFactory;
import org.travis4j.model.Job;
import org.travis4j.model.Log;
import org.travis4j.model.PageIterator;
import org.travis4j.model.Repository;
import org.travis4j.model.User;
import org.travis4j.model.json.JsonEntityFactory;
import org.travis4j.model.page.BuildsPageIterator;
import org.travis4j.model.request.ListBuildsRequest;
import org.travis4j.rest.JsonResponse;
import org.travis4j.rest.SimpleRestClient;

/**
 * TravisClient.
 */
public class TravisClient implements Closeable, Travis,
        RepositoriesResource,
        UsersResource,
        BuildsResource,
        LogsResource,
        JobsResource {

    private static final Logger LOG = LoggerFactory.getLogger(TravisClient.class);

    public static final URI DEFAULT_API = URI.create("https://api.travis-ci.org/");

    private URI api;
    private String token;
    private SimpleRestClient client;
    private EntityFactory factory;

    public TravisClient() {
        this(null);
    }

    public TravisClient(String token) {
        this.api = DEFAULT_API;
        this.token = token;
        this.client = new SimpleRestClient(createHttpClient(), api);
        this.factory = new JsonEntityFactory();
    }

    @Override
    public RepositoriesResource repositories() {
        return this;
    }

    @Override
    public UsersResource users() {
        return this;
    }

    @Override
    public BuildsResource builds() {
        return this;
    }

    @Override
    public JobsResource jobs() {
        return this;
    }

    @Override
    public LogsResource logs() {
        return this;
    }

    @Override
    public Repository getRepository(long id) {
        JsonResponse response = client.query("repos/" + id);
        return factory.createRepository(response);
    }

    @Override
    public Repository getRepository(String ownerName, String repositoryName) {
        JsonResponse response = client.query(String.format("repos/%s/%s", ownerName, repositoryName));
        return factory.createRepository(response);
    }

    @Override
    public List<Repository> getRepositories(String slug) {
        JsonResponse response = client.query(String.format("repos/%s", slug));
        System.out.println(response.getJson());
        return factory.createRepositoryList(response);
    }

    @Override
    public List<Repository> getRepositories(List<Long> ids) {
        JsonResponse response = client.get("repos").addParameter("ids", ",", ids).execute();
        return factory.createRepositoryList(response);
    }

    @Override
    public List<User> getUsers() {
        JsonResponse response = client.query("users");
        return factory.createUserList(response);
    }

    @Override
    public User getUser(long id) {
        JsonResponse response = client.query("users/" + id);
        return factory.createUser(response);
    }

    @Override
    public Build getBuild(long buildId) {
        JsonResponse response = client.query("builds/" + buildId);
        return factory.createBuild(response);
    }

    @Override
    public List<Build> getBuilds(long repositoryId) {
        return getBuilds(new ListBuildsRequest().setRepositoryId(repositoryId));
    }

    @Override
    public List<Build> getBuilds(long repositoryId, long offset) {
        return getBuilds(new ListBuildsRequest().setRepositoryId(repositoryId).setOffset(offset));
    }

    @Override
    public List<Build> getBuilds(ListBuildsRequest request) {
        // TODO validate
        JsonResponse response = client.get("builds")
                .addOptionalParameter("ids", ",", request.getIds())
                .addOptionalParameter("repository_id", request.getRepositoryId())
                .addOptionalParameter("slug", request.getSlug())
                .addOptionalParameter("number", request.getNumber())
                .addOptionalParameter("event_type", request.getEventType())
                .addOptionalParameter("after_number", request.getOffset())
                .execute();

        return factory.createBuildList(response);
    }

    @Override
    public Job getJob(long jobId) {
        JsonResponse response = client.query("jobs/" + jobId);
        return factory.createJob(response);
    }

    @Override
    public List<Job> getJobsOfBuild(long buildId) {
        JsonResponse execute = client.query("builds/" + buildId);
        return factory.createJobList(execute);
    }

    @Override
    public PageIterator<Build> getAllBuilds(long repositoryId) {
        return new BuildsPageIterator(repositoryId, this);
    }

    @Override
    public Log getLog(long logId) {
        JsonResponse log = client.query("logs/" + logId);

        long jobId = log.getJson().getJSONObject("log").getLong("job_id");

        // FIXME there must be a better way, but the travis API doesn't seem to work as documented
        URI logResource = URI.create("https://s3.amazonaws.com/archive.travis-ci.org/jobs/" + jobId + "/log.txt");

        LOG.debug("Got log from Travis {}, now fetching log from s3 for job {}", log, jobId);
        HttpUriRequest logRequest = RequestBuilder.get(logResource)
                .addHeader("Accept", "text/plain")
                .addHeader("Content-Type", "text/plain")
                .build();

        HttpResponse response;
        try {
            response = client.getHttpClient().execute(logRequest);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        LOG.debug("Got response for jobId {}, getting entity", jobId);
        HttpEntity body = null;
        if (response.getStatusLine().getStatusCode() == 200) {
            body = response.getEntity();
        } else {
            LOG.warn("Couldn't get log body: {}", response.getStatusLine());
        }
        LOG.debug("Done getting log {}, creating log object", logId);
        return factory.createLog(log, body);
    }

    @Override
    public void close() throws IOException {
        LOG.info("Closing TravisClient");
        client.close();
    }

    private HttpClient createHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultHeaders(createDefaultHeaders())
                .build();
    }

    private List<Header> createDefaultHeaders() {
        List<Header> headers = new ArrayList<>();

        headers.add(new BasicHeader("Accept", "application/vnd.travis-ci.2+json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        if (token == null || token.isEmpty()) {
            LOG.info("No Authorization token provided, won't be able to access all resources");
        } else {
            headers.add(new BasicHeader("Authorization", "token \"" + token + "\""));
        }

        return headers;
    }
}
