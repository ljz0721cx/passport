package com.ljz.passport.browser.web;

import com.ljz.passport.browser.support.SimpleResponse;
import com.ljz.passport.browser.support.SocialUserInfo;
import com.ljz.passport.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 李建珍
 * @date 2019/3/20
 */
@RestController
public class BrowserSecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 需要身份认证时候需要跳转到这里
     * 默认会有一个也
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            String target = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是 " + target);
            if (StringUtils.endsWith(target, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，跳转到登录页面");
    }


    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 获取用户信息数据
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request, HttpServletResponse response) {
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        socialUserInfo.setProviderId(connection.getKey().getProviderId());
        socialUserInfo.setNickname(connection.getDisplayName());
        socialUserInfo.setHeadImage(connection.getImageUrl());
        socialUserInfo.setOpenId(connection.getKey().getProviderUserId());
        return socialUserInfo;
    }


}
