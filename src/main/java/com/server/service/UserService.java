package com.server.service;

import com.server.domain.User;
import com.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements AbstractService<User, Integer> {

    private final UserRepository userRepository;

    @Override
    public Mono<User> findById(Integer id) {
        return userRepository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> update(User user) {
        return findById(user.getId())
                .flatMap(userRepository::save)
                .then();
    }

    @Override
    public Mono<Void> delete(User user) {
        return findById(user.getId())
                .flatMap(userRepository::delete)
                .then();
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}
