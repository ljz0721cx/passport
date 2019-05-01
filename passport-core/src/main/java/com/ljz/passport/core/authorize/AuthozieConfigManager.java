package com.ljz.passport.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author 李建珍
 * @date 2019/4/30
 */
public interface AuthozieConfigManager {
    /**
     * @param http
     * @throws Exception
     */
    void matchers(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry http) throws Exception;
}
