package com.example.demo.controllers.impl;

import com.example.demo.models.security.AuthenticationRequest;
import com.example.demo.models.security.AuthenticationResponse;
import com.example.demo.models.security.RegisterRequest;
import com.example.demo.services.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user in the database")
    public Mono<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns the access token for the user")
    public Mono<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest, ServerHttpResponse response) {
        return authenticationService.login(authenticationRequest, response);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns the access token for the user")
    public Mono<Void> logout(ServerHttpRequest request, ServerHttpResponse response) {
        return authenticationService.logout(request, response);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a new access token")
    public Mono<AuthenticationResponse> refresh(ServerHttpRequest request) {
        return authenticationService.refresh(request);
    }

}