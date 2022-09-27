package com.travelers.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author kei
 * @since 2022-09-23
 */

public class JsonUtil {
    public static JsonArray getJson(String data) throws IOException {
        ClassPathResource resource = new ClassPathResource(data);
        JsonArray jsonArray = (JsonArray) JsonParser.parseReader(new InputStreamReader(resource.getInputStream()));
        return jsonArray;
    }
}
