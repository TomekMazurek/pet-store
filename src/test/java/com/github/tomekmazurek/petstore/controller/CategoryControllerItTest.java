package com.github.tomekmazurek.petstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser
class CategoryControllerItTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldReturnListOfCategoriesWithStatus200() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/categories");

        // when
        MvcResult result = mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();
        List<CategoryDto> categories = mapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<CategoryDto>>() {
                });

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isNotZero();
    }

    @Test
    void shouldAddCategoryAndReturnStatusCode200() throws Exception {
        // given
        String newCategory = "{" +
                "\"name\":\"food\"" +
                "}";
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCategory);
        // when
        MvcResult result = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        CategoryDto responseBodyObject = mapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class);

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(responseBodyObject.getName()).isEqualTo("food");
        assertThat(responseBodyObject.getId()).isNotNull();
    }

    @Test
    void shouldDeleteCategoryAndReturnStatus200() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/categories/1");

        // when
        MvcResult result = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void shouldReturnNotFoundWheTryingToDeleteCategoryThatDoesNotExists() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/categories/5");

        // when
        MvcResult result = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void shouldGetSingleCategoryAndReturnStatus200() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/categories/1");

        // when
        MvcResult result = mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        CategoryDto responseCategory = mapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class);

        // then
        assertThat(responseCategory.getId()).isEqualTo(1);
        assertThat(responseCategory.getName()).isEqualTo("dogs");
    }
}