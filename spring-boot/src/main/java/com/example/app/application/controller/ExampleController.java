package com.example.app.application.controller;

import com.example.app.domain.object.User;
import com.example.app.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleController {
    @Autowired
    private UserService userService;

    @RequestMapping("/example1")
    public List<User> example1() {
        return userService.selectAll();
    }

    @RequestMapping("/error500")
    public List<User> error500() {
        throw new RuntimeException("500");
    }

    @RequestMapping("/error400")
    public List<User> error400() {
        throw new RuntimeException("400");
    }
}
