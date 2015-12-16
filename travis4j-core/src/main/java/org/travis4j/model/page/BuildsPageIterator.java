package org.travis4j.model.page;

import java.util.List;

import org.travis4j.api.BuildsResource;
import org.travis4j.model.Build;

/**
 * PageResponseImpl.
 */
public class BuildsPageIterator extends AbstractPageIterator<Build> {

    private BuildsResource resource;

    private long repositoryId; // TODO query

    public BuildsPageIterator(long repositoryId, BuildsResource resource) {
        this.repositoryId = repositoryId;
        this.resource = resource;
    }

    @Override
    protected long getCurrentOffset() {
        return getCurrentPage().get(0).getNumber(); // TODO revisit, this may not hold
    }

    @Override
    protected List<Build> loadFirstPage() {
        return resource.getBuilds(repositoryId);
    }

    @Override
    protected List<Build> loadNextPage(long offset) {
        return resource.getBuilds(repositoryId, offset);
    }
}
