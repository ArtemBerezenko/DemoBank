package com.demo.service;

import com.demo.model.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    Iterable<User> findAllUsers();
}
