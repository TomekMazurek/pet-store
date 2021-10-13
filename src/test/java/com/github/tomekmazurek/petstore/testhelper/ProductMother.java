package com.github.tomekmazurek.petstore.testhelper;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductMother {
    private ProductMother(){}

    public static ProductDto createMockProductDto(){
        return new ProductDto(
                0L,
                "collar",
                "dog collar",
                10,
                10.99,
                Collections.singletonList(new CategoryDto(1L, "dogs")));
    }

    public static List<ProductDto> createMockProductDtos(){
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

}
