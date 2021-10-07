package com.github.tomekmazurek.petstore.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@Setter
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private Integer stockQuantity;
    private Double price;
    private List<CategoryDto> categories;

//    @JsonCreator
//    public ProductDto(@JsonProperty("title") String title,
//                      @JsonProperty("description") String description,
//                      @JsonProperty("stockQuantity") Integer stockQuantity,
//                      @JsonProperty("price") Double price) {
//        this.title = title;
//        this.description = description;
//        this.stockQuantity = stockQuantity;
//        this.price = price;
//    }
//
//    @JsonCreator
//    public ProductDto(@JsonProperty("id") Long id,
//                      @JsonProperty("title") String title,
//                      @JsonProperty("description") String description,
//                      @JsonProperty("stockQuantity") Integer stockQuantity,
//                      @JsonProperty("price") Double price) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.stockQuantity = stockQuantity;
//        this.price = price;
//    }
@JsonCreator
    public ProductDto(@JsonProperty("id") Long id,
                      @JsonProperty("title") String title,
                      @JsonProperty("description") String description,
                      @JsonProperty("stockQuantity") Integer stockQuantity,
                      @JsonProperty("price") Double price,
                      @JsonProperty("categories") List<CategoryDto> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categories = categories;
    }
}
