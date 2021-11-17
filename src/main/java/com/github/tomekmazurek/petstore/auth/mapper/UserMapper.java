package com.github.tomekmazurek.petstore.auth.mapper;

import com.github.tomekmazurek.petstore.auth.dto.UserDto;
import com.github.tomekmazurek.petstore.auth.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static User mapToEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getUsername(), userDto.getPassword(), RoleMapper.mapListToEntities(userDto.getRoles()));
    }

    public static UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(RoleMapper.mapListToDtos(user.getRoles()))
                .build();
    }

    public static Collection<UserDto> mapUsersToDtos(Collection<User> users) {
        return users.stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }
}
