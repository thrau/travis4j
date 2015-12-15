package org.travis4j.rest;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 * SimpleRestClient.
 */
public class SimpleRestClient implements Closeable {

    private URI api;
    private HttpClient httpClient;

    public SimpleRestClient(HttpClient httpClient, URI api) {
        this.httpClient = httpClient;
        this.api = api;
    }

    public JsonResponse query(String path) {
        HttpGet request = httpGet(path);
        return new JsonResponse(execute(request));
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpGet httpGet(String path) {
        return new HttpGet(api.resolve(path));
    }

    public HttpResponse execute(HttpGet request) throws UncheckedIOException {
        try {
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
