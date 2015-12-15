package org.travis4j.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Wraps a HttpResponse and makes available some function for handling json.
 */
public class JsonResponse {

    private HttpResponse response;

    public JsonResponse(HttpResponse response) {
        this.response = response;
    }

    public JSONObject getJson() throws JSONException {
        HttpEntity entity = response.getEntity();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            entity.writeTo(out);
            return new JSONObject(out.toString());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public boolean isNotFound() {
        return response.getStatusLine().getStatusCode() == 404;
    }

    public boolean isOk() {
        return response.getStatusLine().getStatusCode() == 200;
    }

    public HttpResponse getHttpResponse() {
        return response;
    }

}
