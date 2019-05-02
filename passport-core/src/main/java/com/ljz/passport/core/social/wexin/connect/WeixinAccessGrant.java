package com.ljz.passport.core.social.wexin.connect;

import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信的access_token信息与标准的OAuth2协议不同,微信在获取access_token时会同时返回openId,并没有单独的通过accessToken换取openId的服务
 * 对于开发来说少了一步换取的步骤
 * 所以这里继承了标准AccessGrant,添加了openId字段,作为对微信access_token信息的封装
 *
 * @author 李建珍
 * @date 2019/5/1
 */
public class WeixinAccessGrant extends AccessGrant {
    private String openId;

    public WeixinAccessGrant() {
        super("");
    }

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
