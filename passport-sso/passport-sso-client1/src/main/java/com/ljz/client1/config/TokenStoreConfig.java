package com.ljz.client1.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenStoreConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "passport.security.oauth2"
            , name = "tokenStore", havingValue = "jwt", matchIfMissing = true
    )
    public static class JwtTokenConfig {


        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {

            JwtAccessTokenConverter accessTokenConverter
                    = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey("janle");

            return accessTokenConverter;
        }

        /**
         * 配置JWT增强器
         */
        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer() {
            return new CityJwtTokenEnhancer();
        }

    }


}
