package com.example.server.repositories;

import com.example.server.domains.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveSortingRepository<User, UUID> {

    Mono<User> findByUsername(String username);

}
