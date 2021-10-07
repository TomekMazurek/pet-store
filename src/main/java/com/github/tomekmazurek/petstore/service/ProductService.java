package com.github.tomekmazurek.petstore.service;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.model.Product;
import com.github.tomekmazurek.petstore.repository.ProductRepository;
import com.github.tomekmazurek.petstore.service.errorhandling.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.github.tomekmazurek.petstore.mapper.ProductMapper.mapToDto;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Unable to find a product with id=" + id));
    }

    @Transactional
    public ProductDto add(ProductDto productDto) {
        Product existingProduct = null;
        if (productDto.getId() != null) {
            existingProduct = productRepository.getById(productDto.getId());
        }
        if (existingProduct == null) {
            List<CategoryDto> categoriesDto = productDto.getCategories();
            categoriesDto.stream().forEach(categoryDto -> categoryService.addCategory(categoryDto));
            List<Category> categories = categoryService.getMatchingCategories(categoriesDto);
            return mapToDto(productRepository.save(new Product(
                    productDto.getId(),
                    productDto.getTitle(),
                    productDto.getDescription(),
                    productDto.getStockQuantity(),
                    BigDecimal.valueOf(productDto.getPrice()),
                    categories)));
        } else {
            return updateProduct(productDto);
        }
    }

    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        Product productToBeUpdated = productRepository
                .findById(productDto.getId())
                .orElseThrow(() -> new ProductNotFoundException("Unable to find product with given id"));
        productToBeUpdated.setTitle(productDto.getTitle());
        productToBeUpdated.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        productToBeUpdated.setDescription(productDto.getDescription());
        productToBeUpdated.setStockQuantity(productDto.getStockQuantity());
        return mapToDto(productToBeUpdated);
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        Product productToBeDeleted = productRepository.getById(id);
        if (productToBeDeleted != null) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new ProductNotFoundException("Unable to find product with given id");
        }
    }
}
