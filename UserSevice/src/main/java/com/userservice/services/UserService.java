package com.userservice.services;

import com.userservice.entities.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public List<User> getAllUsers();

    public User getUserById(String userId);

    //delete

    //update
}
