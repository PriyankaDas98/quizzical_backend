package com.exam.service;


import com.exam.exception.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsersTest()
    {
        List<User> list = new ArrayList<User>();
        User userOne = new User();
        User userTwo = new User();
        User userThree = new User();

        list.add(userOne);
        list.add(userTwo);
        list.add(userThree);

        when(userRepository.findAll()).thenReturn(list);

        //test
        List<User> userList = userService.getUsers();

        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();
    }
    @SneakyThrows
    @Test
    public void getUserByUsernameTest() {
        // setting up dummy object
        User user = new User();
        user.setUsername("PD");
        user.setFirstname("Priyanka");
        user.setLastname("Das");
        user.setEmail("pd@gmail.com");

        when(userRepository.findByUsername("PD"))
                .thenReturn(user);

        //testing service method
        User userObj = userService.getUser("PD");

        assertEquals("Priyanka", userObj.getFirstname());
        assertEquals("Das", userObj.getLastname());
        assertEquals("pd@gmail.com", userObj.getEmail());
    }
    @Test
    public void getUserExceptionTest() {
        when(userRepository.findByUsername("user")).thenReturn(null);
        assertThatThrownBy(() -> {
            userService.getUser("user");
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with this username not found in database !!");


    }
    @Test
    @DisplayName("Create user")
    public void createUserTest() throws Exception {
        User user = new User();
        user.setFirstname("Priya");
        user.setLastname("Sen");
        user.setUsername("priya101");
        user.setPassword("abc");
        user.setEmail("abc@gmail.com");
        user.setProfile("default.png");
        Role role1 = new Role(45L, "NORMAL");
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole = new UserRole();

        userRole.setRole(role1);

        userRole.setUser(user);

        userRoleSet.add(userRole);

        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.createUser(user, userRoleSet).getUsername())
                .isEqualTo(user.getUsername());


    }
    @Test
    @DisplayName("Update user")
    public void updateUserTest() throws Exception {

        User user = new User();
        user.setFirstname("Ria");
        user.setLastname("Sen");
        user.setUsername("ria101");
        user.setPassword("abc");
        user.setEmail("abc@gmail.com");
        user.setProfile("default.png");
        Role role1 = new Role(45L, "NORMAL");
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole = new UserRole();

        userRole.setRole(role1);

        userRole.setUser(user);

        userRoleSet.add(userRole);
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.createUser(user, userRoleSet).getUsername())
                .isEqualTo(user.getUsername());


    }


}
