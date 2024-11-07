package com.example.demo.controllers;

import reactor.core.publisher.Mono;

public interface IController<T1, T2> {

    Mono<T1> findById(Integer id);
    Mono<T1> save(T2 t2);
    Mono<Void> update(Integer id, T2 t2);
    Mono<Void> delete(Integer id);

}
