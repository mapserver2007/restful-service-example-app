package com.example.app.application.session;

//@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30) // 最終アクセスからXX秒なにもしないとセッションタイムアウトする
public class HttpSessionConfig {

//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
//        return new JdkSerializationRedisSerializer();
//    }
//
//    /**
//     * expired切れたと思われるタイミングでアクセスしたら、Redisからごっそり消えた
//     */
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieMaxAge(30);
//        return serializer;
//    }
}
