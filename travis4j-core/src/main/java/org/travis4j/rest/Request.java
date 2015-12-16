package org.travis4j.rest;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public <T> Request addParameter(String key, CharSequence delimiter, Collection<T> values) {
        List<String> joinables = values.stream().map(Objects::toString).collect(Collectors.toList());
        return addParameter(key, String.join(delimiter, joinables));
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

    @Override
    public String toString() {
        return String.format("Request[%s]", builder.build());
    }
}
