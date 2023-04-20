package com.example.api.utils;

import com.example.api.domains.User;
import com.example.api.domains.dtos.UserDTO;

public class UserCreator {

    private static final Long ID = 1L;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AUTHORITIES = "ROLE_ADMIN,ROLE_USER";

    public static User user() {
        return User.builder()
                .id(ID)
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(AUTHORITIES)
                .build();
    }

    public static UserDTO userDTO() {
        return MapperUtil.MAPPER.map(user(), UserDTO.class);
    }

    public static UserDTO userToSave() {
        return userDTO().withUsername(USERNAME.concat("_Save"));
    }

    public static User userToUpdate() {
        return user().withId(2L).withUsername(USERNAME.concat("_Update"));
    }

    public static User userToDelete() {
        return user().withId(3L).withUsername(USERNAME.concat("_Delete"));
    }

}
