package com.example.demo.configs.web.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantJsonDeserializer extends JsonDeserializer<Instant> {

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX").withZone(ZoneOffset.UTC);

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String str = p.getText();
        int i = str.lastIndexOf(".");
        str = str.substring(0, i);
        str = str.concat("Z");
        return Instant.from(fmt.parse(str));
    }

}
