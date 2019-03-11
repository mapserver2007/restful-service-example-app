package com.example.app.application.security;

import com.example.app.application.session.CustomSocialUserDetailsService;
import com.example.app.application.session.SocialSessionAuthenticationFilter;
import com.example.app.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

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
            .authorizeRequests()
            .antMatchers("/login", "/connect/**", "/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterAfter(socialSessionAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .apply(new SpringSocialConfigurer())
            .and()
            .csrf().disable();
    }


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

    @Bean
    public SocialSessionAuthenticationFilter socialSessionAuthenticationFilter() {
        return new SocialSessionAuthenticationFilter();
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new CustomSocialUserDetailsService();
    }
}
