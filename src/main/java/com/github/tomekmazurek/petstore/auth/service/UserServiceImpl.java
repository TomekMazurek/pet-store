package com.github.tomekmazurek.petstore.auth.service;

import com.github.tomekmazurek.petstore.auth.dto.RoleDto;
import com.github.tomekmazurek.petstore.auth.dto.UserDto;
import com.github.tomekmazurek.petstore.auth.mapper.RoleMapper;
import com.github.tomekmazurek.petstore.auth.mapper.UserMapper;
import com.github.tomekmazurek.petstore.auth.model.Role;
import com.github.tomekmazurek.petstore.auth.model.User;
import com.github.tomekmazurek.petstore.auth.repository.RoleRepository;
import com.github.tomekmazurek.petstore.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.error("User not found: " + username);
            throw new UsernameNotFoundException("User not found:" + username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(UserDto user) {
        log.info("Saving user to the database");
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>());
        User savedUser = userRepository.save(UserMapper.mapToEntity(user));
        addRoleToUser(savedUser.getUsername(), "ROLE_USER");
        return savedUser;
    }

    @Override
    public Role saveRole(RoleDto role) {
        log.info("Saving role to the database");
        return roleRepository.save(RoleMapper.mapToEntity(role));
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Accessing the");
        User user = userRepository.findByUsername(username).orElse(null);
        Role role = roleRepository.findRoleByName(roleName).orElse(null);
        if (user != null) {
            user.getRoles().add(role);
        }
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
