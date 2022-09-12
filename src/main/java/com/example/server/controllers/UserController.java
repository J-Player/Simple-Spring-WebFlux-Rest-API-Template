package com.example.server.controllers;

import com.example.server.domains.User;
import com.example.server.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "User")
@SecurityScheme(name = "Basic Authentication", type = SecuritySchemeType.HTTP, scheme = "basic")
@SecurityRequirement(name = "Basic Authentication")
public class UserController implements AbstractController<User, Integer> {

    private final UserService userService;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a user by ID.")
    public Mono<User> findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a user by Username.")
    public Mono<User> findByUsername(@RequestParam String username) {
        return userService.findByName(username);
    }

    @Override
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all users.")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Saves a user in the database.")
    public Mono<User> save(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Updates a user in the database.")
    public Mono<Void> update(@PathVariable Integer id, @RequestBody @Valid User user) {
        return userService.update(user.withId(id));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a user in the database.")
    public Mono<Void> delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

}
