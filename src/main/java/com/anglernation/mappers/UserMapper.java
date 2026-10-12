package com.anglernation.mappers;

import com.anglernation.dtos.UserDto;
import com.anglernation.dtos.CreateUserRequest;
import com.anglernation.dtos.UpdateUserRequest;
import com.anglernation.models.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "passWord")
    @Mapping(target = "rods", ignore = true)
    @Mapping(target = "lures", ignore = true)
    @Mapping(target = "lines", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "catches", ignore = true)
    User toEntity(CreateUserRequest request);

    @Mapping(target = "password", source = "passWord")
    @Mapping(target = "rods", ignore = true)
    @Mapping(target = "lures", ignore = true)
    @Mapping(target = "lines", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "catches", ignore = true)
    void updateEntityFromDto(UpdateUserRequest request, @MappingTarget User user);

    @Mapping(target = "createdAt", source = "createdAt")
    UserDto toDto(User user);
}