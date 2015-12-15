package org.travis4j.model;

import org.travis4j.EntityVisitor;

/**
 * Entity.
 */
public interface Entity {

    /**
     * Accept the given visitor.
     * 
     * @param visitor the visiting visitor
     */
    void accept(EntityVisitor visitor);
}
