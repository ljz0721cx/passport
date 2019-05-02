package com.ljz.passport.core.social.wexin.api;

/**
 * 微信对应的API接口
 *
 * @author 李建珍
 * @date 2019/5/1
 */
public interface Weixin {
    /**
     * 直接拿到用户线信息
     *
     * @param openId
     * @return
     */
    WeixinUserInfo getUserInfo(String openId);
}
