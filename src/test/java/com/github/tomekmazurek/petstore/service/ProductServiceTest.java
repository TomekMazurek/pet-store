package com.github.tomekmazurek.petstore.service;

import com.github.tomekmazurek.petstore.dto.ProductDto;
import com.github.tomekmazurek.petstore.model.Product;
import com.github.tomekmazurek.petstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.github.tomekmazurek.petstore.testhelper.ProductMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private ProductService productService;


    @Test
    void shouldReturnListOfProductsWhenGetAllCalled() {
        // given
        List<Product> mockProducts = createMockProductEntities();
        when(productRepository.findAll()).thenReturn(mockProducts);

        // when
        List<ProductDto> products = productService.getAll();

        // then
        assertThat(products.size()).isEqualTo(mockProducts.size());
        assertThat(products.get(0).getTitle()).isEqualTo(mockProducts.get(0).getTitle());
    }

    @Test
    void shouldReturnProductDtoWhenFindByIdCalled() {
        // given
        Product mockProduct = createMockProductEntity();
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockProduct));

        // when
        ProductDto foundProduct = productService.findById(2L);

        // then
        assertThat(foundProduct.getTitle()).isEqualTo(mockProduct.getTitle());
    }

    @Test
    void shouldReturnProductDtoWhenAddCalled() {
        // given
        Product mockProductEntity = createMockProductEntity();
        ProductDto mockProductDto = createMockProductDto();
        when(productRepository.save(any(Product.class))).thenReturn(mockProductEntity);

        // when
        ProductDto addedProduct = productService.add(mockProductDto);

        // then
        assertThat(addedProduct.getTitle()).isEqualTo(mockProductDto.getTitle());
    }

    @Test
    void shouldReturnDeletedProductDto() {
        // given
        Product mockProductEntity = createMockProductEntity();
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockProductEntity));

        // when
        ProductDto deletedProduct = productService.deleteProduct(2L);

        // then
        assertThat(deletedProduct.getTitle()).isEqualTo(mockProductEntity.getTitle());
    }
}
