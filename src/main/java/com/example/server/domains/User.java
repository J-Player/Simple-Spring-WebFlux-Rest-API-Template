package com.example.server.domains;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("User")
public class User implements UserDetails {

    @Id
    private UUID id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Builder.Default
    private String authorities = "ROLE_USER"; //Ex.: "ROLE_ADMIN,ROLE_USER"
    @Builder.Default
    @Column("isAccountNonExpired")
    private boolean isAccountNonExpired = true;
    @Builder.Default
    @Column("isAccountNonLocked")
    private boolean isAccountNonLocked = true;
    @Builder.Default
    @Column("isCredentialsNonExpired")
    private boolean isCredentialsNonExpired = true;
    @Builder.Default
    @Column("isEnabled")
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
