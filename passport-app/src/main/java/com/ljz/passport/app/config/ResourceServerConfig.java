package com.ljz.passport.app.config;

import com.ljz.passport.app.auths.SelfAuthenticationFailureHandler;
import com.ljz.passport.app.auths.SelfAuthenticationSuccessHandler;
import com.ljz.passport.core.auth.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 配置资源服务
 *
 * @author 李建珍
 * @date 2019/5/8
 */
@Configuration
/*@EnableResourceServer*/
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    protected SelfAuthenticationSuccessHandler selfAuthenticationSuccessHandler;

    @Autowired
    protected SelfAuthenticationFailureHandler selfAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

    }
}

