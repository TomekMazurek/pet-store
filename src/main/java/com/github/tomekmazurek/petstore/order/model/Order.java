package com.github.tomekmazurek.petstore.order.model;

import com.github.tomekmazurek.petstore.model.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Address adress;
    @OneToMany
    private List<Product> products;
    private String status;

}
