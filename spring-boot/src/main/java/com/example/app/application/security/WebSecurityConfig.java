package com.example.app.application.security;

import com.example.app.application.session.CustomAccessDeniedHandler;
import com.example.app.application.session.CustomAuthenticationEntryPoint;
import com.example.app.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
                .and()
            .requestMatcher(new BasicRequestMatcher())
            .authorizeRequests()
            .antMatchers("/login/login", "/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            .and()
            .csrf().disable();
    }

    /**
     * これをかますことでBasic認証以外は通せる
     */
    private class BasicRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String basicAuth = request.getHeader("Authorization");
            if (basicAuth != null && basicAuth.startsWith("Basic")) {
                return true;
            }

            String tokenAuth = request.getHeader("X-Auth-Token");
            if (tokenAuth != null) {
                // TODO 条件が足らない
                return true;
            }

            // TODO ソーシャルの場合、ここから独自チェック？

            return true;
        }
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//    }


//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcauthentication()
//                .datasource(datasource)
//                .authoritiesbyusernamequery("select user_id, authority from t01_connect_session where user_id = ?") // fixme
//
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider())
            .jdbcAuthentication().dataSource(dataSource);
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return new AccountService();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver("X-Auth-Token");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
