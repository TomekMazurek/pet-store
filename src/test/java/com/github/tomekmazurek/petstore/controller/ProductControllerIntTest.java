package com.github.tomekmazurek.petstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class ProductControllerIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldReturnStatusCode200WhenAccessingGetProducts() throws Exception {
        // when
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products");
        MvcResult result = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        List<ProductDto> responseBody = mapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<ProductDto>>() {
                });

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isNotEqualTo("[]");

    }

    @Test
    void shouldReturnStatusCode200WhenAddingNewProduct() throws Exception {
        // given
        String requestBody = "{\n" +
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
        MvcResult result = mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("Leash 5m");
    }

    @Test
    void shouldDeleteProductAndReturnStatusCode200() throws Exception {
        // given
        RequestBuilder requestAllProducts = MockMvcRequestBuilders.get("/api/v1/products");
        List<ProductDto> allProducts = mapper.readValue(
                mockMvc
                        .perform(requestAllProducts)
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<List<ProductDto>>() {
                });
        ProductDto randomProductToBeDeleted = allProducts.get(0);
        Long id = randomProductToBeDeleted.getId();

        // when
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/products/"+id);
        MvcResult result = mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void shouldReturnNotFoundWhenTryingToDeleteProductThatDoesntExist(){
        // given

        // when

        // then
    }

}
