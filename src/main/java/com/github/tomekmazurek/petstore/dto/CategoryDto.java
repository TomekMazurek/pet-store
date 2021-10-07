package com.github.tomekmazurek.petstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class CategoryDto {

    private Long id;
    private String name;

    @JsonCreator
    public CategoryDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

//    @JsonCreator
//    public CategoryDto(@JsonProperty("id") Long id) {
//        this.id = id;
//    }
//
//    @JsonCreator
//    public CategoryDto(@JsonProperty("title") String title) {
//        this.title = title;
//    }
}
