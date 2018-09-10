package com.infosys.onebank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringWriter;

public class JsonParserUtils {

    public static JSONObject parse(String jsonString) {
        JSONParser j = new JSONParser();
        try {
            return (JSONObject) j.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static JSONArray parseArray(String jsonString) {
        JSONParser j = new JSONParser();
        try {
            return (JSONArray) j.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String createJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        StringWriter objectWriter = new StringWriter();
        try {
            mapper.writeValue(objectWriter, obj);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return objectWriter.toString();
    }
}
