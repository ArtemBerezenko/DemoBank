package com.demo.service;

import com.demo.model.User;

public interface UserService {
    User findUser(String login, String password);
    void saveUser(String login, String password);
    Iterable<User> findAllUsers();
}
