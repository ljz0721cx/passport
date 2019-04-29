package com.ljz.passport.core.social.qq.config;

import com.ljz.passport.core.properties.QQProperties;
import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.social.qq.connect.QqOAuthConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * 当配置了appId的时候才启用
 *
 * @author 李建珍
 * @date 2019/4/2
 */
@Configuration
@ConditionalOnProperty(prefix = "passport.security.social.qq", name = "appId")
public class QqAutoConfig extends SocialConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    public ConnectionFactory<?> createConnectionFactory() {
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
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}
