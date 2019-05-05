package com.ljz.passport.core.properties;

import com.ljz.passport.core.auth.SecurityConstants;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;


/**
 * session配置
 *
 * @author 李建珍
 * @date 2019/3/19
 */
public class SessionProperties {
    /**
     * 允许同一个用户名同时在线的session个数
     */
    private int maximumSessions = 4;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     * fasle ： 会走{@link SimpleRedirectSessionInformationExpiredStrategy}
     * true：会阻止登录，这个阻止登录的个性化消息没有设置，看源码的时候好像可以覆盖那个过滤器；设置为true会看到报错信息，然后就可以查看覆盖说明了
     */
    private boolean maxSessionsPreventsLogin;
    /**
     * session失效时跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    /**
     * 设置授权session失败的地址
     */
    private String sessionAuthenticationErrorUrl = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    /**
     * 并发控制到对应的地址,提示已经有登录者
     */
    private String expiredUrl= SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }

    public String getSessionAuthenticationErrorUrl() {
        return sessionAuthenticationErrorUrl;
    }

    public void setSessionAuthenticationErrorUrl(String sessionAuthenticationErrorUrl) {
        this.sessionAuthenticationErrorUrl = sessionAuthenticationErrorUrl;
    }

    public String getExpiredUrl() {
        return expiredUrl;
    }

    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }
}
