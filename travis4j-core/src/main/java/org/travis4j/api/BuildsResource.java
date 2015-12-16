package org.travis4j.api;

import java.util.List;

import org.travis4j.model.Build;
import org.travis4j.model.PageIterator;

/**
 * BuildsResource.
 */
public interface BuildsResource {

    Build getBuild(long buildId);

    List<Build> getBuilds(long repositoryId);

    List<Build> getBuilds(long repositoryId, long offset);

    PageIterator<Build> getAllBuilds(long repositoryId);

    // TODO
}
