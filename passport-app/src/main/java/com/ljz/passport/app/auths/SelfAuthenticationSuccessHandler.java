package com.ljz.passport.app.auths;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * 自定义认证成功处理器
 * 使用spring默认的处理器
 * 仿照BasicAuthenticationFilter
 *
 * @author 李建珍
 * @date 2019/3/20
 */
@Component("selfAuthenticationSuccessHandler")
public class SelfAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String credentialsCharset = "UTF-8";
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.info("登录成功");
        //获得属性值
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            //请求头中没有客户端信息
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        //抽取并且解码请求头里的字符串
        String[] tokens = this.extractAndDecodeHeader(header, request);
        //验证是否是有clientid和clientSecret
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        //通过clientId获取clientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            logger.error("clientId：" + clientId + "对应的信息不存在");
            throw new UnapprovedClientAuthenticationException("授权信息不匹配" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            logger.error("clientId：" + clientId + "对应的secret信息不存在");
            throw new UnapprovedClientAuthenticationException("授权信息不匹配" + clientSecret);
        }
        //map是存储authentication内属性的,因为我们这里自带authentication,所以传空map即可
        TokenRequest tokenRequest = new TokenRequest
                (MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        //clientDetails和tokenRequest合成OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        //oAuth2Request和authentication合成OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        //拿认证去获取令牌
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        //将authentication这个对象转成json格式的字符串
        response.getWriter().write(JSONArray.toJSONString(token));
    }


    /**
     * 抽取解码clientId和clientSecret
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     *                                 Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes(credentialsCharset);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }
        String token = new String(decoded, credentialsCharset);
        //以：拆分字符
        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        //获得用户名和密码
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
