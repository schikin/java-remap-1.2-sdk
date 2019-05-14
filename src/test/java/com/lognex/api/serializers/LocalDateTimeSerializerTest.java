package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lognex.api.LognexApi;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import static java.time.LocalDateTime.now;
import static org.junit.Assert.*;

public class LocalDateTimeSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_localDateTimeSerialization() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        LocalDateTime now = now();
        String expected = "\"" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "\"";

        assertNotEquals(expected, gson.toJson(now));
        assertEquals(expected, gsonCustom.toJson(now));
    }

    @Test
    public void test_localDateTimeDeserialization() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        LocalDateTime expected = now();
        String date = "\"" + expected.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "\"";

        try {
            gson.fromJson(date, LocalDateTime.class);
            fail("Ожидалось исключение JsonSyntaxException!");
        } catch (JsonSyntaxException ignored) {
        }

        assertEquals(
                expected,
                gsonCustom.fromJson(date, LocalDateTime.class)
        );
    }
}
