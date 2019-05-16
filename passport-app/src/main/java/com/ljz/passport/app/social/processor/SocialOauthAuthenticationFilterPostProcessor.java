package com.ljz.passport.app.social.processor;

import com.ljz.passport.core.social.processor.SocialAuthenticationFilterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author 李建珍
 * @date 2019/5/17
 */
@Component
public class SocialOauthAuthenticationFilterPostProcessor implements SocialAuthenticationFilterProcessor {
    @Autowired
    private AuthenticationSuccessHandler selfAuthenticationSuccessHandler;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(selfAuthenticationSuccessHandler);
    }
}
