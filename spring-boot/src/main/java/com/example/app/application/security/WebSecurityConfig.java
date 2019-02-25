package com.example.app.application.security;

import com.example.app.domain.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .csrf().disable();


        // Basic認証
//        http
//            .httpBasic()
//            .and()
//                .csrf()
//            .and()
//                .cors()
//                .configurationSource(corsConfigurationSource())
//            .and()
//                .authorizeRequests()
//                .antMatchers("/get_auth_token")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(getAuthenticationProvider());
//        auth.inMemoryAuthentication()
//            .withUser("test").password(passwordEncoder().encode("test")).roles("USER");
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
