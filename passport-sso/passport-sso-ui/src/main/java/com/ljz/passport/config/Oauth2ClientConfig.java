package com.ljz.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author 李建珍
 * @date 2019/5/26
 */
@EnableResourceServer
@Configuration
public class Oauth2ClientConfig extends AbstractSecurityConfig {
}
