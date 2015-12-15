package org.travis4j.api;

import java.util.List;

import org.travis4j.model.User;

/**
 * UsersResource.
 */
public interface UsersResource {

    List<User> getUsers();

    User getUser(long id);
}
