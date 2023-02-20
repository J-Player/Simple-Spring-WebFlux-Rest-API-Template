package com.example.api.services.impl;

import com.example.api.domains.User;
import com.example.api.repositories.UserRepository;
import com.example.api.services.IService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements IService<User, Long>, ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    public Mono<User> findByName(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .cast(UserDetails.class);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<User> save(User user) {
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        return userRepository.save(user.withPassword(encode));
    }

    @Override
    public Mono<Void> update(User user) {
        return findById(user.getId())
                .map(userSaved -> {
                    String userSavedPassword = userSaved.getPassword();
                    boolean matches = passwordEncoder.matches(user.getPassword(), userSavedPassword);
                    if (!matches) user.withPassword(passwordEncoder.encode(user.getPassword()));
                    return user;
                })
                .flatMap(userRepository::save)
                .then();
    }

    @Override
    public Mono<Void> delete(Long id) {
        return findById(id)
                .flatMap(userRepository::delete)
                .then();
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}
