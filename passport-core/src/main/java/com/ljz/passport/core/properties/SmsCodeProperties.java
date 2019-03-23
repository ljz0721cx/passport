package com.ljz.passport.core.properties;

/**
 * 默认的图形验证码的拦截需要的配置
 *
 * @author 李建珍
 * @date 2019/3/22
 */
public class SmsCodeProperties {
    /**
     * 验证码长度
     */
    private int length = 4;
    /**
     * 过期时间
     */
    private int expireIn = 60;
    /**
     * 某些请求需要验证码,使用“,”隔开
     */
    private String url;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
