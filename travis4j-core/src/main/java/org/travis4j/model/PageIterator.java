package org.travis4j.model;

import java.util.Iterator;
import java.util.List;

/**
 * Allows to iterate over pages, where it is required to dispatch a query to retrieve each page.
 * 
 * @param <T>
 */
public interface PageIterator<T> extends Iterator<List<T>> {
    @Override
    boolean hasNext();

    @Override
    List<T> next();
}
