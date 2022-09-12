package com.example.server.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AbstractService<T, Id> {

    Mono<T> findById(Id id);

    Mono<T> save(T t);

    Mono<Void> update(T t);

    Mono<Void> delete(Id id);

    Flux<T> findAll();

}
