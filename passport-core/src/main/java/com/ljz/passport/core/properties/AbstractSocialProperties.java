package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class AbstractSocialProperties {
    /**
     * 授权的appId
     */
    protected String appId;
    /**
     * 授权的appSecurity
     */
    protected String appSecret;
    /**
     * 社交登录功能拦截的url
     */
    protected String filterProcessesUrl = "/auth";


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
