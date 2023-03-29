package com.example.api.mappers;

import com.example.api.domains.User;
import com.example.api.domains.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "authorities", source = "dto.authorities")
    @Mapping(target = "accountNonExpired", source = "dto.accountNonExpired")
    @Mapping(target = "accountNonLocked", source = "dto.accountNonLocked")
    @Mapping(target = "credentialsNonExpired", source = "dto.credentialsNonExpired")
    @Mapping(target = "enabled", source = "dto.enabled")
    User toUser(UserDTO dto);

}
