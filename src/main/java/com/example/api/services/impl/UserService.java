package com.example.api.services.impl;

import com.example.api.domains.User;
import com.example.api.domains.dtos.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.IService;
import com.example.api.utils.MapperUtil;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IService<User, UserDTO>, ReactiveUserDetailsService {

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
    public Mono<User> save(UserDTO userDTO) {
        return Mono.just(userDTO)
                .map(dto -> MapperUtil.MAPPER.map(dto, User.class))
                .flatMap(userRepository::save);
    }

    @Override
    @Transactional
    public Mono<Void> update(UserDTO userDTO, Long id) {
        return findById(id)
                .doOnNext(userSaved -> MapperUtil.MAPPER.map(userDTO, userSaved))
                .flatMap(userRepository::save)
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long id) {
        return findById(id)
                .flatMap(userRepository::delete)
                .then();
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}
