package org.travis4j.model.request;

import java.util.Arrays;
import java.util.List;

/**
 * ListBuildsRequest.
 */
public class ListBuildsRequest  {

    private List<Long> ids;
    private Long repositoryId;
    private String slug;
    private Long number;
    private Long offset;
    private String eventType;

    public List<Long> getIds() {
        return ids;
    }

    public ListBuildsRequest setIds(List<Long> ids) {
        this.ids = ids;
        return this;
    }

    public ListBuildsRequest setIds(Long... ids) {
        return setIds(Arrays.asList(ids));
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public ListBuildsRequest setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public ListBuildsRequest setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public Long getNumber() {
        return number;
    }

    public ListBuildsRequest setNumber(Long number) {
        this.number = number;
        return this;
    }

    public Long getOffset() {
        return offset;
    }

    public ListBuildsRequest setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public ListBuildsRequest setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }
}
