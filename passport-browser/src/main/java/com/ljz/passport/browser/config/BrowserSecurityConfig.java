package com.ljz.passport.browser.config;

import com.ljz.passport.core.auth.AbstractSecurityConfig;
import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.auth.SmsCodeAuthenticationSecurityConfig;
import com.ljz.passport.core.authorize.AuthozieConfigManager;
import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * web应用适配器的配置
 *
 * @author 李建珍
 * @date 2019/3/19
 */
@Configuration
public class BrowserSecurityConfig extends AbstractSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService localUserDetailsService;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private SpringSocialConfigurer socialSecurityConfig;
    @Autowired
    private AuthozieConfigManager authorizeConfigManager;

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
     * 将rememberme记录到数据库中
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistenceTokenService() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    /**
     * 参数是httpsecurity
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //密码登录认证配置
        applyPasswordAuthenticationConfig(http);
        //和校验码关的配置
        http.apply(validateCodeSecurityConfig)
                .and()
                //拦截短信登录验证
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //拦截第三方登录
                .apply(socialSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistenceTokenService())
                .tokenValiditySeconds(securityProperties.getBrowser().getRemeberMeSeconds())
                .userDetailsService(localUserDetailsService)
                .and()
                //对请求授权
                .authorizeRequests()
                //授权配置登录返回和登录页面
                .antMatchers(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FOORM,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignUpPage(),
                        "/user/regist")
                .permitAll()
                //任何请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                //crsf跨站请求
                .and().csrf().disable();
    }
}
