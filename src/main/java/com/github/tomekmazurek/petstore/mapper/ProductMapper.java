package com.github.tomekmazurek.petstore.mapper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice().doubleValue())
                .stockQuantity(product.getStockQuantity())
                .categories(product.getCategories() == null ? new ArrayList<>() : product
                        .getCategories()
                        .stream()
                        .map(category -> CategoryDto.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
