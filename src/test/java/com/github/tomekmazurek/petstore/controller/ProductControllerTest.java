package com.github.tomekmazurek.petstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.service.ProductService;
import com.github.tomekmazurek.petstore.service.errorhandling.ProductAlreadyExistsException;
import com.github.tomekmazurek.petstore.service.errorhandling.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(ProductController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @Test
    void shouldReturnListOfProductsWithStatus200() throws Exception {
        // given
        List<ProductDto> mockProducts = getMockProducts();
        when(productService.getAll()).thenReturn(mockProducts);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/products"))
                .andDo(print())
                .andReturn();
        List<ProductDto> responseProductList = mapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<ProductDto>>() {
                });

        // then
        assertThat(responseProductList.size()).isEqualTo(mockProducts.size());
        assertThat(responseProductList.get(0).getTitle()).isEqualTo(mockProducts.get(0).getTitle());
    }

    @Test
    void shouldAddProductAndReturnProductDtoWithStatus200() throws Exception {
        // given
        ProductDto mockProduct = getMockProduct();
        when(productService.add(any(ProductDto.class))).thenReturn(mockProduct);

        // when
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProduct)))
                .andDo(print())
                .andReturn();
        ProductDto responseProduct = mapper.readValue(
                result.getResponse().getContentAsString(),
                ProductDto.class);

        // then
        assertThat(responseProduct.getTitle()).isEqualTo(mockProduct.getTitle());
    }

    @Test
    void shouldReturnStatus409WhenAddingProductThatAlreadyExists() throws Exception {
        // given
        ProductDto mockProduct = getMockProduct();
        when(productService.add(any(ProductDto.class))).thenThrow(ProductAlreadyExistsException.class);

        // when
        MvcResult result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProduct)))
                .andDo(print())
                .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(409);
    }

    @Test
    void shouldReturnDeletedProductDtoAndStatus200() throws Exception {
        // given
        ProductDto mockProduct = getMockProduct();
        when(productService.deleteProduct(anyLong())).thenReturn(mockProduct);

        // when
        MvcResult result = mockMvc.perform(delete("/api/v1/products/1"))
                .andDo(print())
                .andReturn();
        ProductDto responseBody = mapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);

        // then
        assertThat(responseBody.getTitle()).isEqualTo(mockProduct.getTitle());
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void shouldReturnStatus404WhenTryingToDeleteProductWithInvalidId() throws Exception {
        // given
        when(productService.deleteProduct(anyLong())).thenThrow(ProductNotFoundException.class);

        // when
        MvcResult result = mockMvc.perform(delete("/api/v1/products/1"))
                .andDo(print())
                .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void shouldUpdateProductAndReturnStatus200AndUpdatedProductDto() throws Exception {
        // given
        ProductDto mockProduct = getMockProduct();
        ProductDto requestProduct = getMockProduct();
        requestProduct.setTitle("before update");
        when(productService.updateProduct(any(ProductDto.class))).thenReturn(mockProduct);

        // when
        MvcResult result = mockMvc.perform(put("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestProduct)))
                .andDo(print())
                .andReturn();
        ProductDto updatedProduct = mapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);

        // then
        assertThat(updatedProduct.getTitle()).isNotEqualTo(requestProduct.getTitle());
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(updatedProduct.getDescription()).isEqualTo(requestProduct.getDescription());
    }

    private ProductDto getMockProduct() {
        return new ProductDto(
                0L,
                "collar",
                "dog collar",
                10,
                10.99,
                Collections.singletonList(new CategoryDto(1L, "dogs")));
    }

    private List<ProductDto> getMockProducts() {
        List<ProductDto> mockProducts = new ArrayList<>();
        mockProducts.add(getMockProduct());
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