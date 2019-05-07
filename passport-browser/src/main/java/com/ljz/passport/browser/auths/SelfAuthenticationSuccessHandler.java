package com.ljz.passport.browser.auths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.passport.core.properties.LoginType;
import com.ljz.passport.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证成功处理器
 * 使用spring默认的处理器
 *
 * @author 李建珍
 * @date 2019/3/20
 */
@Component("selfAuthenticationSuccessHandler")
public class SelfAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.info("登录成功");

        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=utf-8");
            //{"authorities":[{"authority":"ROLE_USER"}],"details":{"remoteAddress":"0:0:0:0:0:0:0:1","sessionId":"71CD5B1076466BBFB76824D97636E204"},"authenticated":true,
            // "principal":{"password":null,"username":"user","authorities":[{"authority":"ROLE_USER"}],"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true},"credentials":null,"name":"user"}
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
            response.flushBuffer();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
