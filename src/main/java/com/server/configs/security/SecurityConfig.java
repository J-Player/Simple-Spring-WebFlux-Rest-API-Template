package com.server.configs.security;

import com.server.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
//    private static final String USER = "USER";

//    private static final String[] PATHS = {};

    private static final String[] PATHS_ADMIN = {
            "/users/**"
    };

    private static final String[] PATHS_SWAGGER = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/com.api-docs/**",
            "/webjars/**"
    };

    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        //@formatter:off
        return httpSecurity
                .csrf().disable()
                .authorizeExchange()
//                    .pathMatchers(HttpMethod.GET, PATHS).hasRole(USER)
                    .pathMatchers(HttpMethod.GET, PATHS_ADMIN).hasRole(ADMIN)
                    .pathMatchers(HttpMethod.DELETE).hasRole(ADMIN)
                    .pathMatchers(HttpMethod.POST).hasRole(ADMIN)
                    .pathMatchers(HttpMethod.PUT).hasRole(ADMIN)
                .pathMatchers(PATHS_SWAGGER).authenticated()
                .and()
                    .formLogin()
                .and()
                    .httpBasic()
                .and()
                .build();
        //@formatter:on
    }

    @Bean
    protected ReactiveAuthenticationManager authenticationManager(UserService userService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
