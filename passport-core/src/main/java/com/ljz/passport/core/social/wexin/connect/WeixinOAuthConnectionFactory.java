package com.ljz.passport.core.social.wexin.connect;

import com.ljz.passport.core.social.wexin.api.Weixin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author 李建珍
 * @date 2019/5/2
 */
public class WeixinOAuthConnectionFactory extends OAuth2ConnectionFactory<Weixin> {
    /**
     * @param providerId the provider id e.g. "facebook"
     * @param appId      应用的id
     * @param appSecret  应用的secret
     */
    public WeixinOAuthConnectionFactory(String providerId, String appId, String appSecret) {
        /**
         * serviceProvider  the ServiceProvider model for conducting the authorization flow and obtaining a native service API instance.
         * QQAdapter the ApiAdapter for mapping the provider-specific service API model to the uniform {@link Connection} interface.
         */
        super(providerId, new WeixinServiceProvider(appId, appSecret), new WeixinAdapter());
    }


    /**
     * 抽取openid
     * 微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不像qq通过adapt获取
     *
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeixinAccessGrant) {
            return ((WeixinAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }


    /**
     * 覆盖创建连接的方法 通过访问权限获取微信连接
     *
     * @param accessGrant
     * @return
     */
    @Override
    public Connection<Weixin> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant),
                accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }


    @Override
    public Connection<Weixin> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
        //多实例对象,不同的人openId不一样,每一个openId对应一个对象,qq这里每一次传回去都是同一个对象
        return new WeixinAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
    }

}
