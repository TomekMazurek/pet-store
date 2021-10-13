package com.github.tomekmazurek.petstore.service;

import com.github.tomekmazurek.petstore.dto.CategoryDto;
import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.mapper.ProductMapper;
import com.github.tomekmazurek.petstore.model.Category;
import com.github.tomekmazurek.petstore.model.Product;
import com.github.tomekmazurek.petstore.repository.ProductRepository;
import com.github.tomekmazurek.petstore.service.errorhandling.ProductAlreadyExistsException;
import com.github.tomekmazurek.petstore.service.errorhandling.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomekmazurek.petstore.mapper.ProductMapper.mapToDto;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    public List<ProductDto> getAll() {
        return productRepository
                .findAll()
                .stream()
                .map(this::buildProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        return ProductMapper.mapToDto(productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new));
    }

    @Transactional
    public ProductDto add(ProductDto productDto) {
        Product existingProduct = null;
        if (productDto.getId() != null) {
            existingProduct = productRepository.getById(productDto.getId());
        }
        if (existingProduct == null) {
            List<CategoryDto> categoriesDto = productDto.getCategories();
            categoriesDto.forEach(categoryService::addCategory);
            List<Category> categories = categoryService.getMatchingCategoriesOrAddNew(categoriesDto);
            return mapToDto(productRepository.save(new Product(
                    productDto.getId(),
                    productDto.getTitle(),
                    productDto.getDescription(),
                    productDto.getStockQuantity(),
                    BigDecimal.valueOf(productDto.getPrice()),
                    categories)));
        } else {
            throw new ProductAlreadyExistsException();
        }
    }

    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        Product productToBeUpdated = productRepository
                .findById(productDto.getId())
                .orElseThrow(ProductNotFoundException::new);
        productToBeUpdated.setTitle(productDto.getTitle());
        productToBeUpdated.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        productToBeUpdated.setDescription(productDto.getDescription());
        productToBeUpdated.setStockQuantity(productDto.getStockQuantity());
        productToBeUpdated.setCategories(categoryService.getMatchingCategoriesOrAddNew(productDto.getCategories()));
        return mapToDto(productToBeUpdated);
    }

    @Transactional
    public ProductDto deleteProduct(Long id) {
        ProductDto response = buildProductDto(productRepository.findById(id).orElseThrow(ProductNotFoundException::new));
        productRepository.deleteById(id);
        return response;
    }

    @Transactional
    public ProductDto decrementQuantity(Long id, int amount) {
        Product productToBeUpdated = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productToBeUpdated.setStockQuantity(productToBeUpdated.getStockQuantity() - amount);
        return mapToDto(productToBeUpdated);
    }

    private ProductDto buildProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice().doubleValue())
                .categories(product.getCategories() == null ? null : getCategoryDtos(product.getCategories()))
                .build();
    }

    private List<CategoryDto> getCategoryDtos(List<Category> categories) {
        return categories
                .stream()
                .map(this::buildCategoryDto)
                .collect(Collectors.toList());
    }

    private CategoryDto buildCategoryDto(Category category) {
            return CategoryDto
                    .builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
    }
}
