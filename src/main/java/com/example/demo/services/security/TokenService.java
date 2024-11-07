package com.example.demo.services.security;

import com.example.demo.configs.security.SecurityProperties;
import com.example.demo.models.enums.TokenType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final SecurityProperties securityProperties;

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, TokenType.ACCESS_TOKEN, securityProperties.getAccessTokenExpiration());
    }

    public ResponseCookie generateRefreshToken(UserDetails userDetails) {
        long refreshTokenExpiration = securityProperties.getRefreshTokenExpiration();
        String refreshToken = generateToken(userDetails, TokenType.REFRESH_TOKEN, refreshTokenExpiration);
        return generateCookie(TokenType.REFRESH_TOKEN.getType(), refreshToken, refreshTokenExpiration);
    }

    public String extractSubject(String token) {
        return extract(token, DecodedJWT::getSubject);
    }

    public String extractType(String token) {
        Map<String, Claim> map = extractAllClaims(token);
        return map.get("type").asString();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String subject = extractSubject(token);
        return subject.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private ResponseCookie generateCookie(String name, String value, Long maxAge) {
        long maxAgeSeconds = maxAge / 1000;
        return ResponseCookie.fromClientResponse(name, value)
                .maxAge(maxAgeSeconds)
                .httpOnly(true)
                .path("/")
                .build();
    }

    private String generateToken(UserDetails userDetails, TokenType type, Long expiration) {
        return JWT.create()
                .withIssuer(securityProperties.getTokenIssuer())
                .withAudience(securityProperties.getTokenAudience())
                .withClaim("type", type.getType())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(expiration))
                .withSubject(userDetails.getUsername())
                .sign(Algorithm.HMAC256(securityProperties.getTokenSecret()));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

    private Instant extractExpiration(String token) {
        return extract(token, DecodedJWT::getExpiresAtAsInstant);
    }

    private Map<String, Claim> extractAllClaims(String token) {
        return extract(token, DecodedJWT::getClaims);
    }

    private <T> T extract(String token, Function<DecodedJWT, T> resolver) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(this.securityProperties.getTokenSecret()))
                    .withIssuer(this.securityProperties.getTokenIssuer())
                    .withAudience(this.securityProperties.getTokenAudience())
                    .withClaimPresence("type")
                    .build()
                    .verify(token);
            return resolver.apply(jwt);
        } catch (JWTVerificationException ex) {
            log.error("[{}]: {} | SecurityProperties: {}", ex.getClass().getSimpleName(), ex.getMessage(), token);
            throw new RuntimeException(ex);
        }
    }

}