package com.demo.service;

import com.demo.model.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @SuppressWarnings("unchecked")
    public User findUser(String login) {
        return userRepository.findUsersByLogin(login);
    }

    @Override
    public void saveUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }
}
