package com.ljz.passport.core.properties;

/**
 * 微信的配置信息
 *
 * @author 李建珍
 * @date 2019/5/1
 */
public class WeixinProperties extends AbstractSocialProperties {
    /**
     * 服务提供商的标识,默认是weixin
     */
    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
