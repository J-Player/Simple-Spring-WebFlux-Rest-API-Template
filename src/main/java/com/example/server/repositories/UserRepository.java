package com.example.server.repositories;

import com.example.server.domains.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveSortingRepository<User, Integer> {

    Mono<User> findByUsername(String username);

}
