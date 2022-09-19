package com.exam.repo;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
 class UserRepoTest {
    @Mock
    private UserRepository userRepository;

    @Test
     void findByUsernameTest(){
        User expected = getUser();
        userRepository.save(expected);

        when(userRepository.findByUsername("Akash")).thenReturn(expected);

        User user = userRepository.findByUsername("Akash");

        assertThat(user).isNotNull();
        assertEquals(user, expected);

    }
    private User getUser(){
        //creating a user object
        User user = new User();
        user.setFirstname("Akash");
        user.setLastname("Sen");
        user.setUsername("akash");
        user.setPassword("abc");
        user.setEmail("abc@gmail.com");
        user.setProfile("default.png");
        Role role1 = new Role(45L, "NORMAL");
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setRole(role1);
        userRole.setUser(user);
        userRoleSet.add(userRole);

        return user;
    }

}
