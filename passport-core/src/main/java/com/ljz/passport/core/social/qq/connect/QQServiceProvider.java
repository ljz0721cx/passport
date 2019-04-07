package com.ljz.passport.core.social.qq.connect;

import com.ljz.passport.core.social.qq.api.QQ;
import com.ljz.passport.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String clientId, String clientSecret) {
        super(new OAuth2Template(clientId, clientSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
