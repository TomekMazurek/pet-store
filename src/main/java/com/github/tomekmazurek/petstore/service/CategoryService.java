package com.github.tomekmazurek.petstore.service;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.mapper.CategoryMapper;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.repository.CategoryRepository;
import com.github.tomekmazurek.petstore.service.errorhandling.CategoryNotFoundException;
import com.github.tomekmazurek.petstore.service.errorhandling.InvalidArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.tomekmazurek.petstore.mapper.CategoryMapper.mapToDto;
import static com.github.tomekmazurek.petstore.mapper.CategoryMapper.mapToEntity;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category matchedWithName = categoryRepository.findCategoryByName(categoryDto.getName()).orElse(null);
        if (categoryDto.getId() == null && categoryDto.getName() != null) {
            if (matchedWithName == null) {
                return mapToDto(categoryRepository.save(mapToEntity(categoryDto)));
            }
            return mapToDto(matchedWithName);
        } else if (categoryDto.getId() != null) {
            Category existingCategory = categoryRepository.findById(categoryDto.getId()).orElse(null);
            if (categoryDto.getName() != null &&
                    existingCategory != null &&
                    existingCategory.getName().equals(categoryDto.getName())) {
                return mapToDto(existingCategory);
            }
            if (existingCategory == null) {
                throw new CategoryNotFoundException();
            }
            return mapToDto(existingCategory);
        }
        throw new InvalidArgumentException();
    }

    @Transactional
    public List<Category> getMatchingCategoriesOrAddNew(List<CategoryDto> categoryDtos) {
        return categoryDtos.stream().map(categoryDto -> {
            Category existingCategory = null;
            if (categoryDto.getId() == null) {
                existingCategory = categoryRepository.findCategoryByName(categoryDto.getName()).orElse(null);
            }
            if (categoryDto.getId() != null) {
                existingCategory = categoryRepository.findById(categoryDto.getId()).orElse(null);
            }
            if (existingCategory == null && categoryDto.getName() != null) {
                return categoryRepository.save(new Category(categoryDto.getName()));
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto deleteCategory(Long id) {
        try {
            Category categoryToDelete = categoryRepository.findById(id).orElse(null);
            if (categoryToDelete != null) {
                CategoryDto responseBody = CategoryDto.builder()
                        .id(categoryToDelete.getId())
                        .name(categoryToDelete.getName())
                        .build();
                categoryRepository.deleteById(id);
                return responseBody;
            } else {
                throw new CategoryNotFoundException();
            }
        } catch (Exception exc) {
            throw new CategoryNotFoundException();
        }
    }

    public CategoryDto getSingleCategory(Long id) {
        return CategoryMapper.mapToDto(categoryRepository
                .findById(id)
                .orElseThrow(CategoryNotFoundException::new));
    }
}
