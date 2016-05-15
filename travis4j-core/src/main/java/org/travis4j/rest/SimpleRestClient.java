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

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps a HttpClient for easier use.
 */
public class SimpleRestClient implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleRestClient.class);

    private URI api;
    private HttpClient httpClient;

    public SimpleRestClient(HttpClient httpClient, URI api) {
        this.httpClient = httpClient;
        this.api = api;
    }

    public JsonResponse query(String path) {
        return get(path).execute();
    }

    public JsonResponse query(RequestBuilder request) {
        return new JsonResponse(execute(request.build()));
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public Request get(String path) {
        return new Request(this, getRequestBuilder(path));
    }

    public RequestBuilder getRequestBuilder(String path) {
        return RequestBuilder.get(api.resolve(path));
    }

    public HttpResponse execute(HttpUriRequest request) throws UncheckedIOException {
        try {
            LOG.debug("Executing {}", request);
            return httpClient.execute(request);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (httpClient instanceof Closeable) {
            ((Closeable) httpClient).close();
        }
    }
}
