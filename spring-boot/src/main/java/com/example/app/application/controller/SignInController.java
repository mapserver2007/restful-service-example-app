package com.example.app.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class SignInController {
    @Autowired
    private DataSource dataSource;

    private final String CONSUMER_KEY = "dummy";
    private final String CONSUMER_SECRET = "dunmy";
    private final String CALLBACK_URL = "http://localhost:8080/auth/twitter/callback";

    @GetMapping("basic")
    public String authBasic() {
        return "ok";
    }


    @GetMapping("twitter")
    public ResponseEntity<Map<String, String>> authTwitter() {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(CONSUMER_KEY, CONSUMER_SECRET);
        OAuth1Operations authOperation = connectionFactory.getOAuthOperations();
        OAuthToken authToken = authOperation.fetchRequestToken(CALLBACK_URL, null);
        String authorizeUrl = authOperation.buildAuthorizeUrl(authToken.getValue(), OAuth1Parameters.NONE);

        Map<String, String> response = new HashMap<>();
        response.put("authorize_url", authorizeUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * TwitterからのコールバックはRESTではなく通常のWebで受け、そのactionでtoken,verifier付きのGETリクエストをRESTAPIに投げる。
     * RESTAPIはリクエストから認証、セッション情報格納をして、OKなら204?でX-AuthTokenを返す。以降はパスワード認証と同じフロー。でOKなはず。
     */
    @GetMapping("twitter/token")
    public ResponseEntity<Map<String, String>> authTwitterCallback(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier) {
        Map<String, String> response = new HashMap<>();

        if (oauthToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(CONSUMER_KEY, CONSUMER_SECRET);
        OAuth1Operations authOperation = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = new OAuthToken(oauthToken, oauthVerifier);
        AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, oauthVerifier);
        OAuthToken accessToken = authOperation.exchangeForAccessToken(authorizedRequestToken, null);

        // このコネクションをDB保存。以降使い回す
        Connection<Twitter> connection = connectionFactory.createConnection(accessToken);
        connection.createData();


//        connection.getApi();

        UserProfile profile = connection.fetchUserProfile();
        Object ttt = connection.hasExpired();




        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
