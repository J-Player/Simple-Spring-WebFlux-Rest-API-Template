package com.example.demo.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Flux;

@NoRepositoryBean
public interface IRepository<T> extends ReactiveCrudRepository<T, Integer>,
        ReactiveSortingRepository<T, Integer> {

    Flux<T> findAllBy(Pageable pageable);

}
