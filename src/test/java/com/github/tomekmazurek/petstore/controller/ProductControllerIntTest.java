package com.github.tomekmazurek.petstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class ProductControllerIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Order(1)
    @Test
    void shouldDecrementProductQuantityAndReturnUpdatedProductDtoAndStatus200() throws Exception {
        // given
        ProductDto productBeforeUpdatingQuantity = mapper.readValue(
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/1"))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                ProductDto.class);
        // when
        RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/products/1").param("amount", "2");
        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andReturn();
        ProductDto responseBody = mapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);

        // then
        assertThat(responseBody.getStockQuantity()).isNotEqualTo(productBeforeUpdatingQuantity.getStockQuantity());
        assertThat(responseBody.getTitle()).isEqualTo(productBeforeUpdatingQuantity.getTitle());
        assertThat(responseBody.getStockQuantity()).isEqualTo(productBeforeUpdatingQuantity.getStockQuantity() - 2);
    }

    @Order(2)
    @Test
    void shouldReturnStatusCode200WhenAccessingGetProducts() throws Exception {
        // when
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products");
        MvcResult result = mockMvc
                .perform(request)
                .andDo(print())
                .andReturn();
        List<ProductDto> responseBody = mapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<ProductDto>>() {
                });

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isNotEqualTo("[]");

    }

    @Order(3)
    @Test
    void shouldReturnStatusCode200WhenAddingNewProduct() throws Exception {
        // given
        String requestBody = getRequestBody();

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/products").content(requestBody).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andDo(print()).andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("Leash 5m");
    }

    @Order(4)
    @Test
    void shouldReturnConflictStatusWhenTryingToAddProductThatAlreadyExist() throws Exception {
        // given
        String requestBody = "{\n" +
                "    \"id\":1," +
                "    \"title\":\"Leash 5m\",\n" +
                "    \"description\":\"Very strong leash for large dogs\",\n" +
                "    \"price\": 18.99,\n" +
                "    \"stockQuantity\":100,\n" +
                "    \"categories\":[\n" +
                "        {\"id\":1},\n" +
                "        {\"id\":2}\n" +
                "    ]\n" +
                "}";

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/products").content(requestBody).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andDo(print()).andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(409);
    }

    @Order(5)
    @Test
    void shouldUpdateProductAndReturnCode200() throws Exception {
        // given
        String requestBody = "{\n" +
                "    \"id\":1," +
                "    \"title\":\"Leash 5m\",\n" +
                "    \"description\":\"Very strong leash for medium dogs\",\n" +
                "    \"price\": 18.99,\n" +
                "    \"stockQuantity\":100,\n" +
                "    \"categories\":[\n" +
                "        {\"id\":1},\n" +
                "        {\"id\":2}\n" +
                "    ]\n" +
                "}";

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/products")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andDo(print()).andReturn();
        ProductDto response = mapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(response.getTitle()).contains("Leash").doesNotContain("collar");
    }

    @Order(6)
    @Test
    void shouldDeleteProductAndReturnStatusCode200() throws Exception {
        // given
        List<ProductDto> allProducts = getCurrentProducts();
        Long id = getRandomId(allProducts);

        // when
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/products/" + id);
        MvcResult result = mockMvc.perform(request).andDo(print()).andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Order(7)
    @Test
    void shouldReturnNotFoundWhenTryingToDeleteProductThatDoesntExist() throws Exception {
        // given
        List<ProductDto> currentProducts = getCurrentProducts();

        // when
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/products/" + currentProducts.size() + 1);
        MvcResult result = mockMvc.perform(request).andReturn();
        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(404);

    }

    private String getRequestBody() {
        return "{\n" +
                "    \"title\":\"Leash 5m\",\n" +
                "    \"price\": 18.99,\n" +
                "    \"stockQuantity\":100,\n" +
                "    \"description\":\"Very strong leash for large dogs\",\n" +
                "    \"categories\":[\n" +
                "        {\"id\":1, \"name\":\"dogs\"},\n" +
                "        {\"id\":2, \"name\":\"accessories\"}\n" +
                "    ]\n" +
                "}";
    }


    private Long getRandomId(List<ProductDto> allProducts) {
        ProductDto randomProductToBeDeleted = allProducts.get((int) Math.round(Math.random() * (allProducts.size() - 1)));
        return randomProductToBeDeleted.getId();
    }

    private List<ProductDto> getCurrentProducts() throws Exception {
        RequestBuilder requestAllProducts = MockMvcRequestBuilders.get("/api/v1/products");
        return mapper.readValue(
                mockMvc
                        .perform(requestAllProducts)
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<List<ProductDto>>() {

                });
    }
}
