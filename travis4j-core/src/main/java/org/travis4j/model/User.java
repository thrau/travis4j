package org.travis4j.model;

import java.time.Instant;
import java.util.List;

/**
 * User.
 */
public interface User extends Entity {

    Long getId();

    String getName();

    String getLogin();

    String getEmail();

    String getGravatarId();

    List<String> getChannels();

    Boolean getCorrectScopes();

    Boolean getIsSyncing();

    Instant getCreatedAt();

    Instant getSyncedAt();
}
