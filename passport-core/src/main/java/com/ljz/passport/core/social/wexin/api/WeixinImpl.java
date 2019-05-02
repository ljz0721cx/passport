package com.ljz.passport.core.social.wexin.api;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 微信实现
 *
 * @author 李建珍
 * @date 2019/4/2
 */
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {
    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";

    public WeixinImpl(String accessToken) {
        //请求放在页面请求url的地址中
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }


    /**
     * 调整为微信需要的utf8字符集
     *
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    /**
     * 获取微信用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public WeixinUserInfo getUserInfo(String openId) {
        String url = String.format(URL_GET_USER_INFO, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        if (StringUtils.contains(result, "errcode")) {
            return null;
        }
        WeixinUserInfo weixinUserInfo = null;

        // 感觉这个 objectMapper真不好用啊，返回的json对象里面多了一个 constellation 字段，
        // userInfo对象里面没有这个字段也报错
        weixinUserInfo = JSONArray.parseObject(result, WeixinUserInfo.class);
        //weixinUserInfo.setOpenId(openId);
        return weixinUserInfo;
    }
}
