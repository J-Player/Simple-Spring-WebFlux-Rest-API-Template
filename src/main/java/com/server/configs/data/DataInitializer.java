package com.server.configs.data;

import com.server.domains.User;
import com.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        userRepository.deleteAll()
                .thenReturn(userRepository.saveAll(List.of(
                                User.builder()
                                        .username("admin")
                                        .authorities("ROLE_ADMIN,ROLE_USER")
                                        .password(passwordEncoder.encode("admin"))
                                        .build(),
                                User.builder()
                                        .username("user")
                                        .authorities("ROLE_USER")
                                        .password(passwordEncoder.encode("user"))
                                        .build()))
                        .thenMany(userRepository.findAll()));
    }

}