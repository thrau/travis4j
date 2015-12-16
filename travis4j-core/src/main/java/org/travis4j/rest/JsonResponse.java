package org.travis4j.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps a HttpResponse and makes available some function for handling json.
 */
public class JsonResponse {

    private static final Logger LOG = LoggerFactory.getLogger(JsonResponse.class);

    private HttpResponse response;
    private String body;

    public JsonResponse(HttpResponse response) {
        this.response = response;
    }

    public String getBody() {
        if (body != null) {
            return body;
        }
        if (!isOk()) {
            // FIXME
            throw new RuntimeException("No 200 response, was: " + response);
        }

        HttpEntity entity = response.getEntity();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            entity.writeTo(out);
            body = out.toString();
            return body;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public JSONObject getJson() throws JSONException {
        return new JSONObject(getBody());
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
