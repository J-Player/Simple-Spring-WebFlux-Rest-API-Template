package com.example.demo.configs.security;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.security.TokenService;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class SecurityFilter extends AuthenticationWebFilter {

    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private final TokenService tokenService;

    public SecurityFilter(ReactiveAuthenticationManager authenticationManager,
                          @Autowired ReactiveUserDetailsService reactiveUserDetailsService,
                          @Autowired TokenService tokenService) {
        super(authenticationManager);
        this.reactiveUserDetailsService = reactiveUserDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = recoverToken(exchange.getRequest());
        if (token != null) {
            return Mono.just(token)
                    .map(tokenService::extractSubject)
                    .onErrorMap(this::toResponseStatusException)
                    .flatMap(reactiveUserDetailsService::findByUsername)
                    .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found")))
                    .filter(userDetails -> tokenService.isValidToken(token, userDetails))
                    .map(userDetails ->
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null,
                                    userDetails.getAuthorities()))
                    .flatMap(authentication -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder
                                    .withAuthentication(authentication)));
        }
        return chain.filter(exchange);
    }

    private String recoverToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) return null;
        return token.substring(7);
    }

    private ResponseStatusException toResponseStatusException(Throwable throwable) {
        if (throwable instanceof JWTCreationException ex) {
            return new ResponseStatusException(INTERNAL_SERVER_ERROR, ex.getMessage());
        } else return new ResponseStatusException(BAD_REQUEST, throwable.getMessage());
    }

}
