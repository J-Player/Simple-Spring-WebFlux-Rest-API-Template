package com.example.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IService<T, Id> {

    Mono<T> findById(Id id);

    Flux<T> findAll();

    Mono<T> save(T t);

    Mono<Void> update(T t);

    Mono<Void> delete(Id id);

}
