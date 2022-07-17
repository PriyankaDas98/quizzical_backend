package com.exam.service;

import com.exam.exception.UserFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //get User by username
    public User getUser(String username) throws UserNotFoundException;

    //update user by id
    User updateUser(User user);

    //delete user by id
    public void deleteUser(Long userId) throws UserNotFoundException;

	public Object getUsers();
}
