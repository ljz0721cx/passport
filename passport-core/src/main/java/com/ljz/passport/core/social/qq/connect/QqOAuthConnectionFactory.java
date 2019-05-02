package com.ljz.passport.core.social.qq.connect;

import com.ljz.passport.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class QqOAuthConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    /**
     * @param providerId 服务商id；自定义字符串
     * @param appId      用于执行授权流和获取本机服务API实例的ServiceProvider模型
     * @param appSecret
     */
    public QqOAuthConnectionFactory(String providerId, String appId, String appSecret) {
        /**
         * serviceProvider  用于执行授权流和获取本机服务API实例的ServiceProvider模型
         * QQAdapter适配器，用于将不同服务提供商的个性化用户信息映射到
         */
        super(providerId, new QqServiceProvider(appId, appSecret), new QQAdapter());
    }
}
