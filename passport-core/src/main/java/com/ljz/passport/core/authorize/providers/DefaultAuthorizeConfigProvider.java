package com.ljz.passport.core.authorize.providers;

import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.authorize.AuthorizeConfigProvider;
import com.ljz.passport.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 自定义权限控制
 *
 * @author 李建珍
 * @date 2019/4/30
 */
@Component
public class DefaultAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param http
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry http) {
        http.antMatchers(
                SecurityConstants.DEFAULT_NEED_AUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                securityProperties.getBrowser().getLoginPage(),
                securityProperties.getBrowser().getLoginPage()
        ).permitAll();
        // 退出成功处理，没有默认值，所以需要判定下
        String signOutUrl = securityProperties.getBrowser().getLoginOut();
        if (signOutUrl != null) {
            http.antMatchers(signOutUrl).permitAll();
        }
    }
}
