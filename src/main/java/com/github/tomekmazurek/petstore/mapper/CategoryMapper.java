package com.github.tomekmazurek.petstore.mapper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.model.Category;

public class CategoryMapper {

    public static CategoryDto mapToDto(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        if (categoryDto.getId() == null) {
            return new Category(categoryDto.getName());
        }
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
