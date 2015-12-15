package org.travis4j.model;

import java.time.Instant;

/**
 * Commit.
 */
public interface Commit {
    String getAuthorName();

    String getCommitterEmail();

    String getCompareUrl();

    Instant getCommittedAt();

    String getAuthorEmail();

    Long getId();

    String getMessage();

    String getSha();

    String getBranch();

    String getCommitterName();
}
