package com.ljz.passport.browser.support;

/**
 * @author 李建珍
 * @date 2019/5/1
 */
public class SocialUserInfo {
    /**
     * 第三方接入的来源
     */
    private String providerId;
    /**
     * 第三方的openId
     */
    private String openId;
    /**
     * 登录用户信息授权名称
     */
    private String nickname;
    /**
     * 头像图片
     */
    private String headImage;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
