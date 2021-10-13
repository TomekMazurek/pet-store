package com.github.tomekmazurek.petstore.testhelper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.mapper.CategoryMapper;
import com.github.tomekmazurek.petstore.mapper.ProductMapper;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMother {

    private ProductMother() {
    }

    public static ProductDto createMockProductDto() {
        return new ProductDto(
                0L,
                "collar",
                "dog collar",
                10,
                10.99,
                Collections.singletonList(new CategoryDto(1L, "dogs")));
    }

    public static List<ProductDto> createMockProductDtos() {
        List<ProductDto> mockProducts = new ArrayList<>();
        mockProducts.add(createMockProductDto());
        mockProducts.add(new ProductDto(
                1L,
                "leash",
                "leash for dogs",
                4,
                16.50,
                Collections.singletonList(new CategoryDto(2L, "accessories"))));

        return mockProducts;
    }

    public static Product createMockProductEntity() {
        return new Product(
                createMockProductDto().getId(),
                createMockProductDto().getTitle(),
                createMockProductDto().getDescription(),
                createMockProductDto().getStockQuantity(),
                BigDecimal.valueOf(createMockProductDto().getPrice()),
                createMockProductDto().getCategories().stream()
                        .map(CategoryMapper::mapToEntity)
                        .collect(Collectors.toList()));
    }

    public static List<Product> createMockProductEntities() {
        return createMockProductDtos().stream().map(productDto -> new Product(productDto.getId(),
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getStockQuantity(),
                BigDecimal.valueOf(productDto.getPrice()),
                productDto.getCategories().stream()
                        .map(CategoryMapper::mapToEntity)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

}
