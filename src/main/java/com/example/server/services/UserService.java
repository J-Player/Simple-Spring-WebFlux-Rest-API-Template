package com.example.server.services;

import com.example.server.domains.User;
import com.example.server.repositories.UserRepository;
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
public class UserService implements AbstractService<User, Integer>, ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> findById(Integer id) {
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
                .flatMap(userRepository::save)
                .then();
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return findById(id)
                .flatMap(userRepository::delete)
                .then();
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}
