package org.travis4j.api;

import java.util.List;

import org.travis4j.model.Job;

/**
 * JobsResource.
 */
public interface JobsResource {
    Job getJob(long jobId);

    List<Job> getJobsOfBuild(long buildId);

}
