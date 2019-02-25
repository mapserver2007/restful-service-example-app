package com.example.app.application.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.Collections;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30) // 最終アクセスからXX秒なにもしないとセッションタイムアウトする
public class HttpSessionConfig {
//    @Bean
//    public JedisConnectionFactory connectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName("localhost");
//        redisStandaloneConfiguration.setPort(16379);
//        redisStandaloneConfiguration.setPassword("redis");
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> rt = new RedisTemplate<>();
//        rt.setConnectionFactory(connectionFactory());
//        rt.setKeySerializer(new StringRedisSerializer());
//        rt.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class)); // 効いてない
//
//        return rt;
//    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new JdkSerializationRedisSerializer();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.addMixIn(UsernamePasswordAuthenticationToken.class, UsernamePasswordAuthenticationTokenMixin.class);
//        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * expired切れたと思われるタイミングでアクセスしたら、Redisからごっそり消えた
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieMaxAge(30);
        return serializer;
    }
}
