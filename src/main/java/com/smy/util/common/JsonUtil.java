package com.smy.util.common;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

/**
 * Json格式转换工具。
 * <p>Author: smy
 * <p>Date: 2018/1/7
 */
public class JsonUtil {

    public static final Gson gson = gsonBuilder().create();

    private static GsonBuilder gsonBuilder() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (jsonElement, type, jsonDeserializationContext) -> new Date(jsonElement.getAsJsonPrimitive().getAsLong()));
        gb.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()));
        gb.setDateFormat(DateFormat.LONG);
        return gb;
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }


}
