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
import org.travis4j.model.User;

/**
 * UserJsonObject.
 */
public class UserJsonObject extends AbstractJsonObject implements User {

    public UserJsonObject(JSONObject json) {
        super(json);
    }

    @Override
    public Long getId() {
        return getLong("id");
    }

    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public String getLogin() {
        return getString("login");
    }

    @Override
    public String getEmail() {
        return getString("email");
    }

    @Override
    public List<String> getChannels() {
        return getStringList("channels");
    }

    @Override
    public String getGravatarId() {
        return getString("gravatar_id");
    }

    @Override
    public Boolean getCorrectScopes() {
        return getBoolean("correct_scopes");
    }

    @Override
    public Boolean getIsSyncing() {
        return getBoolean("is_syncing");
    }

    @Override
    public Instant getCreatedAt() {
        return getInstant("created_at");
    }

    @Override
    public Instant getSyncedAt() {
        return getInstant("synced_at");
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
