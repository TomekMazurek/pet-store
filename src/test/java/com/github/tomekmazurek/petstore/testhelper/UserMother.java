package com.github.tomekmazurek.petstore.testhelper;

import com.github.tomekmazurek.petstore.auth.model.Role;
import com.github.tomekmazurek.petstore.auth.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserMother {

    public static List<User> createListOfUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User(
                1L,
                "Mike",
                "Mike123",
                "test123",
                Collections.singletonList(new Role(1L, "ROLE_USER"))));
        list.add(new User(
                2L,
                "Pete",
                "Peterx",
                "test",
                Collections.singletonList(new Role(1L, "ROLE_USER"))));
        return list;
    }
}
