package com.github.tomekmazurek.petstore.order.repository;

import com.github.tomekmazurek.petstore.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
