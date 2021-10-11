package com.github.tomekmazurek.petstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    private BigDecimal price;
    @ManyToMany( fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @Nullable
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Product(String title, String description, Integer stockQuantity, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    public Product(Long id, String title, String description, Integer stockQuantity, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }
}
