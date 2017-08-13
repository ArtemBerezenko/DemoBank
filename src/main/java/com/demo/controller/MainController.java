package com.demo.controller;

import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.model.User;
import com.demo.repository.UserRepository;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String login,
                                            @RequestParam String password) {
        userService.saveUser(login, password);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }
}
