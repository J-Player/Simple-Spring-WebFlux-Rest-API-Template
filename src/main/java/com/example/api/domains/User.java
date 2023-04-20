package com.example.api.domains;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("User")
public class User implements UserDetails {

    @Id
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Builder.Default
    private String authorities = "ROLE_USER"; //Ex.: "ROLE_ADMIN,ROLE_USER"
    @Builder.Default
    @Column("accountNonExpired")
    private boolean accountNonExpired = true;
    @Builder.Default
    @Column("accountNonLocked")
    private boolean accountNonLocked = true;
    @Builder.Default
    @Column("credentialsNonExpired")
    private boolean credentialsNonExpired = true;
    @Builder.Default
    @Column("enabled")
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
