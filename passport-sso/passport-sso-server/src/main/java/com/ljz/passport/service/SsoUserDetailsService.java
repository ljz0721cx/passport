package com.ljz.passport.service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author 李建珍
 * @date 2019/3/19
 */
@Service
public class SsoUserDetailsService implements UserDetailsService, Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("1");
        logger.info("用户登录名 " + username + "；登录密码为 " + password);
        User user = new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        logger.info("登录成功！用户: {}", JSON.toJSONString(user));
        return user;
    }


}
