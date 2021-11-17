package com.github.tomekmazurek.petstore.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan(value = {
        "com.github.tomekmazurek.petstore.model",
        "com.github.tomekmazurek.petstore.auth.model"})
@EnableJpaRepositories(value = {
        "com.github.tomekmazurek.petstore.repository",
        "com.github.tomekmazurek.petstore.auth.repository"
        })
@EnableSwagger2
public class PetStoreConfiguration {

    @Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/v1/**"))
                .apis(RequestHandlerSelectors.basePackage("com.github.tomekmazurek.petstore"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
