package com.urise.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.urise.webapp.model.AbstractSection;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class JsonParser {
    public static final Type LOCAL_DATE_TYPE = new TypeToken<LocalDate>(){}.getType();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter())
            .registerTypeAdapter(LOCAL_DATE_TYPE, new GsonLocalDateAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}