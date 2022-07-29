package com.exam.repo;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest
@ActiveProfiles("test")

public class UserRepoTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void CreateUserTest(){
        User user = new User();
        user.setUsername("rt99");
        user.setEmail("pd@gmail.com");
        user.setFirstname("Priyanka");
        user.setLastname("Das");
        user.setPassword("password");
        user.setPhone("9099876654");
        user.setProfile("default.png");
        Role role1 = new Role(45L, "NORMAL");
        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setRole(role1);
        userRole.setUser(user);
        userRoleSet.add(userRole);


        User savedInDb = userRepository.save(user);

        Assertions.assertThat(savedInDb.getId()).isNotNull();

    }
    @Test
    public void getUserTest(){
        User user = userRepository.findByUsername("pd101");
        Assertions.assertThat(user.getUsername()).isEqualTo("pd101");

    }
    @Test
    @DisplayName("Should get all the users")
    public void getUsersTest(){
        List<User> userList = userRepository.findAll();
        Assertions.assertThat(userList.size()).isGreaterThan(0);
    }

    @Test
    public void updateUserTest(){
        User user = new User();
        user.setUsername("rt99");
        user.setEmail("pd@gmail.com");
        user.setFirstname("Tisha");
        user.setLastname("Das");
        user.setPassword("password");
        user.setPhone("9099876654");
        user.setProfile("default.png");
        User getFromDb =  userRepository.save(user);
        getFromDb.setUsername("Tisha");
        User updatedUser =  userRepository.save(user);
        Assertions.assertThat(getFromDb.getUsername()).isEqualTo("Tisha");

    }
}
