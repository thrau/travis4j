package org.travis4j.model;

import java.time.Instant;

/**
 * Job.
 */
public interface Job extends Entity {
    Long getId();

    Long getRepositoryId();

    String getRepositorySlug();

    Long getLogId();

    Long getBuildId();

    String getNumber();

    Boolean getAllowFailure();

    Instant getFinishedAt();

    Instant getStartedAt();

    String getState();

    Long getCommitId();

    String getQueue();

    Config getConfig();

    Commit getCommit();
}
