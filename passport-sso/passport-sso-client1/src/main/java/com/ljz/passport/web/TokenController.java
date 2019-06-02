package com.ljz.passport.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李建珍
 * @date 2019/5/27
 */
@RestController
public class TokenController {

    /**
     * 前后端分离后获取token信息
     *
     * @return
     */
    @GetMapping("/auth/token")
    public Authentication getAccessToken() {
        //从单点登录返回之后的状态，本系统还不处于登录状态
        //根据code值去获取access_token，然后再根据access_token去获取用户信息，并将用户信息存到session中
    /*    StringBuffer sb = new StringBuffer();
        sb.append("code=").append(code)
                .append("&grant_type=").append("authorization_code")
                .append("&client_id=").append("janle")
                .append("&client_secret=").append("janleSecret")
                .append("&redirect_uri=").append(URLEncoder.encode((String) "http://client.clouds1000.com:8080/code"));
        String resp = HttpUtil.post("http://www.clouds1000.com/oauth/token?", sb.toString());
        //通过Code码获取信息
        OauthToken oauthToken = JSON.parseObject(resp, OauthToken.class);*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            return authentication;
        }
        return null;
    }


}
