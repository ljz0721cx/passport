package com.ljz.passport.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private TokenStore tokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private TokenEnhancer jwtTokenEnhancer;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 避免出现
     * error="invalid_request", error_description="At least one redirect_uri must be registered with the client."
     * 覆盖这个方法后,在application.properties中的配置都是无效的
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
                //出去的令牌的有效时间
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(2678400)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all")
                .authorities("oauth2")
                .redirectUris("http://www.clouds1000.com");
    }

    /**
     * 配置认证
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * @Link org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer.getDefaultTokenGranters
         * 在WebSecurityConfigurerAdapter的实现类当中，重写，使用密码模式引入，不然不会加载这种模式
         */
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        if (null != jwtAccessTokenConverter && null != jwtTokenEnhancer) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            //通过TokenEnhancerChain增强器链将jwtAccessTokenConverter(转换成jwt)和jwtTokenEnhancer(往里面加内容加信息)连起来
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);

            endpoints
                    //用enhancerChain来配置endpoints这个端点
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
        super.configure(endpoints);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //isAuthenticated():排除anonymous   isFullyAuthenticated():排除anonymous以及remember-me
        //security.tokenKeyAccess("isAuthenticated()");
        //允许表单认证  这段代码在授权码模式下会导致无法根据code　获取token　
        security.allowFormAuthenticationForClients();
    }


}
