package com.example.app.domain.service;

import com.example.app.domain.object.SocialConnectionUser;
import com.example.app.domain.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public SocialConnectionUser connectionUser(String accessToken) {
        return sessionRepository.findByAccessToken(accessToken);
    }
}
