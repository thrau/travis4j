package org.travis4j.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * JsonResources.
 */
public final class JsonResources {

    private JsonResources() {
        // util class
    }

    public static JSONObject read(URL resource) {
        try (Scanner scanner = new Scanner(resource.openStream(), "UTF-8").useDelimiter("\\A")) {
            return new JSONObject(scanner.next());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static JSONObject read(String name, ClassLoader cl) {
        URL resource = cl.getResource(name);
        if (resource == null) {
            throw new UncheckedIOException(new FileNotFoundException(name));
        }
        return read(resource);
    }

    public static JSONObject read(String name) {
        return read(name, JsonResources.class.getClassLoader());
    }

}
