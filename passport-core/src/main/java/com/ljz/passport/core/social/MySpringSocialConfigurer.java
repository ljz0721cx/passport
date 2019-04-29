package com.ljz.passport.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 覆盖默认的SpringSocialConfigurer，重新实现postProcess
 * @author 李建珍
 * @date 2019/4/23
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {


    private String filterProcessUrl;

    public MySpringSocialConfigurer(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        /**
         *  org.springframework.security.config.annotation.SecurityConfigurerAdapter.postProcess()
         * 在SocialAuthenticationFilter中配置死的过滤器拦截地址
         * 这样的方法可以更改拦截的前缀
         */
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        return (T) filter;
    }
}
