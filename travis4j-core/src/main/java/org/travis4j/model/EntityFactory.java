package org.travis4j.model;

import java.util.List;

import org.json.JSONObject;
import org.travis4j.rest.JsonResponse;

/**
 * EntityFactory.
 */
public interface EntityFactory {
    Repository createRepository(JsonResponse response);

    User createUser(JsonResponse response);

    List<User> createUserList(JsonResponse response);

    Build createBuild(JsonResponse response);
}
