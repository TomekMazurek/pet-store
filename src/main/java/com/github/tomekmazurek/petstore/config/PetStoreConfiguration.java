package com.github.tomekmazurek.petstore.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.github.tomekmazurek.petstore"})
@EntityScan("com.github.tomekmazurek.petstore.model")
@EnableJpaRepositories("com.github.tomekmazurek.petstore.repository")
public class PetStoreConfiguration {

}
