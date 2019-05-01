package com.ljz.passport.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.SocialException;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 根据"认证第三方登录使用授权码模式流程图"中的流程执行
 * 第一步："client"执行"1.将用户导向认证服务器"，
 * 是我们在页面通过点击a标签的发送 www.clouds1000.com/auth/qq请求后，
 * 因为我们在SocialAuthenticationFilter过滤器修改了过滤请求路径为"/auth"，
 * 所以所有请求路径是"/auth"开头的请求都会被该过滤器过滤，执行该过滤器的attemptAuthentication方法，
 * attemptAuthentication方法调用attemptAuthService方法，
 * attemptAuthService方法调用SocialAuthenticationService接口的实现类OAuth2AuthenticationService的getAuthToken方法，
 * getAuthToken方法会根据从请求中获得请求参数"code"的值进行判断，
 * 如果没有"code"授权码（Authorization Code）则执行：
 * throw new SocialAuthenticationRedirectException(getConnectionFactory().getOAuthOperations().buildAuthenticateUrl(params))，
 * <p>
 * 1 SocialAuthenticationRedirectException异常类构造方法调用getConnectionFactory方法获得ConnectionFactory抽象类连接工厂的子类OAuth2ConnectionFactory的实例，
 * OAuth2ConnectionFactory的实例的创建和初始化需要调用OAuth2ConnectionFactory类的子类QQConnectionFactory的构造方法，
 * 在QQConnectionFactory的构造方法中传入providerId、appId、appSecret这三个参数，
 * QQConnectionFactory的构造方法调用父类OAuth2ConnectionFactory的构造方法将，
 * 1.1 providerId、
 * 1.2 [1]OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider实例、
 * 1.3 ApiAdapter接口的实现类QQAdapter实例这三个参数传入，
 * 创建ConnectionFactory抽象类连接工厂的子类OAuth2ConnectionFactory的实例
 * 【1解:】其中创建OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider实例，
 * 是将QQConnectionFactory的构造方法中传入的，appId、appSecret、appSecret三个参数中的appId、appSecret这两个参数，
 * 传入OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider的子类QQServiceProvider的构造方法中，
 * QQServiceProvider的构造方法调用AbstractOAuth2ServiceProvider抽象父类的构造方法，
 * AbstractOAuth2ServiceProvider抽象父类的构造方法将
 * 【2】OAuth2Operations接口的实现类OAuth2Template的实例作为参数传入，
 * 初始化AbstractOAuth2ServiceProvider抽象父类的OAuth2Operations oauth2Operations属性，
 * 并创建OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider实例
 * 【2解:】其中创建OAuth2Operations接口的实现类OAuth2Template的实例,
 * 是将QQConnectionFactory的构造方法中传入的appId,appSecret这两个参数，
 * 以及QQServiceProvider类中的
 * 【3】URL_AUTHORIZE,
 * 【4】URL_ACCESS_TOKEN两个属性一共四个参数，
 * 【3解:】其中URL_AUTHORIZE保存的路径"https://graph.qq.com/oauth2.0/authorize"是第1步"将用户导向认证服务器"的请求路径
 * 【4解:】URL_ACCESS_TOKEN保存的路径"https://graph.qq.com/oauth2.0/token"是第4步"申请令牌"的请求路径
 * 传入OAuth2Operations接口的实现类OAuth2Template的子类的QQOAuth2Template的构造方法中，
 * QQOAuth2Template的构造方法调用父类OAuth2Template的四个参数的构造方法，
 * OAuth2Template父类的构造方法将：
 * appId的值作为参数clientId的值,
 * appSecret的值作为参数clientSecret的值，
 * URL_AUTHORIZE的值作为参数authorizeUrl的值,
 * URL_ACCESS_TOKEN的值作为参数accessTokenUrl的值传入，
 * OAuth2Template父类的构造方法又调用五个参数的构造方法，将这四个参数传入，第五个参数authenticateUrl传null，
 * OAuth2Template父类五个参数的构造方法初始化OAuth2Template父类的五个属性值，
 * clientId属性初始化值为appId，
 * clientSecret属性初始化值为：clientSecret，
 * authorizeUrl属性值初始化为：authenticateUrl（URL_AUTHORIZE）+"?client_id=" + formEncode(clientId（appId）)第1步"将用户导向认证服务器"的请求路径，
 * 如果authenticateUrl属性值不为null，则authenticateUrl属性值初始化值为：authenticateUrl+"?client_id=" + formEncode(clientId)，
 * 如果authenticateUrl属性值为null，在下一步"OAuth2Template的实例执行buildAuthenticateUrl方法"中为其赋值，
 * accessTokenUrl属性初始化值为：accessTokenUrl，
 * 并创建OAuth2Operations接口的实现类OAuth2Template的实例
 * 2 ConnectionFactory抽象类连接工厂的子类OAuth2ConnectionFactory的实例执行getOAuthOperations方法，
 * 获得OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider实例，
 * OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider实例执行OAuth2ServiceProvider方法，
 * 获得OAuth2Operations接口的实现类OAuth2Template的实例，
 * 3 OAuth2Operations接口的实现类OAuth2Template的实例执行buildAuthenticateUrl方法并传入MultiValueMap接口的实现类ParameterMap的子类OAuth2Parameters参数，
 * OAuth2Parameters参数中有两个键值对，
 * 第一个key="redirect_uri"、value="http:// www.clouds1000.com/auth/qq",
 * 第二个kye="state"、value=生成的UUID
 * buildAuthenticateUrl方法中判断如果authenticateUrl属性值为空，则调用buildAuthorizeUrl方法将GrantType.AUTHORIZATION_CODE, parameters作为参数传入，
 * buildAuthorizeUrl方法调用buildAuthUrl方法，将GrantType.AUTHORIZATION_CODE, parameters作为参数传入，
 * 同时buildAuthUrl方法还会获取OAuth2Operations接口的实现类OAuth2Template的authorizeUrl属性的值（authenticateUrl（URL_AUTHORIZE）+"?client_id=" + formEncode(clientId（appId）)第1步"将用户导向认证服务器"的请求路径）
 * buildAuthUrl方法根据GrantType枚举的值作为判断给OAuth2Operations接口的实现类OAuth2Template的属性authenticateUrl赋值的请求路径中增加"response_type=code"请求参数，
 * 还是给OAuth2Operations接口的实现类OAuth2Template的属性authenticateUrl赋值的请求路径中增加"response_type=token"请求参数，
 * 最终buildAuthUrl方法返回第1步"将用户导向认证服务器"的完整请求路径：
 * "https://graph.qq.com/oauth2.0/authorize?client_id=100550231&response_type=code&redirect_uri=http%3A%2F%2Fwww.clouds1000.com%2FqqLogin%2Fqq&state=efe1500d-6932-45c8-890f-9ff9ef8f4eb5"
 * buildAuthUrl方法将完整请求路径返回给buildAuthorizeUrl方法，buildAuthorizeUrl方法返回给buildAuthenticateUrl方法，
 * 4 最终作为SocialAuthenticationRedirectException异常类构造方法的参数，
 * 将该类的redirectUrl属性初始化，并抛出SocialAuthenticationRedirectException异常类
 * 实现AuthenticationException接口的SocialAuthenticationRedirectException异常类，
 * 最终会被SocialAuthenticationFilter过滤器类的父类AbstractAuthenticationProcessingFilter捕获AuthenticationException接口的catch方法捕获，
 * 执行该类的unsuccessfulAuthentication方法，
 * 在该方法中执行AuthenticationFailureHandler认证失败处理类接口的实现类SocialAuthenticationFailureHandler社交认证失败处理类的onAuthenticationFailure方法，
 * 在该方法中HttpServletResponse类的sendRedirect方法参数为SocialAuthenticationRedirectException异常类的redirectUrl属性值
 * 重定向到获取授权码（Authorization Code）的请求
 * <p>
 * 第二步:"2.用户同意授权"
 * 然后"资源拥有者"被重定向到QQ认证登录页面中进行"服务提供商"的认证登录，
 * <p>
 * 第三步:"3.返回Client并携带授权码"步骤会回调"Client"，
 * "资源拥有者"经过"服务提供商"认证通过后，"服务提供商"会回调我们的"应用服务"，
 * 因为回调请求路径就是"1.将用户导向认证服务器"步骤中，
 * 向服务供应商发送请求的地址 www.clouds1000.com/auth/qq
 * 所以回调请求路径又被请求地址还会被SocialAuthenticationFilter过滤器过滤，
 * 再次执行该过滤器的attemptAuthentication方法，
 * 在该方法会调用attemptAuthService方法，
 * 该方法会调用SocialAuthenticationService接口的实现类OAuth2AuthenticationService的getAuthToken方法，
 * 该方法根据从请求中获得请求参数"code"的值进行判断，
 * 因为执行"3.返回Client并携带授权码"步骤，
 * 会回调"Client"的请求，该回调请求携带"code"授权码（Authorization Code）,
 * 即该回到请求中具有请求参数"code"的值，则执行:
 * AccessGrant accessGrant = getConnectionFactory().getOAuthOperations().exchangeForAccess(code, returnToUrl, null);
 * <p>
 * 1 执行getConnectionFactory获得ConnectionFactory连接工厂抽象类的子类OAuth2ConnectionFactory，
 * 在我们的应用服务中使用我们定义的，OAuth2ConnectionFactory类的子类QQConnectionFactory项目启动时执行构造方法并调用父类OAuth2ConnectionFactory的构造方法创建实例并初始化
 * <p>
 * 2 OAuth2ConnectionFactory类执行getOAuthOperations方法，在该方法中调用getOAuth2ServiceProvider方法，
 * 该方法获得OAuth2ServiceProvider接口的抽象实现类AbstractOAuth2ServiceProvider，
 * 在我们的应用服务中使用我们定义的，AbstractOAuth2ServiceProvider抽象类的子类QQServiceProvider项目启动时执行构造方法并调用父抽象类AbstractOAuth2ServiceProvider的构造方法创建实例并初始化
 * <p>
 * 3 AbstractOAuth2ServiceProvider执行getOAuthOperations方法，获得OAuth2Operations接口的实现类OAuth2Template，
 * 在我们的应用服务中使用我们定义的，OAuth2Template类的子类QQOAuth2Template项目启动时执行构造方法并父抽象类OAuth2Template的构造方法创建实例并初始化
 * <p>
 * 第四步:"4.申请令牌"
 * OAuth2Template类执行exchangeForAccess方法，该方法最终执行该类的postForAccessGrant方法，
 * 在该方法中执行extractAccessGrant方法，在该方法的参数中调用getRestTemplate方法，
 * 该方法会调用createRestTemplate方法创建RestTemplate实体，
 * 然后调用该实体的postForObject方法，传入作为响应返回值类型的参数Map.class，
 * 该方法的作用是向"服务提供商"发送POST请求并将返回值封装到以Key、Value的形式封装到Map类型实体中返回
 * <p>
 * 第五步:"5.发放令牌"
 * 如果"服务提供商"返回的Map类型响应返回值不为null，则将响应返回值传入到extractAccessGrant方法中，
 * 该方法从Map类型响应返回值中取出四个参数名为"access_token"、"scope"、"refresh_token"、"expires_in"的值，
 * 最后调用createAccessGrant方法并将四个参数的值传入该方法创建AccessGrant实体并返回，
 * 并调用SocialAuthenticationToken的构造方法，将AccessGrant作为构造方法的参数，
 * 返回创建的SocialAuthenticationToken实体，
 * 最终SocialAuthenticationFilter调用attemptAuthentication方法获得了SocialAuthenticationToken，
 * 获得了服务提供商发送的令牌
 * <p>
 * 注意内容：
 * 如果返回的Map类型响应返回值为null，则SocialAuthenticationFilter过滤器调用attemptAuthentication获得的SocialAuthenticationToken为空，
 * 则会执行throw new AuthenticationServiceException("authentication failed")抛出异常，
 * 最终AuthenticationServiceException异常类被SocialAuthenticationFilter过滤器类的父类AbstractAuthenticationProcessingFilter类捕获，
 * 并执行该类的unsuccessfulAuthentication方法，将异常作为参数传入其中，
 * 在该方法中调用作为AbstractAuthenticationProcessingFilter类的属性AuthenticationFailureHandler失败处理器接口的onAuthenticationFailure方法，
 * 当实例化SocialAuthenticationFilter过滤器类时，创建AuthenticationFailureHandler失败处理器接口的实现类SimpleUrlAuthenticationFailureHandler，
 * 在执行SimpleUrlAuthenticationFailureHandler类的构造方法时，
 * 并将SocialAuthenticationFilter过滤器类静态变量DEFAULT_FAILURE_URL的值"/signin"作为参数传入其中，
 * 该构造方法会调用setDefaultFailureUrl方法，传入默认失败请求路径DEFAULT_FAILURE_URL的值作为参数，
 * 该方法最终执行UrlUtils.isValidRedirectUrl静态方法，传入默认失败请求路径作的值为参数，
 * 该方法的作用是，重定向到参数值的请求路径上，
 * 所以当SocialAuthenticationFilter过滤器调用attemptAuthentication获得的SocialAuthenticationToken为空，
 * 获取不到服务提供商发送的令牌时，会重定向"/signin"请求，
 * <p>
 * 但是创建的RestTemplate实体并没有添加对html的响应数据的处理，
 * 因为在OAuth2Template类执行exchangeForAccess方法，该方法最终执行该类的postForAccessGrant方法，
 * 在该方法中执行extractAccessGrant方法，在该方法的参数中调用getRestTemplate方法，
 * 该方法会调用createRestTemplate方法并创建RestTemplate实体，
 * 在该方法中对创建的RestTemplate实体执行setMessageConverters方法，增加响应处理的转换器类，
 * List<HttpMessageConverter<?>> converters = new ArrayList(2);
 * converters.add(new FormHttpMessageConverter());//处理Content-Type为 "multipart/form-data"的响应数据
 * converters.add(new FormMapHttpMessageConverter());//处理Map类型的响应数据
 * converters.add(new MappingJackson2HttpMessageConverter());//处理Json类型的响应数据
 * restTemplate.setMessageConverters(converters);
 * 并没有处理html的响应数据，
 * 所以我们还需要创建OAuth2Template类的子类QQOAuth2Template，
 * 重写createRestTemplate方法并创建RestTemplate实体，
 * 在该方法中对创建的RestTemplate实体执行getMessageConverters方法，
 * 获得RestTemplate实体的List<HttpMessageConverter<?>>集合属性，
 * 向该集合属性增加StringHttpMessageConverter()处理网页类型响应数据，
 * 并返回创建的RestTemplate实体
 * <p>
 * 第五步:"5.发放令牌"如果"服务提供商"返回的Map类型响应返回值不为null，则将响应返回值传入到extractAccessGrant方法中，
 * 该方法从Map类型响应返回值中取出四个参数名为"access_token"、"scope"、"refresh_token"、"expires_in"的值，
 * 最后调用createAccessGrant方法并将四个参数的值传入该方法创建AccessGrant实体并返回，
 * "服务提供商"返回的Map类型响应返回值是Oauth2官方规定的返回值类型，
 * 但是在http://wiki.connect.qq.com/，
 * QQ开放平台中点击网站应用，
 * 网站开发流程，点击"获取Access_Token"，
 * 找到"（可选）权限自动续期，获取Access Token","返回说明"，
 * QQ"服务提供商"响应返回值字符串为：access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
 * QQ"服务提供商"执行"5.发放令牌"返回的结果是String字符串，
 * 所以我们要重写OAuth2Template类的postForAccessGrant方法，
 * 以"&"符号分割响应返回值字符串，得到以"="分割的响应参数和值，并保存到字符串集合中，其中响应值部分内容只显示"************************"暗码
 * 从字符串中取出每个元素，从每个元素中被"="分割的响应参数和值中取出响应参数值，
 * 重新赋值给AccessGrant类的构造方法的参数中，创建AccessGrant实体并返回，
 * 并调用SocialAuthenticationToken的构造方法，将AccessGrant作为构造方法的参数，
 * 返回创建的SocialAuthenticationToken实体，
 * 最终SocialAuthenticationFilter调用attemptAuthentication方法获得了SocialAuthenticationToken，
 * 获得了服务提供商发送的令牌
 *
 * @author 李建珍
 * @date 2019/4/28
 */
