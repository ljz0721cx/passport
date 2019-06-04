package com.ljz.passport.mappers;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.ljz.passport.config.OauthSecurityConfig;
import com.ljz.passport.config.OauthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @author 李建珍
 * @date 2019/5/27
 */
@RestController
public class TokenMapper {

    @Autowired
    private OauthSecurityConfig oauthSecurityConfig;

    /**
     * 前后端分离后获取token信息
     *
     * @return
     */
    @GetMapping("/auth/token")
    public OauthToken getAccessToken(String code) {
        //从单点登录返回之后的状态，本系统还不处于登录状态
        //根据code值去获取access_token，然后再根据access_token去获取用户信息   ，并将用户信息存到session中
        StringBuffer sb = new StringBuffer();
        sb.append("code=").append(code)
                .append("&grant_type=").append("authorization_code")
                .append("&client_id=").append(oauthSecurityConfig.getClientId())
                .append("&client_secret=").append(oauthSecurityConfig.getClientSecret())
                .append("&redirect_uri=").append(URLEncoder.encode(oauthSecurityConfig.getRegistRedirectUri()));
        String resp = HttpUtil.post("http://www.clouds1000.com/oauth/token?", sb.toString());
        //通过Code码获取信息
        OauthToken oauthToken = JSON.parseObject(resp, OauthToken.class);
        return oauthToken;
    }


}
