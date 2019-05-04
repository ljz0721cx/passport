package com.ljz.passport.core.social.wexin.config;

import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.properties.WeixinProperties;
import com.ljz.passport.core.social.SocialConfig;
import com.ljz.passport.core.social.views.ConnectionView;
import com.ljz.passport.core.social.wexin.api.Weixin;
import com.ljz.passport.core.social.wexin.connect.WeixinOAuthConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author 李建珍
 * @date 2019/5/1
 */
@Configuration
@EnableSocial
@ConditionalOnProperty(prefix = "passport.security.social.weixin", name = "appId")
public class WeixinAutoConfiguration extends SocialConfig {
    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public ConnectionFactory<Weixin> createConnectionFactory() {
        WeixinProperties weixin = securityProperties.getSocial().getWeixin();
        return new WeixinOAuthConnectionFactory(weixin.getProviderId(), weixin.getAppId(), weixin.getAppSecret());
    }

    /**
     * 微信授权成功后绑定的视图
     * 如果有对应的bean指定，使用当前默认的配置
     *
     * @return
     * @Link com.ljz.passport.core.social.views.ConnectionView
     */
    @Bean(name = {"/connect/weixinConnected", "/connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weixinConnectiedView")
    public View weixinConnected() {
        return new ConnectionView();
    }
}
