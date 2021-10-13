package com.github.tomekmazurek.petstore.testhelper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryMother {
private CategoryMother(){}

    public static CategoryDto createMockCategoryDto(){
        return new CategoryDto(1L, "accessories");
    }

    public static List<CategoryDto> createMockCategoryDtos(){
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto(1L, "dogs"));
        categories.add(new CategoryDto(2L, "cats"));
        return categories;
    }
}
