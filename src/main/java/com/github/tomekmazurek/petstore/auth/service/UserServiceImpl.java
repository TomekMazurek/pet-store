package com.github.tomekmazurek.petstore.auth.service;

import com.github.tomekmazurek.petstore.auth.model.Role;
import com.github.tomekmazurek.petstore.auth.model.User;
import com.github.tomekmazurek.petstore.auth.repository.RoleRepository;
import com.github.tomekmazurek.petstore.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("Saving user to the database");
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role to the database");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Accessing the");
        User user = userRepository.findByUsername(username).orElse(null);
        Role role = roleRepository.findRoleByName(roleName).orElse(null);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
