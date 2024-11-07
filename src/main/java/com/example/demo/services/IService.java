package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Mono;

public interface IService<T> {

    Mono<T> findById(Integer id);

    Mono<Page<T>> findAll(Pageable pageable);

    Mono<T> save(T t);

    Mono<Void> update(T t);

    Mono<Void> delete(Integer id);

}
