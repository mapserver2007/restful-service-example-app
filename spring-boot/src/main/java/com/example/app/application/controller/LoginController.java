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

import java.io.IOException;
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

    @GetMapping("/auth_token")
    public ResponseEntity<Map<String, String>> getAuthToken() {
        Map<String, String> response = new HashMap<>();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Twitter認証用URLを返却
     * @return
     */
    @GetMapping("/connect/twitter")
    public ResponseEntity<Map<String, String>> connectTwitter() {
        Map<String, String> response = new HashMap<>();
        response.put("authorize_url", socialLoginService.createAuthorizeUrl());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Twitter認証を実行し、アクセストークンを返却する
     * @param oauthToken
     * @param oauthVerifier
     * @return
     */
    @GetMapping("/auth/twitter")
    public ResponseEntity<Map<String, String>> authTwitter(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier) {
        HttpStatus status;
        Map<String, String> response = new HashMap<>();

        try {
            socialLoginService.addConnection(oauthToken, oauthVerifier);
            response.put("message", "log in success.");
            response.put("access_token", socialLoginService.getSessionId());
            status = HttpStatus.OK;
        } catch (ConnectionRepositoryException | IOException e) {
            response.put("message", e.getMessage());
            response.put("access_token", "");
            status = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(response, status);
    }
}
