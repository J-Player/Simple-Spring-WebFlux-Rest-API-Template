package com.example.api.configs.data;

import com.example.api.domains.User;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserCallback implements BeforeConvertCallback<User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Publisher<User> onBeforeConvert(User entity, SqlIdentifier table) {
        return Mono.just(encrypt(entity));
    }

    private User encrypt(User entity) {
        return entity.withPassword(passwordEncoder.encode(entity.getPassword()));
    }

}
