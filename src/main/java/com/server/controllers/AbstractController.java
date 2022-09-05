package com.server.controllers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AbstractController<T, Id> {

    Mono<T> findById(Id id);

    Flux<T> findAll();

    Mono<T> save(T t);

    Mono<Void> update(Id id, T t);

    Mono<Void> delete(Id id);

}
