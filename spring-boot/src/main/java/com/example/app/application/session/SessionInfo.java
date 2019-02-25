package com.example.app.application.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

/**
 * lombokを使うとシリアライズがうまくいかない
 */
@SessionScope
@Component
public class SessionInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
