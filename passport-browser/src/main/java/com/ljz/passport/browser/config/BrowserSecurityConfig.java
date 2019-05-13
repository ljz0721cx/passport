package com.ljz.passport.browser.config;

import com.alibaba.fastjson.JSONArray;
import com.ljz.passport.core.auth.AbstractSecurityConfig;
import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.auth.SmsCodeAuthenticationSecurityConfig;
import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.support.SimpleResponse;
import com.ljz.passport.core.validate.ValidateCodeSecurityConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * web应用适配器的配置
 *
 * @author 李建珍
 * @date 2019/3/19
 */
@Configuration
@EnableRedisHttpSession
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
    private SpringSocialConfigurer springSocialConfigurer;

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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*.html");
        super.configure(web);
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
        //和校验码相关的配置
        http.apply(validateCodeSecurityConfig)
                .and()
                //拦截短信登录验证
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //拦截第三方登录
                .apply(springSocialConfigurer)
                .and()
                .rememberMe()
                .tokenRepository(persistenceTokenService())
                .tokenValiditySeconds(securityProperties.getBrowser().getRemeberMeSeconds())
                .userDetailsService(localUserDetailsService);
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
        //.and().csrf().requireCsrfProtectionMatcher(new RequestMatcher()).disable();
        //对session的管理
        http
                .sessionManagement()
                // session-management@invalid-session-url
                .invalidSessionUrl(securityProperties.getBrowser().getSession().getSessionInvalidUrl())
                // session-management@session-authentication-error-url
                .sessionAuthenticationErrorUrl(securityProperties.getBrowser().getSession().getSessionAuthenticationErrorUrl())
                //设置同时登陆的账户数
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                // session-management/concurrency-control@error-if-maximum-exceeded
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                // session-management/concurrency-control@expired-url 并发控制到对应的地址,提示已经有登录者
                .expiredUrl(securityProperties.getBrowser().getSession().getExpiredUrl());


        http.logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    if (StringUtils.endsWith(securityProperties.getBrowser().getLogout(), ".html")) {
                        response.sendRedirect(securityProperties.getBrowser().getLogout());
                    } else {
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(JSONArray.toJSONString(new SimpleResponse("退出成功")));
                        response.flushBuffer();
                    }
                })
                .deleteCookies("JSESSIONID");
    }


}
