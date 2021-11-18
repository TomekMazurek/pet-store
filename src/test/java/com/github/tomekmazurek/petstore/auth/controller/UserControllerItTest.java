package com.github.tomekmazurek.petstore.auth.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomekmazurek.petstore.auth.dto.RoleDto;
import com.github.tomekmazurek.petstore.auth.dto.UserDto;
import com.github.tomekmazurek.petstore.testhelper.UserMother;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerItTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    void shouldSaveUserToDatabase() throws Exception {
        // given
        RequestBuilder request = post("/api/v1/auth")
                .content(mapper.writeValueAsString(UserMother.createSingleUser()))
                .contentType(MediaType.APPLICATION_JSON);

        // when
        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andReturn();
        UserDto userDto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        // then
        assertThat(userDto.getUsername()).isEqualTo(UserMother.createSingleUser().getUsername());
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    @Order(2)
    void shouldReturnListOfUsersAndReturn200() throws Exception {
        // given
        RequestBuilder request = get("/api/v1/auth");

        // when
        MvcResult result = mockMvc.perform(request).andDo(print()).andReturn();
        List<UserDto> userDtoList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserDto>>() {
        });

        // then
        assertThat(userDtoList.size()).isEqualTo(1);
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    @Order(3)
    void shouldAddNewRoleAndReturnStatus200() throws Exception {
        // given
        RequestBuilder request = post("/api/v1/auth/new-role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(RoleDto.builder()
                        .name("ROLE_MANAGER")
                        .build()));

        // when
        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andReturn();
        RoleDto responseRoleDto = mapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        MvcResult resultForCallForListOfRoles = mockMvc.perform(get("/api/v1/auth/roles"))
                .andDo(print())
                .andReturn();
        List<RoleDto> roles = mapper.readValue(resultForCallForListOfRoles.getResponse().getContentAsString(), new TypeReference<List<RoleDto>>() {
        });


        // then
        assertThat(responseRoleDto).isNotNull();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(roles.size()).isEqualTo(3);
        assertThat(roles.stream().filter(roleDto -> roleDto.getName().equals("ROLE_MANAGER"))).isNotNull();

    }
}
