/**
 *    Copyright 2015-2016 Thomas Rausch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.travis4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.travis4j.model.Build;
import org.travis4j.model.Job;
import org.travis4j.model.Log;
import org.travis4j.model.PageIterator;
import org.travis4j.model.Repository;
import org.travis4j.model.request.ListBuildsRequest;

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

    @Test
    public void getBuilds_byIds_returnsCorrectData() throws Exception {
        List<Build> builds = travis.builds().getBuilds(new ListBuildsRequest().setIds(18708233L, 18707665L));

        assertEquals(2, builds.size());
        assertEquals(1889385, builds.get(0).getRepositoryId(), 0);
        assertEquals(1889385, builds.get(1).getRepositoryId(), 0);

        assertEquals(1, builds.get(0).getNumber(), 0);
        assertEquals(2, builds.get(1).getNumber(), 0);

        assertEquals(18707665, builds.get(0).getId(), 0);
        assertEquals(18708233, builds.get(1).getId(), 0);
    }

    @Test
    public void getRepositories_byIds_returnsCorrectData() throws Exception {
        List<Repository> list = travis.getRepositories(Arrays.asList(1889384L, 1889385L));

        assertEquals(2, list.size());
        list.sort((r1, r2) -> Long.compare(r1.getId(), r2.getId())); // order is not guaranteed by travis
        assertEquals("thrau/dotfiles", list.get(0).getSlug());
        assertEquals("thrau/jarchivelib", list.get(1).getSlug());
    }

    @Test
    public void getAllBuilds_returnsCorrectIterator() throws Exception {
        PageIterator<Build> pageIterator = travis.getAllBuilds(1889385);

        List<Build> next;

        assertTrue(pageIterator.hasNext());
        next = pageIterator.next();
        assertEquals(25, next.size());
        assertTrue(next.get(0).getNumber() > 64);

        assertTrue(pageIterator.hasNext());
        next = pageIterator.next();
        assertEquals(25, next.size());
    }

    @Test
    public void getLog_returnsCorrectData() throws Exception {
        Log log = travis.getLog(65952238L);
        assertEquals(65952238, log.getId(), 0);
        assertEquals(92561342, log.getJobId(), 0);

        Stream<String> body = log.getBody();
        assertEquals(6989, body.count()); // the amount of lines in the body

    }

    @Test
    public void getJob_returnsCorrectData() throws Exception {
        Job job = travis.getJob(92561342);

        assertEquals(92561342, job.getId(), 0);
        assertEquals(26318654, job.getCommitId(), 0);
        assertEquals(26318654, job.getCommit().getId(), 0);
    }

    @Test
    public void getJobs_byIds_returnsCorrectData() throws Exception {
        List<Job> list = travis.getJobsOfBuild(92561340L);

        assertEquals(2, list.size());
        list.sort((r1, r2) -> Long.compare(r1.getId(), r2.getId()));
        assertEquals(92561342L, list.get(0).getId(), 0);
        assertEquals(92561344L, list.get(1).getId(), 0);
    }
}
