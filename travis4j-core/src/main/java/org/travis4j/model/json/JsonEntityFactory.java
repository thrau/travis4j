package org.travis4j.model.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.travis4j.model.Build;
import org.travis4j.model.EntityFactory;
import org.travis4j.model.Repository;
import org.travis4j.model.User;
import org.travis4j.rest.JsonResponse;

public class JsonEntityFactory implements EntityFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JsonEntityFactory.class);

    @Override
    public Repository createRepository(JsonResponse response) {
        return createIfExists(response, "repo", RepositoryJsonObject::new);
    }

    @Override
    public User createUser(JsonResponse response) {
        return createIfExists(response, "user", UserJsonObject::new);
    }

    @Override
    public List<User> createUserList(JsonResponse response) {
        return Collections.singletonList(createUser(response)); // FIXME
    }

    @Override
    public Build createBuild(JsonResponse response) {
        return createIfExists(response, "build", BuildJsonObject::new);
    }

    @Override
    public List<Repository> createRepositoryList(JsonResponse response) {
        return createListIfExists(response, "repos", RepositoryJsonObject::new);
    }

    @Override
    public List<Build> createBuildList(JsonResponse response) {
        return createIfExists(response, json -> {
            JSONArray buildsArray = json.getJSONArray("builds");
            JSONArray commitsArray = json.getJSONArray("commits");

            List<Build> list = new ArrayList<>(buildsArray.length());

            for (int i = 0; i < buildsArray.length(); i++) {
                BuildJsonObject build = new BuildJsonObject(buildsArray.getJSONObject(i));
                build.setCommit(commitsArray.optJSONObject(i));

                list.add(build);
            }

            return list;
        });
    }

    private <T> T createIfExists(JsonResponse response, Function<JSONObject, T> factory) {
        return read(response).map(factory).orElse(null);
    }

    private <T> T createIfExists(JsonResponse response, String key, Function<JSONObject, T> factory) {
        return read(response)
                .map(obj -> obj.getJSONObject(key))
                .map(factory)
                .orElse(null);
    }

    private <T> List<T> createListIfExists(JsonResponse response, String key, Function<JSONObject, T> factory) {
        return read(response)
                .map(obj -> obj.getJSONArray(key)) // may throw an exception, but optJSON... may swallow errors
                .map(arr -> map(arr, factory))
                .orElse(Collections.emptyList());
    }

    private <T> List<T> map(JSONArray array, Function<JSONObject, T> mapper) {
        List<T> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            list.add(mapper.apply(array.getJSONObject(i)));
        }
        return list;
    }

    private Optional<JSONObject> read(JsonResponse response) {
        return response.isNotFound() ? Optional.empty() : Optional.of(response.getJson());
    }

}