public class QqOAuth2Template extends OAuth2Template {
    private Logger logger = LoggerFactory.getLogger(QqOAuth2Template.class);

    /**
     * @param clientId
     * @param clientSecret
     * @param authorizeUrl
     * @param accessTokenUrl
     */
    public QqOAuth2Template(String clientId, String clientSecret,
                            String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /**
         * 在http://wiki.connect.qq.com/，QQ开放平台中点击网站应用，网站开发流程，
         * 点击"获取Access_Token"，找到"（可选）权限自动续期，获取Access Token","请求参数"，"grant_type"、"client_id"、"client_secret"、"refresh_token"这四个参数是"
         * 4.申请令牌"步骤，向QQ"服务提供商"发送获取令牌请求必填的四个参数，
         * 因为这四个参数在OAuth2Operations接口的实现类OAuth2Template的exchangeForAccess方法中必须设置，
         * 所以QQ"服务提供商"也必须接收这四个参数，
         * 但是设置"client_id"和"client_secret"这两个参数的判断条件是useParametersForClientAuthentication值为true，
         * 所以需要我们在初始化OAuth2Template类的时候，将该类的useParametersForClientAuthentication属性初始化为true
         */
        //看看代码org.springframework.social.oauth2.OAuth2Template.exchangeForAccess
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 响应返回值字符串为：access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     * 以"&"符号分割响应返回值字符串，得到以"="分割的响应参数和值，并保存到字符串集合中，其中响应值部分内容只显示"************************"暗码
     * <p>
     * 从字符串中取出每个元素，从每个元素中被"="分割的响应参数和值中取出响应参数值，
     * 重新赋值给AccessGrant类的构造方法的参数中，创建AccessGrant实体并返回，
     * 并调用SocialAuthenticationToken的构造方法，将AccessGrant作为构造方法的参数， 返回创建的SocialAuthenticationToken实体，
     * 最终SocialAuthenticationFilter调用attemptAuthentication方法获得了SocialAuthenticationToken,获得了服务提供商发送的令牌
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseString = this.getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.error("获取accessToke的响应：" + responseString);
        String errorDescription = StringUtils.substringBetween(responseString, "\"error_description\":\"", "\"}");
        if (StringUtils.isNotBlank(errorDescription)) {
            throw new SocialException(errorDescription) {
            };
        }
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseString, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = Long.parseLong(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        //重新构建授权类
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    /**
     * 进行消息转换
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
