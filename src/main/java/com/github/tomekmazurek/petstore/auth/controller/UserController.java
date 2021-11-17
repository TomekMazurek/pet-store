package com.github.tomekmazurek.petstore.auth.controller;

import com.github.tomekmazurek.petstore.auth.dto.UserDto;
import com.github.tomekmazurek.petstore.auth.mapper.UserMapper;
import com.github.tomekmazurek.petstore.auth.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getUsers() {
        return ResponseEntity.ok(UserMapper.mapUsersToDtos(userService.getUsers()));
    }

    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(UserMapper.mapToDto(userService.saveUser(user)));
    }
}
