package com.github.tomekmazurek.petstore.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private List<RoleDto> roles;

    @JsonCreator

    public UserDto(Long id, String name, String username, String password, List<RoleDto> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
