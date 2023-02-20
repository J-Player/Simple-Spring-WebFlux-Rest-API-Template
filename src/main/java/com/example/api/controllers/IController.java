package com.example.api.controllers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IController<T, DTO> {

    Mono<T> findById(Long id);

    Flux<T> findAll();

    Mono<T> save(DTO dto);

    Mono<Void> update(DTO dto, Long id);

    Mono<Void> delete(Long id);

}
