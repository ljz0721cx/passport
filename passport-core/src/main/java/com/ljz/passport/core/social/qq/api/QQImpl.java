package com.ljz.passport.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * qq实现的
 *
 * @author 李建珍
 * @date 2019/4/2
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;
    private ObjectMapper objectMapper = new ObjectMapper();


    public QQImpl(String accessToken, String appId) {
        //请求放在页面请求url的地址中
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        //调用返回的结果
        System.out.println(result);
        //通过调用获得openid
        this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
    }


    @Override
    public QQUserInfo getQqUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        try {
            return objectMapper.readValue(result, QQUserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
