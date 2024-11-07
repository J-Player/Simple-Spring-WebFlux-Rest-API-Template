package com.example.demo.models.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationResponse(@NotEmpty(message = "The 'accessToken' cannot be empty or null")
                                     @JsonProperty("access_token") String accessToken) {
}
