package com.github.tomekmazurek.petstore.order.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "adresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String phoneNumber;
}
