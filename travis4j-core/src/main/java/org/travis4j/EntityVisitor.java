package org.travis4j;

import org.travis4j.model.Build;
import org.travis4j.model.Repository;
import org.travis4j.model.User;
import org.travis4j.model.json.BuildJsonObject;

/**
 * A visitor of Entity objects.
 */
public interface EntityVisitor {

    void visit(Repository entity);

    void visit(User entity);

    void visit(Build entity);
}
