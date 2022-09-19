package com.exam.controller;

import com.exam.config.JwtUtils;
import com.exam.exception.UserNotFoundException;
import com.exam.model.JwtRequest;
import com.exam.model.JwtResponse;
import com.exam.model.User;
import com.exam.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@CrossOrigin(origins= "*")
@CrossOrigin("http://localhost:4200")
public class AuthenticateController {
   Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    //generate token
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse>generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
            try{

                authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
            }
            catch(UsernameNotFoundException e){
                throw new UserNotFoundException();
        }
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            String token = this.jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            logger.info("{} logged in.", username);
        }catch (DisabledException e){
            throw  new Exception("User Disabled"+e.getMessage());
        }
        catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials"+e.getMessage());
        }
    }

    //return details of current user
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return (User)this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
