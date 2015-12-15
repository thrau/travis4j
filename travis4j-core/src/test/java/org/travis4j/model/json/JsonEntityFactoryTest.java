package org.travis4j.model.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.travis4j.model.Build;
import org.travis4j.model.EntityFactory;
import org.travis4j.testing.JsonResources;
import org.travis4j.testing.MockJsonResponse;

/**
 * JsonEntityFactoryTest.
 */
public class JsonEntityFactoryTest {

    private EntityFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new JsonEntityFactory();
    }

    @Test
    public void createBuildList_createsBuildListCorrectly() throws Exception {
        JSONObject json = JsonResources.read("builds.json");

        List<Build> list = factory.createBuildList(new MockJsonResponse(json));
        Iterator<Build> builds = list.iterator();

        assertEquals(2, list.size());

        Build build;

        build = builds.next();
        assertNotNull(build.getCommit());
        assertEquals(26318654, build.getCommitId(), 0);
        assertEquals(26318654, build.getCommit().getId(), 0);

        build = builds.next();
        assertNotNull(build.getCommit());
        assertEquals(26281307, build.getCommitId(), 0);
        assertEquals(26281307, build.getCommit().getId(), 0);
    }

}
