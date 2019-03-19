package com.ljz.passport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * web应用适配器的配置
 *
 * @author 李建珍
 * @date 2019/3/19
 */
@Configuration
public class BrowssrSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 选择通用的加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 参数是httpsecurity
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用表单认证所有的访问都需要认证
        http.formLogin()
                //httpbasic认证方式
                //http.httpBasic()
                .and()
                //对请求授权
                .authorizeRequests()
                //任何请求
                .anyRequest()
                //都需要身份认证
                .authenticated();
    }
}
