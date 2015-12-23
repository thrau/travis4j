package org.travis4j.api;

import org.travis4j.model.Job;

/**
 * JobsResource.
 */
public interface JobsResource {
    Job getJob(long jobId);
}
