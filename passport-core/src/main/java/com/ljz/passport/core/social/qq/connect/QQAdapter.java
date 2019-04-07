package com.ljz.passport.core.social.qq.connect;

import com.ljz.passport.core.social.qq.api.QQ;
import com.ljz.passport.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * qq适配器，用于将不同服务提供商的个性化用户信息映射到
 *
 * @author 李建珍
 * @date 2019/4/2
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试qq服务是否可用
     *
     * @param qq
     * @return ture可用
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * 设置连接参数
     *
     * @param qq
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo userInfo = qq.getQqUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_2());
        //没有主页
        connectionValues.setProfileUrl(null);
        // 服务提供商返回的该user的openid
        // 一般来说这个openid是和你的开发账户也就是appid绑定的
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * qq不需要
     *
     * @param qq
     * @param s
     */
    @Override
    public void updateStatus(QQ qq, String s) {
    }
}
