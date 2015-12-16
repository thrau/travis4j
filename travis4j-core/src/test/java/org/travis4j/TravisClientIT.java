package org.travis4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.travis4j.model.Build;
import org.travis4j.model.Repository;

/**
 * Integration test that calls the actual travis API.
 */
public class TravisClientIT {

    private TravisClient travis;

    @Before
    public void setUp() throws Exception {
        travis = new TravisClient();
    }

    @After
    public void tearDown() throws Exception {
        travis.close();
    }

    @Test
    public void getBuild_byId_returnsCorrectData() throws Exception {
        Build build = travis.getBuild(92561340);

        assertEquals("push", build.getEventType());
        assertEquals(Instant.parse("2015-11-22T15:09:22Z"), build.getFinishedAt());
        assertEquals(26318654, build.getCommitId(), 0);
        assertEquals(1889385, build.getRepositoryId(), 0);
    }

    @Test
    public void getBuild_nonExistingId_returnsNull() throws Exception {
        Build build = travis.getBuild(1);
        assertNull(build);
    }

    @Test
    public void getRepository_byAuthorAndName_returnsCorrectData() throws Exception {
        Repository repository = travis.repositories().getRepository("thrau", "jarchivelib");

        assertEquals(1889385, repository.getId(), 0);
        assertEquals("thrau/jarchivelib", repository.getSlug());
        assertEquals("Java", repository.getGithubLanguage());
        assertTrue(64 < repository.getLastBuildNumber());
        assertTrue(Instant.parse("2015-11-22T15:00:00Z").isBefore(repository.getLastBuildStartedAt()));
    }

    @Test
    public void getBuilds_returnsCorrectData() throws Exception {
        List<Build> builds = travis.builds().getBuilds(1889385);

        assertEquals(25, builds.size(), 5);
        assertTrue(builds.get(0).getNumber() > 64);

        for (Build build : builds) {
            assertEquals(1889385, build.getRepositoryId(), 0);
        }
    }

    @Test
    public void getBuilds_withOffset_returnsCorrectData() throws Exception {
        List<Build> builds = travis.builds().getBuilds(1889385, 3);

        assertEquals(2, builds.size());
        assertEquals(1889385, builds.get(0).getRepositoryId(), 0);
        assertEquals(1889385, builds.get(1).getRepositoryId(), 0);

        assertEquals(2, builds.get(0).getNumber(), 0);
        assertEquals(1, builds.get(1).getNumber(), 0);

        assertEquals(18708233, builds.get(0).getId(), 0);
        assertEquals(18707665, builds.get(1).getId(), 0);

    }
}
