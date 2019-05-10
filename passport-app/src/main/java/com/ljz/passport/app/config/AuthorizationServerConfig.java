package com.ljz.passport.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 授权服务
 *
 * @author 李建珍
 * @date 2019/5/8
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 避免出现
     * error="invalid_request", error_description="At least one redirect_uri must be registered with the client."
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //添加客户端信息
        clients.inMemory()                  // 使用in-memory存储客户端信息
                .withClient("janle")
                .secret("{bcrypt}" + new BCryptPasswordEncoder().encode("janleSecret"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .scopes("all")
                .authorities("oauth2")
                .redirectUris("http://www.clouds1000.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer.getDefaultTokenGranters
        // 在WebSecurityConfigurerAdapter的实现类当中，重写，使用密码模式引入，不然不会加载这种模式
        endpoints.authenticationManager(authenticationManager);
        super.configure(endpoints);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }
}
