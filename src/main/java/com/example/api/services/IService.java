package com.example.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IService<T1, T2> {

    Mono<T1> findById(Long id);

    Flux<T1> findAll();

    Mono<T1> save(T2 t2);

    Mono<Void> update(T2 t2, Long id);

    Mono<Void> delete(Long id);

}
