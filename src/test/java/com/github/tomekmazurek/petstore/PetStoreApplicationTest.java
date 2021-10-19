package com.github.tomekmazurek.petstore;

import com.github.tomekmazurek.petstore.auth.controller.UserController;
import com.github.tomekmazurek.petstore.auth.service.UserServiceImpl;
import com.github.tomekmazurek.petstore.controller.CategoryController;
import com.github.tomekmazurek.petstore.controller.ProductController;
import com.github.tomekmazurek.petstore.service.CategoryService;
import com.github.tomekmazurek.petstore.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PetStoreApplicationTest {

    @Autowired
    ProductController productController;
    @Autowired
    CategoryController categoryController;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserController userController;
    @Autowired
    UserServiceImpl userService;

    @Test
    void contextLoads() {
        assertThat(productController).isNotNull();
        assertThat(categoryController).isNotNull();
        assertThat(productService).isNotNull();
        assertThat(categoryService).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(userService).isNotNull();
    }
}
