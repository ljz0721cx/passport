package com.ljz.passport.core.social.processor;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 用于在不同环境下个性化社交登录的配置
 *
 * @author 李建珍
 * @date 2019/5/8
 */
public interface SocialAuthenticationFilterProcessor {
    /**
     * SocialAuthenticationFilter后处理器,
     *
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
