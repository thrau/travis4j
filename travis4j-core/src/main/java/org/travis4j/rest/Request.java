package org.travis4j.rest;

import org.apache.http.client.methods.RequestBuilder;

/**
 * Convenience class around RequestBuilder.
 */
public class Request {

    private final SimpleRestClient client;

    private RequestBuilder builder;

    public Request(SimpleRestClient client, RequestBuilder builder) {
        this.client = client;
        this.builder = builder;
    }

    public Request addParameter(String key, Object value) {
        builder.addParameter(key, String.valueOf(value));
        return this;
    }

    /**
     * Executes the request using the held rest client.
     * 
     * @return a JsonResponse from the request
     */
    public JsonResponse execute() {
        return client.query(builder);
    }

    public RequestBuilder getBuilder() {
        return builder;
    }
}
