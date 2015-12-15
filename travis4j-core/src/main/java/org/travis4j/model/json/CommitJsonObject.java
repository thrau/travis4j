package org.travis4j.model.json;

import java.time.Instant;

import org.json.JSONObject;
import org.travis4j.model.Commit;

/**
 * CommitJsonObject.
 */
public class CommitJsonObject extends AbstractJsonObject implements Commit {

    public CommitJsonObject(JSONObject json) {
        super(json);
    }

    @Override
    public String getCommitterEmail() {
        return getString("committer_email");
    }

    @Override
    public String getAuthorName() {
        return getString("author_name");
    }

    @Override
    public String getCompareUrl() {
        return getString("compare_url");
    }

    @Override
    public Instant getCommittedAt() {
        return getInstant("committed_at");
    }

    @Override
    public String getAuthorEmail() {
        return getString("author_email");
    }

    @Override
    public String getSha() {
        return getString("sha");
    }

    @Override
    public String getBranch() {
        return getString("branch");
    }

    @Override
    public String getCommitterName() {
        return getString("committer_name");
    }

    @Override
    public String getMessage() {
        return getString("message");
    }

    @Override
    public Long getId() {
        return getLong("id");
    }

}
