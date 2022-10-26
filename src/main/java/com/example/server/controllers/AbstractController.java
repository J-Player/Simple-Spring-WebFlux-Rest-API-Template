package com.example.server.controllers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AbstractController<T, DTO, Id> {

    Mono<T> findById(Id id);

    Flux<T> findAll();

    Mono<T> save(DTO dto);

    Mono<Void> update(DTO dto, Id id);

    Mono<Void> delete(Id id);

}
