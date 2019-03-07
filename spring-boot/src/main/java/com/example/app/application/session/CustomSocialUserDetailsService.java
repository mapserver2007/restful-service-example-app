package com.example.app.application.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.ArrayList;

public class CustomSocialUserDetailsService implements SocialUserDetailsService {

    private UserDetailsService userDetailsService;

    public CustomSocialUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        // TODO Repositoryからデータ引っ張る
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userId); これは使えない。userテーブルってアホか。

        // このuserIdがt01_connect_sessionのprincipal_nameに入る
        return new SocialUser(userId, "", new ArrayList<>());
    }
}
