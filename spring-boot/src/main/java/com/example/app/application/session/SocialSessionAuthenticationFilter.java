package com.example.app.application.session;

import com.example.app.domain.object.SocialConnectionUser;
import com.example.app.domain.object.authentication.TwitterKey;
import com.example.app.domain.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialSessionAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private SocialUserDetailsService socialUserDetailsService;

    @Autowired
    private TwitterKey twitterKey;

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String accessToken = request.getHeader("X-Auth-Social-Token");
        SocialConnectionUser user = sessionService.connectionUser(accessToken);

        if (user != null) {
            OAuthToken oAuthToken = new OAuthToken(user.getAccessToken(), user.getSecret());
            TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterKey.getConsumerKey(), twitterKey.getConsumerSecret());
            Connection<Twitter> connection = connectionFactory.createConnection(oAuthToken);
            SocialUserDetails userDetails = socialUserDetailsService.loadUserByUserId(user.getDisplayName());
            SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken(connection, userDetails,null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(socialAuthenticationToken);
        }

        chain.doFilter(request, response);
    }

    /**
     * Filter内でDIを有効にする設定
     */
    @Override
    protected void initFilterBean() {
        if (getFilterConfig() != null) {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    getFilterConfig().getServletContext());
        }
    }
}