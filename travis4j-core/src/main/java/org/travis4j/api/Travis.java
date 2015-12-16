package org.travis4j.api;

/**
 * TravisClient.
 */
public interface Travis {

    RepositoriesResource repositories();

    UsersResource users();

    BuildsResource builds();
}
