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

    public JsonResponse(HttpResponse response) {
        this.response = response;
    }

    public JSONObject getJson() throws JSONException {
        HttpEntity entity = response.getEntity();

        if(!isOk()) {
            // FIXME
            throw new RuntimeException("No 200 response, was: " + response);
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            entity.writeTo(out);
            LOG.info("Wrote {}", out);
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
