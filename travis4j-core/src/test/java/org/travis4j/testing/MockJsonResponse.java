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
package org.travis4j.testing;

import org.json.JSONException;
import org.json.JSONObject;
import org.travis4j.rest.JsonResponse;

/**
 * MockJsonResponse.
 */
public class MockJsonResponse extends JsonResponse {

    private JSONObject json;

    public MockJsonResponse(JSONObject json) {
        super(null);
        this.json = json;
    }

    @Override
    public boolean isNotFound() {
        return false;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public JSONObject getJson() throws JSONException {
        return json;
    }
}
