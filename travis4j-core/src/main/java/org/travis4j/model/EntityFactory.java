package org.travis4j.model;

import java.util.List;

import org.apache.http.HttpEntity;
import org.travis4j.rest.JsonResponse;

/**
 * EntityFactory.
 */
public interface EntityFactory {
    Repository createRepository(JsonResponse response);

    User createUser(JsonResponse response);

    Build createBuild(JsonResponse response);

    Job createJob(JsonResponse response);

    List<User> createUserList(JsonResponse response);

    List<Build> createBuildList(JsonResponse response);

    List<Repository> createRepositoryList(JsonResponse response);

    Log createLog(JsonResponse response, HttpEntity body);
}
