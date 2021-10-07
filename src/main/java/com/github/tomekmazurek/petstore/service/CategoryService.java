package com.github.tomekmazurek.petstore.service;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    public CategoryDto addCategory(CategoryDto categoryDto) {
        System.out.println(categoryDto.toString());
        if (categoryDto.getId() != null) {
            Category existingCategory = categoryRepository.getById(categoryDto.getId());
            if (existingCategory == null) {
                return mapToDto(categoryRepository.save(mapToEntity(categoryDto)));
            } else {
                return null;
            }
        }
        return mapToDto(categoryRepository.save(new Category(categoryDto.getName())));
    }

    public List<Category> getMatchingCategories(List<CategoryDto> categoryDtos) {
        return categoryDtos
                .stream()
                .map(categoryDto -> categoryRepository
                        .findById(categoryDto.getId()).orElse(new Category()))
                .filter(category -> category != null)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        Category categoryToBeDeleted = categoryRepository.getById(id);
        if (categoryToBeDeleted != null) {
            categoryRepository.delete(categoryToBeDeleted);
            return true;
        }
        return false;
    }
}
