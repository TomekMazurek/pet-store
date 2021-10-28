package com.github.tomekmazurek.petstore.order.service;

import com.github.tomekmazurek.petstore.order.repository.AddressRepository;
import com.github.tomekmazurek.petstore.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private AddressRepository addressRepository;
    private OrderRepository orderRepository;


}
