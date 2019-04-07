package com.ljz.passport.core.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class QQOAuthConnectionFactory extends OAuth2ConnectionFactory {


    /**
     * @param providerId 服务商id；自定义字符串
     * @param appId 用于执行授权流和获取本机服务API实例的ServiceProvider模型
     * @param appSecret
     */
    public QQOAuthConnectionFactory(String providerId, String appId, String appSecret) {
        /**
         * serviceProvider  用于执行授权流和获取本机服务API实例的ServiceProvider模型
         * QQAdapter适配器，用于将不同服务提供商的个性化用户信息映射到
         */
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
