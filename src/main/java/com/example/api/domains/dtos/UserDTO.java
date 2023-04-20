package com.example.api.domains.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Builder.Default
    private String authorities = "ROLE_USER"; //Ex.: "ROLE_ADMIN,ROLE_USER"
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;

}
