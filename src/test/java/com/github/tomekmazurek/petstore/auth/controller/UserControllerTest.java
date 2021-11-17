package com.github.tomekmazurek.petstore.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.auth.config.SecurityConfig;
import com.github.tomekmazurek.petstore.auth.dto.UserDto;
import com.github.tomekmazurek.petstore.auth.model.User;
import com.github.tomekmazurek.petstore.auth.service.UserServiceImpl;
import com.github.tomekmazurek.petstore.testhelper.UserMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomekmazurek.petstore.testhelper.UserMother.createListOfUsers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldReturnListOfUsersWithStatus200() throws Exception {
        // given
        List<User> mockUsersList = createListOfUsers();
        Mockito.when(userService.getUsers()).thenReturn(mockUsersList);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/auth"))
                .andDo(print())
                .andReturn();
        List<User> resultList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });

        // then
        Assertions.assertThat(resultList).isEqualTo(mockUsersList);
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void shouldSaveUserToDB() throws Exception {
        // given
        User user = UserMother.createListOfUsers().get(0);
        user.setId(null);
        user.setRoles(user.getRoles().stream().map(role -> {
            role.setId(null);
            return role;
        }).collect(Collectors.toList()));
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(user);

        // when
        MvcResult result = mockMvc.perform(
                post("/api/v1/auth")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        // then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains(user.getUsername());
    }
}