package com.github.tomekmazurek.petstore.testhelper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.mapper.CategoryMapper;
import com.github.tomekmazurek.petstore.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMother {

    private CategoryMother() {
    }

    public static CategoryDto createMockCategoryDto() {
        return new CategoryDto(1L, "accessories");
    }

    public static List<CategoryDto> createMockCategoryDtos() {
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto(1L, "dogs"));
        categories.add(new CategoryDto(2L, "cats"));
        return categories;
    }

    public static Category createMockCategoryEntity() {
        return CategoryMapper.mapToEntity(createMockCategoryDto());
    }

    public static List<Category> createMockCategoryEntities() {
        return createMockCategoryDtos()
                .stream()
                .map(CategoryMapper::mapToEntity)
                .collect(Collectors.toList());
    }
}
