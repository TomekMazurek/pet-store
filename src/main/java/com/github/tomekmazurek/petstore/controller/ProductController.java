package com.github.tomekmazurek.petstore.controller;

import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.model.Product;
import com.github.tomekmazurek.petstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto newProduct) {
        return ResponseEntity.ok(productService.add(newProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeProduct(@PathVariable("id") Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}