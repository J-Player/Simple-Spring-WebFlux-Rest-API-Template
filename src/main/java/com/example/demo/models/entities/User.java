package com.example.demo.models.entities;

import static com.example.demo.models.enums.UserRole.ADMIN;
import static com.example.demo.models.enums.UserRole.USER;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.configs.web.deserializers.InstantJsonDeserializer;
import com.example.demo.configs.web.serializers.InstantJsonSerializer;
import com.example.demo.models.enums.UserRole;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@Builder
@Table("Users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    private Integer id;

    @NotEmpty(message = "The 'username' cannot be empty or null")
    private String username;

    @NotEmpty(message = "The 'password' cannot be empty or null")
    private String password;

    @Builder.Default
    @NotNull(message = "The 'role' cannot be empty or null")
    @Column("role")
    private UserRole role = USER;

    @Builder.Default
    @Column("accountNonLocked")
    private boolean accountNonLocked = true;

    @Builder.Default
    @Column("accountNonExpired")
    private boolean accountNonExpired = true;

    @Builder.Default
    @Column("credentialsNonExpired")
    private boolean credentialsNonExpired = true;

    @Builder.Default
    @Column("enabled")
    private boolean enabled = true;

    @Column("created_at")
    @CreatedDate
    @JsonDeserialize(using = InstantJsonDeserializer.class)
    @JsonSerialize(using = InstantJsonSerializer.class)
    private Instant createdAt;

    @Column("updated_at")
    @LastModifiedDate
    @JsonDeserialize(using = InstantJsonDeserializer.class)
    @JsonSerialize(using = InstantJsonSerializer.class)
    private Instant updatedAt;

    @Hidden
    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(2);
        if (role == ADMIN) {
            authorities.add(new SimpleGrantedAuthority(ADMIN.getRole()));
            authorities.add(new SimpleGrantedAuthority(USER.getRole()));
        } else {
            authorities.add(new SimpleGrantedAuthority(USER.getRole()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
