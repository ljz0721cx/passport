package com.ljz.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author 李建珍
 * @date 2019/5/26
 */
@EnableResourceServer
@Configuration
public class Oauth2ClientConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/auth/token").permitAll()
                .anyRequest().authenticated()
                .and().csrf();
        super.configure(http);
    }
}
