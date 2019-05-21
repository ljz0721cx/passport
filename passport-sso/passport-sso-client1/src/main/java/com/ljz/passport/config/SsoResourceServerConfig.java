package com.ljz.passport.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 李建珍
 * @date 2019/5/20
 */
@Configuration
@EnableOAuth2Sso
public class SsoResourceServerConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


}
