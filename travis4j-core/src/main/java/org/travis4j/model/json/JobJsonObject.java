package org.travis4j.model.json;

import java.time.Instant;

import org.json.JSONObject;
import org.travis4j.EntityVisitor;
import org.travis4j.model.Commit;
import org.travis4j.model.Config;
import org.travis4j.model.Job;

/**
 * JobJsonObject.
 */
public class JobJsonObject extends AbstractJsonObject implements Job {

    private JSONObject commit;

    public JobJsonObject(JSONObject json) {
        super(json);
    }

    @Override
    public Long getRepositoryId() {
        return getLong("repository_id");
    }

    @Override
    public Long getLogId() {
        return getLong("log_id");
    }

    @Override
    public Long getBuildId() {
        return getLong("build_id");
    }

    @Override
    public Boolean getAllowFailure() {
        return getBoolean("allow_failure");
    }

    @Override
    public Instant getFinishedAt() {
        return getInstant("finished_at");
    }

    @Override
    public Instant getStartedAt() {
        return getInstant("started_at");
    }

    @Override
    public Long getCommitId() {
        return getLong("commit_id");
    }

    @Override
    public String getQueue() {
        return getString("queue");
    }

    @Override
    public Commit getCommit() {
        if (commit == null) {
            return null;
        }
        return new CommitJsonObject(commit);
    }

    @Override
    public Config getConfig() {
        throw new UnsupportedOperationException();
    }

    protected void setCommit(JSONObject json) {
        this.commit = json;
    }

    @Override
    public String getRepositorySlug() {
        return getString("repository_slug");
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
