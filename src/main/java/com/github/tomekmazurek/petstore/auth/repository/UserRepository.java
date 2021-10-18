package com.github.tomekmazurek.petstore.auth.repository;

import com.github.tomekmazurek.petstore.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
