package com.example.app.domain.service;

import com.example.app.domain.object.SocialConnectionUser;
import com.example.app.domain.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class SessionService {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private SessionRepository sessionRepository;

    public SocialConnectionUser connectionUser(String accessToken) {
        return sessionRepository.findByAccessToken(accessToken);
    }


//    public void connectionUser() {
//        UsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        Object ttt = usersConnectionRepository;
//    }
}
