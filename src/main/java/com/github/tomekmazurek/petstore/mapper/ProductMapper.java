package com.github.tomekmazurek.petstore.mapper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
    }


    public static ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice().doubleValue())
                .categories(product.getCategories() == null ? null : getCategoryDtos(product.getCategories()))
                .build();
    }

    private static CategoryDto buildCategoryDto(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private static List<CategoryDto> getCategoryDtos(List<Category> categories) {
        return categories
                .stream()
                .map(ProductMapper::buildCategoryDto)
                .collect(Collectors.toList());
    }
}
