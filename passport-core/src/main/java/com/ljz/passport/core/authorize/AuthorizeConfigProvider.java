package com.ljz.passport.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义权限控制
 *
 * @author 李建珍
 * @date 2019/4/30
 */
public interface AuthorizeConfigProvider {
    /**
     * @param http
     * @see HttpSecurity#authorizeRequests()
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry http);
}
