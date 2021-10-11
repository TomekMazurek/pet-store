package com.github.tomekmazurek.petstore.controller;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getSingleCategory(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
