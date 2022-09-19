package com.exam.service.impl;

import com.exam.exception.UserFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    creating user
    public User createUser(User user, Set<UserRole> userRoles) throws UserFoundException {
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local !=null){

            logger.error("User {} already exists.",local.getUsername());

            throw new UserFoundException();
        }
        else{
            //Assigning all role defined for a users to user
            for(UserRole ur: userRoles){
                roleRepository.save(ur.getRole());
            }
            //saving role to user
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
            logger.info("User with {} is registered.",local.getUsername());
        }
        return local;
    }


    //getting user by username
    @Override
    public User getUser(String username) throws UserNotFoundException {

        User user = this.userRepository.findByUsername(username);
        if(user == null) {
            logger.error("User not found");
            throw new UserNotFoundException();
        }
        return user;


    }


    //Update user
    @Override
    public User updateUser(User user)  {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        try {
            this.userRepository.deleteById(userId);
        }catch (Exception e){
            throw new UserNotFoundException();
        }
    }

    //get all users

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }




}
