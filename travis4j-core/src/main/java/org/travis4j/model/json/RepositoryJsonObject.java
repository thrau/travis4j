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

import org.json.JSONObject;
import org.travis4j.EntityVisitor;
import org.travis4j.model.Repository;

/**
 * RepositoryJsonObject.
 */
public class RepositoryJsonObject extends AbstractJsonObject implements Repository {

    public RepositoryJsonObject(JSONObject json) {
        super(json);
    }

    @Override
    public Long getId() {
        return getLong("id");
    }

    @Override
    public String getDescription() {
        return getString("description");
    }

    @Override
    public Boolean getActive() {
        return getBoolean("active");
    }

    @Override
    public Long getLastBuildId() {
        return getLong("last_build_id");
    }

    @Override
    public String getLastBuildState() {
        return getString("last_build_state");
    }

    @Override
    public Long getLastBuildDuration() {
        return getLong("last_build_duration");
    }

    @Override
    public String getLastBuildLanguage() {
        return getString("last_build_language");
    }

    @Override
    public Instant getLastBuildStartedAt() {
        return getInstant("last_build_started_at");
    }

    @Override
    public Instant getLastBuildFinishedAt() {
        return getInstant("last_build_finished_at");
    }

    @Override
    public String getGithubLanguage() {
        return getString("github_language");
    }

    @Override
    public Long getLastBuildNumber() {
        return getLong("last_build_number");
    }

    @Override
    public String getSlug() {
        return getString("slug");
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
