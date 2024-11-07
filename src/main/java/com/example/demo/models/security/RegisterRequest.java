package com.example.demo.models.security;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty(message = "The 'username' cannot be empty or null")
        String username,
        @NotEmpty(message = "The 'password' cannot be empty or null")
        String password) {
}
