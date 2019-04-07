package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class QQProperties extends SocialProperties {
    private String appId;
    private String appSecret;
    /**
     * 服务提供商的标识
     */
    private String providerId = "qq";

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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
