package com.ljz.passport.app.config;

import com.ljz.passport.app.auths.SelfAuthenticationFailureHandler;
import com.ljz.passport.app.auths.SelfAuthenticationSuccessHandler;
import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.auth.SmsCodeAuthenticationSecurityConfig;
import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 配置资源服务
 *
 * @author 李建珍
 * @date 2019/5/8
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    protected SelfAuthenticationSuccessHandler selfAuthenticationSuccessHandler;
    @Autowired
    protected SelfAuthenticationFailureHandler selfAuthenticationFailureHandler;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FOORM)
                .successHandler(selfAuthenticationSuccessHandler)
                .failureHandler(selfAuthenticationFailureHandler);
        //和校验码相关的配置
        http.apply(validateCodeSecurityConfig)
                .and()
                //拦截短信登录验证
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //拦截第三方登录
                .apply(springSocialConfigurer);
        //对请求授权
        http.authorizeRequests()
                //授权配置登录返回和登录页面
                .antMatchers(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FOORM,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignUpPage(),
                        securityProperties.getBrowser().getLogout(),
                        "/user/regist")
                .permitAll()
                //任何请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                //crsf跨站请求
                .and().csrf().disable();
    }


    @Configuration
    public static class AppSecurityConfig extends WebSecurityConfigurerAdapter {
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
}

