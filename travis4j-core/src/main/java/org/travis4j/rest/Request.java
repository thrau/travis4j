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

    public Request addOptionalParameter(String key, Object value) {
        return (value == null) ? this : addParameter(key, value);
    }

    public <T> Request addParameter(String key, CharSequence delimiter, Collection<T> values) {
        return addParameter(key, join(delimiter, values));
    }

    public <T> Request addOptionalParameter(String key, CharSequence delimiter, Collection<T> values) {
        return (values == null) ? this : addParameter(key, join(delimiter, values));
    }

    private <T> String join(CharSequence delimiter, Collection<T> values) {
        return String.join(delimiter, toStringList(values));
    }

    private <T> List<String> toStringList(Collection<T> values) {
        return values.stream().map(Objects::toString).collect(Collectors.toList());
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
