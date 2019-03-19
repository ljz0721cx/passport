package com.ljz.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 李建珍
 * @date 2019/3/19
 */
@Configuration
public class BrowssrSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用表单认证所有的访问都需要认证
        //http.formLogin()
        http.httpBasic()
                .and()
                .authorizeRequests()
                //所有请求求需要认证
                .anyRequest()
                //
                .authenticated();
    }
}
