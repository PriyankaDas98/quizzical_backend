package com.exam.service;

import com.exam.exception.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.UserRepository;
import com.exam.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Get all the users")
    public void getUsersTest(){

        when(userRepository.findAll()).thenReturn(Stream
                .of(new User(), new User())
                .collect(Collectors.toList()));

        assertThat(userServiceImpl.getUsers().size()).isEqualTo(2);



    }

    @Test
    @DisplayName("Create user")
    public void createUserTest() throws Exception {

        User user = new User();
        user.setFirstname("Ria");
        user.setLastname("Roy");
        user.setUsername("ria101");
        user.setPassword(this.bCryptPasswordEncoder.encode("abc"));
        user.setEmail("abc@gmail.com");
        user.setProfile("default.png");
        Role role1 = new Role(45L, "NORMAL");
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole = new UserRole();

        userRole.setRole(role1);

        userRole.setUser(user);

        userRoleSet.add(userRole);


        when(userRepository.save(user)).thenReturn(user);
        assertThat(userServiceImpl.createUser(user, userRoleSet).getUsername())
                .isEqualTo(user.getUsername());


    }

    @SneakyThrows
    @Test
    @DisplayName("Should find user by username")
    public void getUserTest() {
        User user = new User();
        user.setUsername("PD101");
        when(userRepository.findByUsername("PD101")).thenReturn(user);
        assertThat(userServiceImpl.getUser("PD101").getUsername())
                .isEqualTo("PD101");


    }
    @Test
    @DisplayName("Should throw UserNotFound exception")
    public void getUserExceptionTest() {
        when(userRepository.findByUsername("PD4545")).thenReturn(null);
        assertThatThrownBy(() -> {
           userServiceImpl.getUser("PD4545");
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with this username not found in database !!");


    }
    @Test
    @DisplayName("Delete user by id")
    public void deleteUserTest() throws UserNotFoundException {
        User user = new User();
        user.setId(22L);
        userServiceImpl.deleteUser(22L);
        verify(userRepository, times(1)).deleteById(22L);

    }


}
