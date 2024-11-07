package com.example.demo.util;

import com.example.demo.mappers.UserMapper;
import com.example.demo.models.entities.User;
import com.example.demo.models.enums.UserRole;
import com.example.demo.models.security.RegisterRequest;

public abstract class UserCreator {

    public static final int adminId = 0;
    public static final String adminUsername = "admin";
    public static final String adminPassword = "admin";

    public static final int userId = 1;
    public static final String userUsername = "user";
    public static final String userPassword = "user";

    public static final int userToReadId = 2;
    public static final String userToReadUsername = "user_to_read";

    public static final int userToUpdateId = 3;
    public static final String userToUpdateUsername = "user_to_update";

    public static final int userToDeleteId = 4;
    public static final String userToDeleteUsername = "user_to_delete";

    public static User admin() {
        return User.builder()
                .id(adminId)
                .username(userUsername)
                .password(userPassword)
                .role(UserRole.ADMIN)
                .build();
    }

    public static User user() {
        return User.builder()
                .id(userId)
                .username(userUsername)
                .password(userPassword)
                .role(UserRole.USER)
                .build();
    }

    public static RegisterRequest registerRequest() {
        return UserMapper.INSTANCE.toRegisterRequest(user());
    }

    public static User invalidUser() {
        return user().withUsername(null);
    }

    public static User userToRead() {
        return user().withId(userToReadId).withUsername(userToReadUsername);
    }

    public static User userToUpdate() {
        return user().withId(userToUpdateId).withUsername(userToUpdateUsername);
    }

    public static User userToDelete() {
        return user().withId(userToDeleteId).withUsername(userToDeleteUsername);
    }
}
