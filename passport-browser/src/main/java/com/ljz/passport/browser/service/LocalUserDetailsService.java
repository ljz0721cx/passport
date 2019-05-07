package com.ljz.passport.browser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * TODO  每个平台需要实现的
 *
 * @author 李建珍
 * @date 2019/3/19
 */
@Component
public class LocalUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 通过用户民查找用户信息
     * FIXME 实现登陆授权校验需要编写的接口
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        logger.info("用户登录名 " + username + "；登录密码为 " + password);
        return buildUser(username);
    }

    /**
     * 通过用户id获得用户信息
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("第三方登录用户ID " + userId);
        //这里的userId就是数据库中我们的唯一的值
    /*    SocialUser socialUser =
                new SocialUser("user", "123456", false,
                        false, false, true,
                        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
*/
        return buildUser(userId);
    }


    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是:" + password);
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
