package com.example.demo.controllers.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.annotations.PageableAsQueryParam;
import com.example.demo.controllers.IController;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.entities.User;
import com.example.demo.models.security.RegisterRequest;
import com.example.demo.services.impl.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController implements IController<User, RegisterRequest> {

    private final UserService userService;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a user by ID.")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
    public Mono<User> findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns a user by username.")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.username.toUpperCase == #username.toUpperCase")
    public Mono<User> findByName(@RequestParam String username) {
        return userService.findByName(username);
    }

    @GetMapping("/all")
    @PageableAsQueryParam
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all users.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<Page<User>> listAll(@Parameter(hidden = true) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Saves a user in the database.")
    public Mono<User> save(@RequestBody @Valid RegisterRequest registerRequest) {
        User user = UserMapper.INSTANCE.toUser(registerRequest);
        return userService.save(user);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Updates a user in the database.")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
    public Mono<Void> update(@PathVariable Integer id, @RequestBody @Valid RegisterRequest registerRequest) {
        User user = UserMapper.INSTANCE.toUser(registerRequest);
        return userService.update(user.withId(id));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a user in the database.")
    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
    public Mono<Void> delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

}