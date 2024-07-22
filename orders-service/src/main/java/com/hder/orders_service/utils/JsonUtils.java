package com.hder.orders_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try{
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json,clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
