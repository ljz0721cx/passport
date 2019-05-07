package com.ljz.passport.core.social;

import com.ljz.passport.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * https://www.petrikainulainen.net/programming/spring-framework/adding-social-sign-in-to-a-spring-mvc-web-application-configuration/
 *
 * @author 李建珍
 * @date 2019/4/2
 */
@Configuration
public abstract class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SocialConnectionSignUp socialConnectionSignUp;


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //Encryptors.noOpText() 不做加解密处理
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //设置数据库的前缀
        jdbcUsersConnectionRepository.setTablePrefix("ljz_");
        //设置第三方登录账户信息保存
        if (null != socialConnectionSignUp) {
            jdbcUsersConnectionRepository.setConnectionSignUp(socialConnectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    /**
     * 创建对应的链接工厂
     *
     * @return
     */
    public abstract ConnectionFactory<?> createConnectionFactory();

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
        //1、认证失败跳转注册页面
        // 跳转到signUp controller，从session中获取用户信息并通过生成的uuid保存到redis里面，然后跳转bind页面
        // 前端绑定后发送用户信息到后台bind controller，1）保存到自己系统用户；2）保存一份userconnection表数据，Spring Social通过这里面表数据进行判断是否绑定
        String filterProcessUrl = securityProperties.getSocial().getFilterProcessesUrl();
        MySpringSocialConfigurer mySpringSocialConfigurer = new MySpringSocialConfigurer(filterProcessUrl);
        //配置自己的注册url
        mySpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpPage());
        return mySpringSocialConfigurer;
    }


    /**
     * 登录处理的工具类,主要是用来注册第三方账户
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }


    /**
     * 查看第三方绑定账户情况
     *
     * @param connectionFactoryLocator
     * @param connectionRepository
     * @return
     */
    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
