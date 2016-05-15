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
