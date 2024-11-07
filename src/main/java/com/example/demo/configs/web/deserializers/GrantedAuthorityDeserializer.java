package com.example.demo.configs.web.deserializers;

import java.io.IOException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class GrantedAuthorityDeserializer extends JsonDeserializer<GrantedAuthority> {

    @Override
    public GrantedAuthority deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String authority = jsonParser.getText();
        if (authority != null) {
            return new SimpleGrantedAuthority(authority);
        } else {
            return null;
        }
    }
}