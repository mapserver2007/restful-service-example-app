package com.example.app.application.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;

import javax.sql.DataSource;

@Configuration
@EnableSocial
@EnableJdbcHttpSession(tableName = "t01_connect_session", maxInactiveIntervalInSeconds = Integer.MAX_VALUE) // about 68years
public class SocialSessionConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserIdSource userIdSource() {
        return new AuthenticationUserIdExtractor();
    }

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        return new SocialAuthenticationServiceRegistry();
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
        repository.setTablePrefix("t01_");
        return repository;
    }
}
