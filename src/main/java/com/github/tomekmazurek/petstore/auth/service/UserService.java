package com.github.tomekmazurek.petstore.auth.service;

import com.github.tomekmazurek.petstore.auth.dto.UserDto;
import com.github.tomekmazurek.petstore.auth.model.Role;
import com.github.tomekmazurek.petstore.auth.model.User;

import java.util.List;


public interface UserService {

    User saveUser(UserDto user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String role);

    User getUser(String username);

    List<User> getUsers();

}
