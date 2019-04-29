package com.ljz.passport.core.social.qq.connect;

import com.ljz.passport.core.social.qq.api.QQ;
import com.ljz.passport.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author 李建珍
 * @date 2019/4/2
 */
public class QqServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;
    /**
     * 在http://wiki.connect.qq.com/，QQ开放平台中点击API文档的"网站应用"，"网站开发流程"，进入"获取Access_Token",
     * 找到"过程详解"中的"请求地址"https://graph.qq.com/oauth2.0/authorize"，
     * 该地址就是authorizeUrl第1步"将用户导向认证服务器"的请求路径
     */
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 找到"通过Authorization Code获取Access Token"中的"请求地址"https://graph.qq.com/oauth2.0/token，
     * 该地址就是accessTokenUrl第4步"申请令牌"的请求路径
     */
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    public QqServiceProvider(String appId, String appSecret) {
        /**
         * 创建OAuth2Operations接口的实现类方法的子类QQOAuth2Template，初始化父类OAuth2Operations
         * 我们在构造方法中传入appId,appSecret是为了扩展更多的不同的应用服务请求QQ服务提供商
         */
        super(new QqOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
        this.appId = appId;
    }

    /**
     * accessToken属性用来保存第5步发放的令牌，资源所有者（用户）走完前5步流程后获得的令牌是不同的，
     * 因为Spring容器中注入的实体默认是单例的，
     * 所以不能在继承AbstractOAuth2ApiBinding抽象类的Api接口的实现类中使用@Compoment注解将该类注入到Spring容器中，
     * 这样该类中accessaToken属性保存的令牌都是相同的了，
     * 而是要在实现ServiceProvider接口的实现类中，创建继承AbstractOAuth2ApiBinding抽象类的Api接口的实现类，
     * 这样保证资源持有者访问第三方应用Client时向服务提供商发送获得的用户信息求情时，
     * 发送的accessaToken属性保存的令牌是不同的
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
