package com.example.app.application.controller;

import com.example.app.domain.service.UserService;
import com.example.app.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleController {
    @Autowired
    private UserService userService;

    @RequestMapping("/example1")
    public List<UserEntity> example1(Model model) {
        return userService.selectAll();
    }
}
