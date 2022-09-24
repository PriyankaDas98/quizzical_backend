package com.exam.controller;

import com.exam.exception.UserFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class UserController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws UserFoundException {

        return new ResponseEntity<>(this.userService.createUser(user), HttpStatus.CREATED);

        }
    //getUser
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) throws UserNotFoundException{
        return this.userService.getUser(username);
    }
    
    //get all users
    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers(){

        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    //update user
    @PutMapping("/updateUser")
    public User updateUser(@Valid @RequestBody  User user){

        return this.userService.updateUser(user);
    }
    //delete user
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) throws UserNotFoundException{
        this.userService.deleteUser(userId);
    }

    @ExceptionHandler(UserFoundException.class)
    public String exceptionHandler(UserFoundException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(UserNotFoundException.class)
    public String exceptionHandler(UserNotFoundException ex) {

        return (ex.getMessage());
    }



}
