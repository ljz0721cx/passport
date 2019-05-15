package com.ljz.passport.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 李建珍
 * @date 2019/5/9
 */
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 密码模式需要重写配置
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
