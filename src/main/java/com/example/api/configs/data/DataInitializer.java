package com.example.api.configs.data;

import com.example.api.domains.User;
import com.example.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        userRepository.deleteAll()
                .thenReturn(userRepository.saveAll(List.of(
                                User.builder()
                                        .username("admin")
                                        .authorities("ROLE_ADMIN,ROLE_USER")
                                        .password("admin")
                                        .build(),
                                User.builder()
                                        .username("user")
                                        .authorities("ROLE_USER")
                                        .password("user")
                                        .build()))
                        .doOnNext(user -> log.info("User: {}", user)));
    }

}