package com.github.tomekmazurek.petstore.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {

    private Long id;
    private String name;

    @JsonCreator
    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
