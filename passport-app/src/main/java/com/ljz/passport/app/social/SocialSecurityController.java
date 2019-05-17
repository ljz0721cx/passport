package com.ljz.passport.app.social;

import com.ljz.passport.core.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李建珍
 * @date 2019/5/17
 */
@RestController
public class SocialSecurityController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private OauthSignUpUtils appSignUpUtils;


    /**
     * 要注册时跳到这里，返回401和用户信息给前端
     *
     * @param request
     * @return
     */
    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setOpenId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadImage(connection.getImageUrl());
        //做转存从connection中拿出数据到redis中,
        appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
        return userInfo;
    }
}
