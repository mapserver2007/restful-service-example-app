package com.example.app.domain.service;

import com.example.app.domain.object.authentication.TwitterKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionRepositoryException;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class SocialLoginService {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TwitterKey twitterKey;

    public String createAuthorizeUrl() {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterKey.getConsumerKey(), twitterKey.getConsumerSecret());
        OAuth1Operations authOperation = connectionFactory.getOAuthOperations();
        OAuthToken authToken = authOperation.fetchRequestToken(twitterKey.getCallbackUrl(), null);

        return authOperation.buildAuthorizeUrl(authToken.getValue(), OAuth1Parameters.NONE);
    }

    public void addConnection(String oauthToken, String oauthVerifier) throws ConnectionRepositoryException {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterKey.getConsumerKey(), twitterKey.getConsumerSecret());
        OAuth1Operations authOperation = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = new OAuthToken(oauthToken, oauthVerifier);
        AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, oauthVerifier);
        OAuthToken accessToken = authOperation.exchangeForAccessToken(authorizedRequestToken, null);
        Connection<Twitter> connection = connectionFactory.createConnection(accessToken);

        connectionRepository.addConnection(connection);
    }
}
