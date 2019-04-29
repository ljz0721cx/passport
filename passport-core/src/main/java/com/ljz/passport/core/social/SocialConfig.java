package com.ljz.passport.core.social;

import com.ljz.passport.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //Encryptors.noOpText() 不做加解密处理
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //设置数据库的前缀
        jdbcUsersConnectionRepository.setTablePrefix("ljz_");
        return jdbcUsersConnectionRepository;
    }


    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    /**
     * 默认配置类，进行组件的组装
     * 配置spring social的config
     * 包括了过滤器SocialAuthenticationFilter 添加到security过滤链中
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer socialSecurityConfig() {
        //配置授权地址
        String filterProcessUrl = securityProperties.getSocial().getFilterProcessesUrl();
        MySpringSocialConfigurer mySpringSocialConfigurer = new MySpringSocialConfigurer(filterProcessUrl);
        return mySpringSocialConfigurer;
    }
}
