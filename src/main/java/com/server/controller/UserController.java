package com.server.controller;

import com.server.domain.User;
import com.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@SecurityScheme(
        name = "Basic Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@SecurityRequirement(name = "Basic Authentication")
@RestController
@RequestMapping("/users")
public class UserController implements AbstractController<User, Integer> {

    private final UserService userService;

    @Override
    @Operation(tags = "users", security = @SecurityRequirement(name = "Basic Authentication"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @Override
    @Operation(tags = "users", security = @SecurityRequirement(name = "Basic Authentication"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @Override
    @Operation(tags = "users", security = @SecurityRequirement(name = "Basic Authentication"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<User> save(@RequestBody User user) {
        return userService.save(user);
    }

    @Override
    @Operation(tags = "users", security = @SecurityRequirement(name = "Basic Authentication"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public Mono<Void> update(@RequestBody User user) {
        return userService.update(user);
    }

    @Override
    @Operation(tags = "users", security = @SecurityRequirement(name = "Basic Authentication"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public Mono<Void> delete(@RequestBody User user) {
        return userService.delete(user);
    }

}
