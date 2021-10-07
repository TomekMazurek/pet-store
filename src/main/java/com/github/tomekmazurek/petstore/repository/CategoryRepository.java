package com.github.tomekmazurek.petstore.repository;

import com.github.tomekmazurek.petstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
