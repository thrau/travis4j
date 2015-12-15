package org.travis4j.api;

import java.util.List;

import org.travis4j.model.Repository;

/**
 * RepositoriesResource.
 */
public interface RepositoriesResource {

    Repository getRepository(long id);

    Repository getRepository(String ownerName, String repositoryName);

    List<Repository> getRepositories(String slug);

}
