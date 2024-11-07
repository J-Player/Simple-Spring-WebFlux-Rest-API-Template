package com.example.demo.repositories.impl;

import org.springframework.stereotype.Repository;

import com.example.demo.models.entities.User;
import com.example.demo.repositories.IRepository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends IRepository<User> {

    Mono<User> findByUsernameIgnoreCase(String username);

}
