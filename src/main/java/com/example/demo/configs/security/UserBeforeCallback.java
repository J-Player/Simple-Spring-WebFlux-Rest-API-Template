package com.example.demo.configs.security;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.models.entities.User;
import reactor.core.publisher.Mono;

@Component
public class UserBeforeCallback implements BeforeConvertCallback<User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public @NonNull Publisher<User> onBeforeConvert(@NonNull User entity, @NonNull SqlIdentifier table) {
        return Mono.just(entity)
                .doOnNext(user -> passwordEncoder.upgradeEncoding(user.getPassword()))
                .onErrorResume(IllegalArgumentException.class, e -> Mono.just(entity
                        .withPassword(passwordEncoder.encode(entity.getPassword()))));
    }

}
