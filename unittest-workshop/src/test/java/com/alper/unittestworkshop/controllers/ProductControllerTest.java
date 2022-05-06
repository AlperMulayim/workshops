package com.alper.unittestworkshop.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//INTEGRATION TEST

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductTest() throws  Exception{
        mockMvc.perform(
                get("/product/{name}","Biscuit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Biscuit"))
                .andExpect(jsonPath("$.price").value(10));

    }

    @Test
    public void getAllProducts() throws  Exception{
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }
}