package com.client.service;

import com.server.domain.User;
import com.server.service.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements AbstractService<User, Integer> {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<User> findById(Integer id) {
        return webClient
                .get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Flux<User> findAll() {
        return webClient
                .get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class);
    }

    @Override
    public Mono<User> save(User user) {
        return webClient
                .post()
                .uri("/users")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<Void> update(User user) {
        return webClient
                .put()
                .uri("/users")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> delete(User user) {
        return webClient
                .delete()
                .uri("/users")
                .retrieve()
                .bodyToMono(Void.class);
    }

}
