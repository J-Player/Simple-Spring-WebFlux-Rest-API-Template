package com.example.server.utils;

import com.example.server.domains.User;

public class UserCreator {

    private static final int ID = 1;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AUTHORITIES = "ROLE_ADMIN,ROLE_USER";

    public static User createValidUser() {
        return User.builder()
                .id(ID)
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(AUTHORITIES)
                .build();
    }

    public static User createToSaved() {
        return User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(AUTHORITIES)
                .build();
    }

}
