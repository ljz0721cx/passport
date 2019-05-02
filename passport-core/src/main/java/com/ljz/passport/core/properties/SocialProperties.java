package com.ljz.passport.core.properties;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class SocialProperties {
    /**
     * qq 配置项
     */
    private QQProperties qq = new QQProperties();
    /**
     * 微信的配置项
     */
    private WeixinProperties weixin = new WeixinProperties();
    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }


    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }
}
