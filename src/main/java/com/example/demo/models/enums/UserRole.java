package com.example.demo.models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    public String getRole() {
        return "ROLE_" + role.toUpperCase();
    }

}
