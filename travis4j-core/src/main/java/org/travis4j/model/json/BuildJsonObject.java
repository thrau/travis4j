package org.travis4j.model.json;

import java.time.Instant;
import java.util.List;

import org.json.JSONObject;
import org.travis4j.EntityVisitor;
import org.travis4j.model.Build;
import org.travis4j.model.Config;

/**
 * BuildJsonObject.
 */
public class BuildJsonObject extends AbstractJsonObject implements Build {

    public BuildJsonObject(JSONObject json) {
        super(json);
    }

    @Override
    public List<Long> getJobIds() {
        return getLongList("job_ids");
    }

    @Override
    public Long getDuration() {
        return getLong("duration");
    }

    @Override
    public String getEventType() {
        return getString("event_type");
    }

    @Override
    public Boolean getPullRequest() {
        return getBoolean("pull_request");
    }

    @Override
    public String getPullRequestTitle() {
        return getString("pull_request_title");
    }

    @Override
    public Long getPullRequestNumber() {
        return getLong("pull_request_number");
    }

    @Override
    public Instant getStartedAt() {
        return getInstant("started_at");
    }

    @Override
    public Instant getFinishedAt() {
        return getInstant("finished_at");
    }

    @Override
    public Long getRepositoryId() {
        return getLong("repository_id");
    }

    @Override
    public Long getCommitId() {
        return getLong("commit_id");
    }

    @Override
    public Config getConfig() {
        throw new UnsupportedOperationException(); // TODO config
    }

    @Override
    public Long getId() {
        return getLong("id");
    }

    @Override
    public String getState() {
        return getString("state");
    }

    @Override
    public String getNumber() {
        return getString("number");
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
