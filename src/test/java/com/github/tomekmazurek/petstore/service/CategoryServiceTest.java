package com.github.tomekmazurek.petstore.service;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.repository.CategoryRepository;
import com.github.tomekmazurek.petstore.service.errorhandling.CategoryNotFoundException;
import com.github.tomekmazurek.petstore.service.errorhandling.InvalidArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.tomekmazurek.petstore.testhelper.CategoryMother.createMockCategoryDto;
import static com.github.tomekmazurek.petstore.testhelper.CategoryMother.createMockCategoryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;


    @Test
    void shouldAddCategoryThatDoesNotHaveNameButHaveIdThatExistsInDb() {
        // given
        Category mockCategoryEntity = createMockCategoryEntity();
        CategoryDto mockCategoryDto = createMockCategoryDto();
        mockCategoryDto.setName(null);
        when(categoryRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockCategoryEntity));

        // when
        CategoryDto addedCategory = categoryService.addCategory(mockCategoryDto);

        // then
        assertThat(addedCategory.getName()).isEqualTo(mockCategoryEntity.getName());
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenCategoryArgumentsAreNull() {
        // given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        CategoryDto categoryDto = CategoryDto.builder().id(4L).build();
        // when

        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.addCategory(categoryDto));
    }
}