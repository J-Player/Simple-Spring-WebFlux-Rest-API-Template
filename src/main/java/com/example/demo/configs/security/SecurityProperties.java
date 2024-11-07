package com.example.demo.configs.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Data
@Component
@ConfigurationProperties("api.config.security")
public class SecurityProperties {

    private Map<String, String> token = new HashMap<>();

    private static final UUID randomSecretDefault = UUID.randomUUID();

    @Getter
    @AllArgsConstructor
    private enum Constraint {
        ISSUER("issuer"),
        AUDIENCE("audience"),
        SECRET("secret"),
        ACCESS_TOKEN_EXPIRATION("access-token-expiration"),
        REFRESH_TOKEN_EXPIRATION("refresh-token-expiration");
        private String key;
    }

    public String getTokenIssuer() {
        return token.get(Constraint.ISSUER.getKey());
    }

    public String getTokenAudience() {
        return token.get(Constraint.AUDIENCE.getKey());
    }

    public String getTokenSecret() {
        return token.getOrDefault(Constraint.SECRET.getKey(), randomSecretDefault.toString());
    }

    public Long getAccessTokenExpiration() {
        return getMilliseconds(token.get(Constraint.ACCESS_TOKEN_EXPIRATION.getKey()));
    }

    public Long getRefreshTokenExpiration() {
        return getMilliseconds(token.get(Constraint.REFRESH_TOKEN_EXPIRATION.getKey()));
    }

    private long getMilliseconds(String value) {
        final String regex = "^([1-9]\\d*)([dhms])$";
        Pattern pattern = Pattern.compile(regex);
        value = value.toLowerCase();
        Matcher matcher = pattern.matcher(value);
        matcher.matches();
        long time = Long.parseLong(matcher.group(1));
        String timeType = matcher.group(2);
        return switch (timeType) {
            case "d" -> time * 60 * 60 * 60 * 1000;
            case "h" -> time * 60 * 60 * 1000;
            case "m" -> time * 60 * 1000;
            default -> time * 1000;
        };
    }

}
