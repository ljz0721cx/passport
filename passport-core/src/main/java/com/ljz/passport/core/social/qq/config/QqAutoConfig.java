package com.ljz.passport.core.social.qq.config;

import com.ljz.passport.core.properties.QQProperties;
import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.social.SocialConfig;
import com.ljz.passport.core.social.qq.api.QQ;
import com.ljz.passport.core.social.qq.connect.QqOAuthConnectionFactory;
import com.ljz.passport.core.social.views.ConnectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 当配置了appId的时候才启用
 *
 * @author 李建珍
 * @date 2019/4/2
 */
@Configuration
@EnableSocial
@ConditionalOnProperty(prefix = "passport.security.social.qq", name = "appId")
public class QqAutoConfig extends SocialConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ConnectionFactory<QQ> createConnectionFactory() {
        QQProperties qq = securityProperties.getSocial().getQq();
        return new QqOAuthConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }

    /**
     * FIXME
     * 后补：做到处理注册逻辑的时候发现的一个bug：登录完成后，数据库没有数据，但是再次登录却不用注册了
     * 就怀疑是否是在内存中存储了。结果果然发现这里父类的内存ConnectionRepository覆盖了SocialConfig中配置的jdbcConnectionRepository
     * 这里需要返回null，否则会返回内存的 ConnectionRepository
     *
     * @param connectionFactoryLocator
     * @return
     */


    /**
     * qq授权成功后绑定的视图
     * 如果有对应的bean指定，使用当前默认的配置
     *
     * @return
     */
    @Bean(name = {"/connect/qqConnected", "/connect/qqConnect"})
    @ConditionalOnMissingBean(name = "qqConnectiedView")
    public View weixinConnected() {
        return new ConnectionView();
    }
}
