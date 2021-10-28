package com.github.tomekmazurek.petstore.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private AddressDto addressDto;
    private List<ProductDto> products;
    private String status;

    @JsonCreator
    public OrderDto(
            @JsonProperty("id") Long id,
            @JsonProperty("address") AddressDto addressDto,
            @JsonProperty("products") List<ProductDto> products,
            @JsonProperty("status") String status) {
        this.id = id;
        this.addressDto = addressDto;
        this.products = products;
        this.status = status;
    }
}
