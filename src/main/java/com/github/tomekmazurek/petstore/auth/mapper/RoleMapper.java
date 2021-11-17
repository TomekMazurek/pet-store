package com.github.tomekmazurek.petstore.auth.mapper;

import com.github.tomekmazurek.petstore.auth.dto.RoleDto;
import com.github.tomekmazurek.petstore.auth.model.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    private RoleMapper() {
    }

    public static Role mapToEntity(RoleDto roleDto) {
        return new Role(roleDto.getId(), roleDto.getName());
    }

    public static RoleDto mapToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static List<Role> mapListToEntities(Collection<RoleDto> roleDtoList) {
        return roleDtoList.stream().map(RoleMapper::mapToEntity).collect(Collectors.toList());
    }

    public static List<RoleDto> mapListToDtos(Collection<Role> roleList) {
        return roleList.stream().map(RoleMapper::mapToDto).collect(Collectors.toList());
    }
}
