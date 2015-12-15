package org.travis4j.model;

import java.time.Instant;
import java.util.List;

/**
 * Build.
 */
public interface Build extends Entity {

    Long getId();

    List<Long> getJobIds();

    Long getDuration();

    String getNumber();

    String getEventType();

    Boolean getPullRequest();

    String getPullRequestTitle();

    Long getPullRequestNumber();

    Instant getStartedAt();

    Instant getFinishedAt();

    Long getRepositoryId();

    String getState();

    Long getCommitId();

    Config getConfig();

    Commit getCommit();
}
