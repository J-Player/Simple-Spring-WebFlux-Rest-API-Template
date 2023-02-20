package com.example.api.utils;

import com.example.api.domains.User;
import com.example.api.domains.dtos.UserDTO;
import com.example.api.mappers.UserMapper;

import java.util.Random;

public class UserCreator {

    private static final Random random = new Random();
    private static final Long ID = random.nextLong();
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
