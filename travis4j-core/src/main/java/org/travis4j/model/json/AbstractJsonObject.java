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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * AbstractJsonObject.
 */
public abstract class AbstractJsonObject {

    protected JSONObject json;

    public AbstractJsonObject(JSONObject json) {
        this.json = json;
    }

    public JSONObject getJson() {
        return json;
    }

    public String getString(String key) {
        return (json.isNull(key)) ? null : json.getString(key);
    }

    public Boolean getBoolean(String key) {
        return (json.isNull(key)) ? null : json.getBoolean(key);
    }

    public Long getLong(String key) {
        return (json.isNull(key)) ? null : json.getLong(key);
    }

    public Instant getInstant(String key) {
        return (json.isNull(key)) ? null : Instant.parse(json.getString(key));
    }

    public List<String> getStringList(String key) {
        return getList(key, JSONArray::getString);
    }

    public List<Long> getLongList(String key) {
        return getList(key, JSONArray::getLong);
    }

    public <T> List<T> getList(String key, BiFunction<JSONArray, Integer, T> getter) {
        return (json.isNull(key)) ? null : toList(json.getJSONArray(key), getter);
    }

    public <T> List<T> toList(JSONArray array, BiFunction<JSONArray, Integer, T> getter) {
        return toList(array, false, getter);
    }

    public <T> List<T> toList(JSONArray array, boolean includeNulls, BiFunction<JSONArray, Integer, T> getter) {
        List<T> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            if (!array.isNull(i) && !includeNulls) {
                list.add(getter.apply(array, i));
            }
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(json, ((AbstractJsonObject) o).json);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(json);
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
