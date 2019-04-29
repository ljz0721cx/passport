package com.ljz.passport.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQImpl(String accessToken, String appId) {
        //请求放在页面请求url的地址中
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        //调用返回的结果
        logger.info(result);
        //通过调用获得openid
        this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
    }


    @Override
    public QQUserInfo getQqUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        QQUserInfo qqUserInfo = null;
        try {
            // 感觉这个 objectMapper真不好用啊，返回的json对象里面多了一个 constellation 字段，
            // userInfo对象里面没有这个字段也报错
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
        } catch (IOException e) {
            throw new RuntimeException("获取QQ用户信息失败", e);
        }
        // 获得code，交换token，然后会调用该方法获取信息
        // 却返回了错误 {"ret":-22,"msg":"openid is invalid"}
        return qqUserInfo;
    }
}
