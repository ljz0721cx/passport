package com.ljz.passport.app.config;

import com.ljz.passport.app.enhancer.JwtTokenEnhancer;
import com.ljz.passport.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置TokenStore的配置
 * 将令牌之类的信息存到redis中
 *
 * @author 李建珍
 * @date 2019/5/18
 */
@Configuration
public class TokenStoreConfig {
    @Configuration
    @ConditionalOnProperty(prefix = "passport.security.oauth2", name = "storeType", havingValue = "redis")
    public static class RedisConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }


    /**
     * jwt的token配置
     * 避免TokenStore冲突
     * passport.security.oauth2 表示要检查的属性,
     * havingValue当这个属性的值为"jwt"时这个内部类里面的配置生效,如果在配置文件中没有配这个属性的时候
     * matchIfMissing我认为你是匹配的,如果我在配置文件里没写这个属性项,底下整个都是生效的
     */
    @Configuration
    @ConditionalOnProperty(prefix = "passport.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {
        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            //JwtTokenStore用来读取的,并不管token的生成
            return new JwtTokenStore(jwtAccessTokenConverter());
        }


        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //设置秘钥
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            //访问令牌转换器
            return accessTokenConverter;
        }


        @Bean
        public TokenEnhancer jwtTokenEnhancer() {
            return new JwtTokenEnhancer();
        }

    }


}
