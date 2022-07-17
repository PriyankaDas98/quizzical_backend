package com.exam.controller;

import com.exam.exception.UserFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    //creating method
    @PostMapping("/")
    public User createUser(@Valid @RequestBody User user) throws Exception {

        user.setProfile("default.png");

        //encoding password(BCryptPassword)
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Set<UserRole> roles = new HashSet<>();
        Role role = new Role();

        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        roles.add(userRole);


        return this.userService.createUser(user,roles);
    }

    //getUser
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) throws UserNotFoundException{
        return this.userService.getUser(username);
    }
    
    //get all users
    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(this.userService.getUsers());
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
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserNotFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }



}
