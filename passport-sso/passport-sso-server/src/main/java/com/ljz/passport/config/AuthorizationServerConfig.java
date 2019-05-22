package com.ljz.passport.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 加载顺序比WebSecurityConfig高
 *
 * @author 李建珍
 * @date 2019/5/20
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //TODO 接入数据库或者客户端
        clients.inMemory()
                .withClient("janle")
                .secret(passwordEncoder.encode("janleSecret"))
                //使用授权码和refresh_token
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .redirectUris("http://www.clouds1000.com:8080/login")
                .authorities("oauth2")
                .autoApprove(true)
                .scopes("all")
                .and()
                .withClient("janle1")
                .secret(passwordEncoder.encode("janleSecret"))
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
        //TODO JwtTokenStore换用别的tokenstore的承载
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //TODO  使用配置的方式 Sets the JWT signing key
        jwtAccessTokenConverter.setSigningKey("janle");
        return jwtAccessTokenConverter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
