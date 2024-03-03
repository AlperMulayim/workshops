package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//Create REST API testing.

@WebMvcTest(controllers = UsersController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;


    @Test
    void testCreateUserWhenValidUserDetailsProvided() throws Exception {
        //arrange
        UserDetailsRequestModel model = new UserDetailsRequestModel();

        model.setFirstName("alper");
        model.setLastName("mulayim");
        model.setEmail("alp@test.com");
        model.setPassword("abcd1234");
        model.setRepeatPassword("abdc1234");

        UserDto dto = new ModelMapper().map(model,UserDto.class);
        dto.setUserId(UUID.randomUUID().toString());

        //user service create method will return my dto as mock.
        when(usersService.createUser(any(UserDto.class))).thenReturn(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(model));

        //act
        MvcResult resultMVC = mockMvc.perform(requestBuilder).andReturn();
        String  responseBody = resultMVC.getResponse().getContentAsString();

        UserRest result = new ObjectMapper().readValue(responseBody, UserRest.class);

        //assert
        assertEquals(model.getFirstName(),result.getFirstName());
        assertEquals(model.getLastName(),result.getLastName());
        assertEquals(model.getEmail(),result.getEmail());
        assertFalse(result.getUserId().isEmpty());
        assertEquals(HttpStatus.OK.value(), resultMVC.getResponse().getStatus());
    }

    @Test
    void testWhenUserNameCharactersNotMatchActualSizeReturnBadRequest() throws Exception {
        UserDetailsRequestModel model = new UserDetailsRequestModel();

        model.setFirstName("a");
        model.setLastName("mulayim");
        model.setEmail("alp@test.com");
        model.setPassword("abcd1234");
        model.setRepeatPassword("abdc1234");

        UserDto dto = new ModelMapper().map(model,UserDto.class);
        dto.setUserId(UUID.randomUUID().toString());

        when(usersService.createUser(any(UserDto.class))).thenReturn(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(model));


        MvcResult resultMVC = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(),resultMVC.getResponse().getStatus());
    }


}
