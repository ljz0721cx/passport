package com.ljz.passport.core.validate;

import com.ljz.passport.core.validate.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author 李建珍
 * @date 2019/3/24
 */
@Component
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    /**
     * @see ValidateCodeFilter  目前融合了短信和图形验证码的验证功能
     */
    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 由源码得知，在最前面的是UsernamePasswordAuthenticationFilter
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
