package com.example.app.domain.object.authentication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "twitter")
@Data
public class TwitterKey {
    private String consumerKey;
    private String consumerSecret;
    private String callbackUrl;
}
