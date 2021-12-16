package com.client.controller;

import com.client.service.UserService;
import com.server.controller.AbstractController;
import com.server.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController implements AbstractController<User, Integer> {

    private final UserService userService;

    @Override
    public Mono<User> findById(Integer id) {
        return userService.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @Override
    public Mono<User> save(User user) {
        return userService.save(user);
    }

    @Override
    public Mono<Void> update(User user) {
        return userService.update(user);
    }

    @Override
    public Mono<Void> delete(User user) {
        return userService.delete(user);
    }

}
