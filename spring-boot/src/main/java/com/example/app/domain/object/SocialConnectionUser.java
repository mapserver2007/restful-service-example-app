package com.example.app.domain.object;

import lombok.Data;

@Data
public class SocialConnectionUser {
    private String displayName;
    private String accessToken;
    private String secret;
}
