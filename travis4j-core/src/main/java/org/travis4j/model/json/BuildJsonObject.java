/**
 *    Copyright 2015-2016 Thomas Rausch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.travis4j.model.json;

import java.time.Instant;
import java.util.List;

import org.json.JSONObject;
import org.travis4j.EntityVisitor;
import org.travis4j.model.Build;
import org.travis4j.model.Commit;
import org.travis4j.model.Config;

/**
 * BuildJsonObject.
 */
public class BuildJsonObject extends AbstractJsonObject implements Build {

    private JSONObject commit;

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
    public Commit getCommit() {
        if (commit == null) {
            return null;
        }
        return new CommitJsonObject(commit);
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
    public Long getNumber() {
        return getLong("number");
    }

    protected void setCommit(JSONObject json) {
        this.commit = json;
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
