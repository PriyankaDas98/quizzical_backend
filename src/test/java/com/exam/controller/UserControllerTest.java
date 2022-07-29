package com.exam.controller;

import com.exam.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test

    public void getUsersTest() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .get("/user/getUsers")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());

    }
    @Test

    public void getUser() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .get("/user/{username}", "priyanka98")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("priyanka98"));
    }
    @Test
    public void createUserTest() throws Exception
    {
        User user = new User();
        user.setId(99L);
        user.setFirstname("Test66");
        user.setLastname("Test66");
        user.setPhone("9099876654");
        user.setUsername("test3");
        user.setEmail("user@gmail.com");
        user.setPassword("password");
        user.setProfile("default.jpg");

        mvc.perform( MockMvcRequestBuilders
                        .post("/user/")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
