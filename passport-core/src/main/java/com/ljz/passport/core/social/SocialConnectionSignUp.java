package com.ljz.passport.core.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author 李建珍
 * @date 2019/5/1
 */
@Component
public class SocialConnectionSignUp implements ConnectionSignUp {
    /**
     * 根据社交用户信息默认创建用户，并返回用户唯一标识
     *
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        return connection.getDisplayName();
    }
}
