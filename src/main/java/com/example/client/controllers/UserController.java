package com.example.client.controllers;

import com.example.server.controllers.AbstractController;
import com.example.server.domains.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements AbstractController<User, Integer> {

    private static final String PATH = "/users";

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<User> findById(Integer id) {
        return webClient
                .get()
                .uri(PATH.concat("/{id}"), id)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> findByUsername(String username) {
        return webClient
                .get()
                .uri(builder -> builder
                        .path(PATH)
                        .queryParam("username", username)
                        .build())
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Flux<User> findAll() {
        return webClient
                .get()
                .uri(PATH.concat("/all"))
                .retrieve()
                .bodyToFlux(User.class);
    }

    @Override
    public Mono<User> save(User user) {
        return webClient
                .post()
                .uri(PATH)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .doOnNext(user1 -> log.info(user1.toString()));
    }

    @Override
    public Mono<Void> update(Integer id, User user) {
        return webClient
                .put()
                .uri(PATH.concat("/{id}"), id)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return webClient
                .delete()
                .uri(PATH.concat("/{id}"), id)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
