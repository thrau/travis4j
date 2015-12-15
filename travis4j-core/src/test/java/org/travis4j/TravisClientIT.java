package org.travis4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

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

        assertEquals("thrau/jarchivelib", repository.getSlug());
        assertEquals("Java", repository.getGithubLanguage());
        assertTrue(64 < repository.getLastBuildNumber());
        assertTrue(Instant.parse("2015-11-22T15:00:00Z").isBefore(repository.getLastBuildStartedAt()));
    }
}
