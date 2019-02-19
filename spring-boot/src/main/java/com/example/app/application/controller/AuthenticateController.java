package com.example.app.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RestController
public class AuthenticateController {

    @RequestMapping("/auth")
    @GetMapping
    public Map<String, Object> getAuthToken(HttpSession session) {
        return Collections.singletonMap("session", session.getId());
    }
}
