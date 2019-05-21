package com.ljz.passport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author 李建珍
 * @date 2019/5/20
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("janle")
                .secret( new BCryptPasswordEncoder().encode("janleSecret"))
                //使用授权码和refresh_token
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .redirectUris("http://www.clouds1000.com:8080/login")
                .authorities("oauth2")
                .autoApprove(true)
                .scopes("all")
                .and()
                .withClient("janle1")
                .secret(new BCryptPasswordEncoder().encode("janleSecret"))
                //使用授权码和refresh_token
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .redirectUris("http://www.clouds1000.com:8081/login")
                .authorities("oauth2")
                .autoApprove(true)
                .scopes("all");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //  Sets the JWT signing key
        jwtAccessTokenConverter.setSigningKey("janle");
        return jwtAccessTokenConverter;
    }

}
