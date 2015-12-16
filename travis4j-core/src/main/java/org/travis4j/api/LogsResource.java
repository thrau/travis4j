package org.travis4j.api;

import org.travis4j.model.Log;

/**
 * LogsResource.
 */
public interface LogsResource {
    Log getLog(long logId);
}
