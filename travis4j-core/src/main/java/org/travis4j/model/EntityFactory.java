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

    List<Job> createJobList(JsonResponse response);

    List<User> createUserList(JsonResponse response);

    List<Build> createBuildList(JsonResponse response);

    List<Repository> createRepositoryList(JsonResponse response);

    Log createLog(JsonResponse response, HttpEntity body);
}
