package com.github.tomekmazurek.petstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.service.CategoryService;
import com.github.tomekmazurek.petstore.service.errorhandling.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void shouldReturnListOfCategories() throws Exception {
        // given
        List<CategoryDto> mockCategories = getMockCategories();
        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        // when
        MvcResult result = mockMvc
                .perform(get("/api/v1/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> results = mapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<CategoryDto>>() {
                });

        // then
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(0).getName()).isEqualTo(mockCategories.get(0).getName());
    }

    @Test
    void shouldReturnSingleCategory() throws Exception {
        // given
        CategoryDto mockCategory = getMockCategory();
        when(categoryService.getSingleCategory(anyLong())).thenReturn(mockCategory);

        // when
        MvcResult result = mockMvc
                .perform(get("/api/v1/categories/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto responseCategory = mapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class);

        // then
        assertThat(responseCategory.getName()).isEqualTo(mockCategory.getName());
        assertThat(responseCategory.getId()).isEqualTo(1);

    }

    @Test
    void shouldThrowCategoryNotFoundExceptionAndReturnStatus404WhenGettingCategoryThatDoesntExists() throws Exception {
        // given
        when(categoryService.getSingleCategory(anyLong())).thenThrow(new CategoryNotFoundException());

        // when
        MvcResult result = mockMvc
                .perform(get("/api/v1/categories/1"))
                .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void shouldAddCategoryAndReturnCategoryDtoAndStatus200() throws Exception {
        // given
        CategoryDto mockCategory = getMockCategory();
        when(categoryService.addCategory(any(CategoryDto.class))).thenReturn(mockCategory);

        // when
        MvcResult result = mockMvc
                .perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockCategory)))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto responseCategory = mapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class);

        // then
        assertThat(responseCategory.getId()).isEqualTo(mockCategory.getId());
        assertThat(responseCategory.getName()).isEqualTo(mockCategory.getName());
    }

    @Test
    void shouldDeleteCategoryAndReturnStatus200AndDeletedCategoryDto() throws Exception {
        // given
        CategoryDto mockCategory = getMockCategory();
        when(categoryService.deleteCategory(anyLong())).thenReturn(mockCategory);

        // when
        MvcResult result = mockMvc
                .perform(delete("/api/v1/categories/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto deletedCategory = mapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class);

        // then
        assertThat(deletedCategory.getName()).isEqualTo(mockCategory.getName());
    }

    private List<CategoryDto> getMockCategories() {
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto(1L, "dogs"));
        categories.add(new CategoryDto(2L, "cats"));
        return categories;
    }

    private CategoryDto getMockCategory() {
        return new CategoryDto(1L, "accessories");
    }
}