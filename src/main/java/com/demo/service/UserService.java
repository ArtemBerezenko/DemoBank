package com.demo.service;

import com.demo.model.User;

public interface UserService {
    User findUser(String login);
    void saveUser(User user);
    Iterable<User> findAllUsers();
}
