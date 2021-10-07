package com.github.tomekmazurek.petstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class PetStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
    }

}
