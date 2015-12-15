package org.travis4j.api;

import org.travis4j.model.Build;

/**
 * BuildsResource.
 */
public interface BuildsResource {

    Build getBuild(long buildId);

    // TODO
}
