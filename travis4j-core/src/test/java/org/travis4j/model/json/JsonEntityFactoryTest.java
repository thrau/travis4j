package org.travis4j.model.json;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.travis4j.model.Build;
import org.travis4j.model.EntityFactory;
import org.travis4j.model.Repository;
import org.travis4j.testing.JsonResources;
import org.travis4j.testing.MockJsonResponse;

/**
 * JsonEntityFactoryTest.
 * 
 * TODO these tests aren't really good, they test too much in a crude way
 */
public class JsonEntityFactoryTest {

    private EntityFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new JsonEntityFactory();
    }

    @Test
    public void createRepository_createsObjectCorrectly() throws Exception {
        Repository repository = factory.createRepository(new MockJsonResponse(JsonResources.read("repository.json")));

        assertEquals(1889385, repository.getId(), 0);
        assertEquals("thrau/jarchivelib", repository.getSlug());
        assertEquals("A simple archiving and compression library for Java", repository.getDescription());
        assertEquals("Java", repository.getGithubLanguage());
        assertEquals(true, repository.getActive());

        assertEquals(92561340, repository.getLastBuildId(), 0);
        assertEquals(65, repository.getLastBuildNumber(), 0);
        assertEquals(378, repository.getLastBuildDuration(), 0);
        assertEquals(Instant.parse("2015-11-22T15:03:57Z"), repository.getLastBuildStartedAt());
        assertEquals(Instant.parse("2015-11-22T15:09:22Z"), repository.getLastBuildFinishedAt());
        assertEquals("passed", repository.getLastBuildState());
        assertEquals(null, repository.getLastBuildLanguage());
    }

    @Test
    public void createBuildList_createsBuildListCorrectly() throws Exception {
        JSONObject json = JsonResources.read("builds.json");

        List<Build> list = factory.createBuildList(new MockJsonResponse(json));
        Iterator<Build> builds = list.iterator();

        assertEquals(2, list.size());

        Build build;

        build = builds.next();
        // base data
        assertEquals(92561340, build.getId(), 0);
        assertEquals(378, build.getDuration(), 0);
        assertEquals(65, build.getNumber(), 0);
        assertEquals(Instant.parse("2015-11-22T15:03:57Z"), build.getStartedAt());
        assertEquals(Instant.parse("2015-11-22T15:09:22Z"), build.getFinishedAt());
        assertEquals("passed", build.getState());
        assertEquals("push", build.getEventType());
        assertEquals(false, build.getPullRequest());
        assertEquals(null, build.getPullRequestNumber());
        assertEquals(null, build.getPullRequestTitle());

        // job ids
        assertEquals(2, build.getJobIds().size());
        assertThat(build.getJobIds(), hasItems(92561342L, 92561344L));

        // commit data
        assertNotNull(build.getCommit());
        assertEquals(26318654, build.getCommitId(), 0);
        assertEquals(26318654, build.getCommit().getId(), 0);
        assertEquals("Thomas Rausch", build.getCommit().getAuthorName());
        assertEquals("Thomas Rausch", build.getCommit().getCommitterName());
        assertEquals("thomas@rauschig.org", build.getCommit().getAuthorEmail());
        assertEquals("thomas@rauschig.org", build.getCommit().getCommitterEmail());
        assertEquals(Instant.parse("2015-11-22T15:03:04Z"), build.getCommit().getCommittedAt());
        assertEquals("dc8bf2206e1ddba304502abb2aad97a0d8e72f59", build.getCommit().getSha());
        assertEquals("master", build.getCommit().getBranch());
        assertEquals("https://github.com/thrau/jarchivelib/compare/563b7cdf5266...dc8bf2206e1d",
                build.getCommit().getCompareUrl());

        // rudimentary check of the second entry
        build = builds.next();
        assertEquals(true, build.getPullRequest());
        assertEquals(46, build.getPullRequestNumber(), 0);
        assertNotNull(build.getCommit());
        assertEquals(26281307, build.getCommitId(), 0);
        assertEquals(26281307, build.getCommit().getId(), 0);
    }

}
