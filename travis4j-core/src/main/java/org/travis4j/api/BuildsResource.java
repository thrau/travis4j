package org.travis4j.api;

import java.util.List;

import org.travis4j.model.Build;
import org.travis4j.model.PageIterator;
import org.travis4j.model.request.ListBuildsRequest;

/**
 * BuildsResource.
 */
public interface BuildsResource {

    Build getBuild(long buildId);

    List<Build> getBuilds(long repositoryId);

    List<Build> getBuilds(long repositoryId, long offset);

    List<Build> getBuilds(ListBuildsRequest request);

    PageIterator<Build> getAllBuilds(long repositoryId);

    // TODO
}
