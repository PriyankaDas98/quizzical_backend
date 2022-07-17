package com.exam.service.impl;

import com.exam.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.exam.repo.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	   private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	   
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(username);

        if(user == null){
            logger.error("User not found");
            throw new UsernameNotFoundException("no User Found");
        }
//        logger.info(username +" logged in");
        return user;
    }
}
