package com.example.app.infrastructure.datasource;

import com.example.app.domain.object.SocialConnectionUser;
import com.example.app.domain.repository.SessionRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SessionRepositoryImpl extends SessionRepository {
    @Override
    SocialConnectionUser findByAccessToken(String accessToken);
}
