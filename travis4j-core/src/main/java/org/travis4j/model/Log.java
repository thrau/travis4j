package org.travis4j.model;

import java.util.stream.Stream;

/**
 * Log.
 */
public interface Log extends Entity {

    Long getId();

    Long getJobId();

    Stream<String> getBody();
}
