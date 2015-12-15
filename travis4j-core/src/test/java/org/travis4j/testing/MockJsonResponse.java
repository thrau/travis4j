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
