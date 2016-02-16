package org.travis4j.model.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpEntity;
import org.json.JSONObject;
import org.travis4j.EntityVisitor;
import org.travis4j.model.Log;

/**
 * LogJsonObject.
 */
public class LogJsonObject extends AbstractJsonObject implements Log {

    private final HttpEntity body;
    private List<String> cache;

    public LogJsonObject(JSONObject json, HttpEntity body) {
        super(json);
        this.body = body;
    }

    @Override
    public Long getId() {
        return getLong("id");
    }

    @Override
    public Long getJobId() {
        return getLong("job_id");
    }

    @Override
    public Stream<String> getBody() {
        if (cache != null) {
            return cache.stream();
        }

        if (body == null) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(body.getContent()))) {
            cache = reader.lines().collect(Collectors.toList());
            return cache.stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
