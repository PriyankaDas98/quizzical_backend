package com.exam.service.impl;

import com.exam.exception.UserFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.repo.*;
import  com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//to define service class user service
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    
    //creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local !=null){
//            System.out.println("User is already there!!");
            logger.error("User "+local.getUsername()+" already exists.");

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
            logger.info("User with "+local.getUsername() + " is registered.");
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
    	if(userRepository.findById(userId)!=null) {
    		this.userRepository.deleteById(userId);
    		logger.info("User with "+userId+" deleted successfully!");
    	}
    	else {
    		logger.error("User Id "+ userId+" not present in DB");
    		throw new UserNotFoundException();
    		
    	}
    }
    
    //get all users

	@Override
    public Set<User> getUsers() {
        return new HashSet<>(this.userRepository.findAll());
    }




}
