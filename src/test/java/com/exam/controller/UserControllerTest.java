package com.exam.controller;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private UserServiceImpl userService;


    @BeforeEach
    public void setUp(){
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();

    }
//    @Test
//    @DisplayName("create new user")
//    void createUserTest() throws Exception {
//        User user = new User();
//        user.setId(99L);
//        user.setFirstname("Taniya");
//        user.setLastname("Das");
//        user.setPhone("9099876654");
//        user.setUsername("taniya08");
//        user.setEmail("taniya@gmail.com");
//        user.setPassword("password");
//        user.setProfile("default.jpg");
//        Role role1 = new Role(45L, "NORMAL");
//        Set<UserRole> userRoleSet = new HashSet<>();
//        UserRole userRole = new UserRole();
//
//        userRole.setRole(role1);
//
//        userRole.setUser(user);
//
//        userRoleSet.add(userRole);
//        when(userService.createUser(user, userRoleSet)).thenReturn(user);
//        mvc.perform( MockMvcRequestBuilders
//                        .post("/user/")
//                        .content(asJsonString(user))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//
//    }
    @Test
    @DisplayName("get all users")
    void getUsersTest() throws Exception
    {
        List<User> list = new ArrayList<>();
        User userOne = new User();
        User userTwo = new User();
        User userThree = new User();

        list.add(userOne);
        list.add(userTwo);
        list.add(userThree);

        when(userService.getUsers()).thenReturn(list);

        mvc.perform( MockMvcRequestBuilders
                        .get("/user/getUsers")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());

    }
    @Test
    @DisplayName("get user by username")
    void getUser() throws Exception
    {
        User user = new User();
        user.setUsername("PD");
        user.setFirstname("Priyanka");
        user.setLastname("Das");
        user.setEmail("pd@gmail.com");

        when(userService.getUser("PD"))
                .thenReturn(user);
        mvc.perform( MockMvcRequestBuilders
                        .get("/user/{username}", "username")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("update user")
    void updateUserTest() throws Exception {
        User user = new User();
        user.setId(99L);
        user.setFirstname("Priyanka");
        user.setLastname("Das");
        user.setUsername("priya08");
        user.setEmail("priya@gmail.com");
        user.setPassword("password");
        user.setPhone("9099876654");
        when(userService.updateUser(user)).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders
                        .put("/user/updateUser")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("delete user by Id")
    void deleteUserTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/user/{userId}",999L))
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
