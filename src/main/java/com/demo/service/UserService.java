package com.demo.service;

import com.demo.model.User;

public interface UserService {
    User findUser(String login);
    void saveUser(String login, String password);
    Iterable<User> findAllUsers();
}
