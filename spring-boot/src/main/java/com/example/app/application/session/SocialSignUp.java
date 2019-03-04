package com.example.app.application.session;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class SocialSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        // TODO

        return "test1";
    }
}
