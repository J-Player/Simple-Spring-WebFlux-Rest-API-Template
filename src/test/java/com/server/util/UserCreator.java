package com.server.util;

import com.server.domain.User;

public class UserCreator {

    private static final int id = 1;
    private static final String name = "name";
    private static final String authorities = "ROLE_ADMIN,ROLE_USER";
    private static final String username = "username";
    private static final String password = "password";

    public static User createValidUser() {
        return User.builder()
                .id(id)
                .name(name)
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }

    public static User createToSaved() {
        return User.builder()
                .name(name)
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }

}
