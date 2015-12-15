package org.travis4j.model;

import java.time.Instant;

/**
 * Repository.
 */
public interface Repository extends Entity {

    Long getId();

    String getSlug();

    String getDescription();

    Boolean getActive();

    String getGithubLanguage();

    Long getLastBuildId();

    Long getLastBuildNumber();

    String getLastBuildState();

    Long getLastBuildDuration();

    String getLastBuildLanguage();

    Instant getLastBuildStartedAt();

    Instant getLastBuildFinishedAt();

}
