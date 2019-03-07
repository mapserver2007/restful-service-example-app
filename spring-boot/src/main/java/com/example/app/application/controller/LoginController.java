package com.example.app.application.controller;

import com.example.app.domain.service.SocialLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepositoryException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private SocialLoginService socialLoginService;

    /**
     * あとで消す　
     * @param principal
     * @return
     */
    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> getInfo(Principal principal) {
        Map<String, String> response = new HashMap<>();
        response.put("name", principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Twitter認証用URLを返却
     * @return
     */
    @GetMapping("/auth/twitter")
    public ResponseEntity<Map<String, String>> authTwitter() {
        Map<String, String> response = new HashMap<>();
        response.put("authorize_url", socialLoginService.createAuthorizeUrl());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Twitterログインを実行
     * @param oauthToken
     * @param oauthVerifier
     * @return
     */
    @GetMapping("/connect/twitter")
    public ResponseEntity<Map<String, String>> twitterCallback(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier) {
        HttpStatus status;
        Map<String, String> response = new HashMap<>();

        try {
            socialLoginService.addConnection(oauthToken, oauthVerifier);
            response.put("message", "log in success.");
            status = HttpStatus.OK;
        } catch (ConnectionRepositoryException e) {
            response.put("message", e.getMessage());
            status = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(response, status);
    }
}
