package com.example.app.domain.service;

import com.example.app.domain.object.authentication.TwitterKey;
import lombok.Getter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

import java.io.IOException;

@Service
public class SocialLoginService {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TwitterKey twitterKey;

    @Getter
    private String sessionId;

    public String createAuthorizeUrl() {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterKey.getConsumerKey(), twitterKey.getConsumerSecret());
        OAuth1Operations authOperation = connectionFactory.getOAuthOperations();
        OAuthToken authToken = authOperation.fetchRequestToken(twitterKey.getCallbackUrl(), null);

        return authOperation.buildAuthorizeUrl(authToken.getValue(), OAuth1Parameters.NONE);
    }

    public void addConnection(String oauthToken, String oauthVerifier) throws ConnectionRepositoryException, IOException {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterKey.getConsumerKey(), twitterKey.getConsumerSecret());
        OAuth1Operations authOperation = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = new OAuthToken(oauthToken, oauthVerifier);
        AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, oauthVerifier);
        OAuthToken oAuthToken = authOperation.exchangeForAccessToken(authorizedRequestToken, null);
        Connection<Twitter> connection = connectionFactory.createConnection(oAuthToken);
        connectionRepository.addConnection(connection);
        createSessionId(connection.createData().getAccessToken());
    }

    private void createSessionId(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/auth_token")
                .addHeader("X-Auth-Social-Token", accessToken)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        sessionId = response.header("X-Auth-Token");
    }


}
