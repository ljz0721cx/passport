package com.ljz.passport.browser.config;

import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * web应用适配器的配置
 *
 * @author 李建珍
 * @date 2019/3/19
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler selfAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler selfAuthenticationFailureHandler;

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
        ValidateCodeFilter valdateCodeFilter = new ValidateCodeFilter();
        valdateCodeFilter.setAuthenticationFailureHandler(selfAuthenticationFailureHandler);

        //传递进配置信息
        valdateCodeFilter.setSecurityProperties(securityProperties);
        valdateCodeFilter.afterPropertiesSet();

        http
                //在表单验证之前添加验证码过滤器
                .addFilterBefore(valdateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //httpbasic认证方式 用表单认证所有的访问都需要认证
                //http.httpBasic()
                .loginPage("/login")
                .loginPage(securityProperties.getBrowser().getLoginPage())
                //登录提交的url
                .loginProcessingUrl("/login/form")
                //自定义登录成功认证
                .successHandler(selfAuthenticationSuccessHandler)
                //自定义登录失败的处理器
                .failureHandler(selfAuthenticationFailureHandler)
                .and()
                //对请求授权
                .authorizeRequests()
                //授权配置登录返回和登录页面
                .antMatchers("/login", "/validate/code", securityProperties.getBrowser().getLoginPage()).permitAll()
                //任何请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                //crsf跨站请求
                .and().csrf().disable();
    }
}
