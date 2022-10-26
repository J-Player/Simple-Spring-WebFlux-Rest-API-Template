package com.example.server.utils;

import com.example.server.domains.User;
import com.example.server.domains.dtos.UserDTO;
import com.example.server.mappers.UserMapper;

import java.util.UUID;

public class UserCreator {

    private static final UUID ID = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AUTHORITIES = "ROLE_ADMIN,ROLE_USER";

    public static User createUser() {
        return UserMapper.INSTANCE.toUser(createUserDTO()).withId(ID);
    }

    public static UserDTO createUserDTO() {
        return UserDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(AUTHORITIES)
                .build();
    }

}
