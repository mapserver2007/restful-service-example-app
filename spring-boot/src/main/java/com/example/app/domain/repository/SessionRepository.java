package com.example.app.domain.repository;

import com.example.app.domain.object.SocialConnectionUser;

public interface SessionRepository {
    SocialConnectionUser findByAccessToken(String accessToken);
}
