package com.github.tomekmazurek.petstore.repository;

import com.github.tomekmazurek.petstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
