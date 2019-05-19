package com.ljz.passport.oauth.web;

import com.ljz.passport.app.social.OauthSignUpUtils;
import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.oauth.dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author 李建珍
 * @date 2019/3/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OauthSignUpUtils oauthSignUpUtils;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * FIXME
     * 注册用户
     * oauthSignUpUtils用于第三方登录，不需要进行处理了。
     *
     * @param user
     */
    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {
        /**
         * 获取到用户的ID，也可以是openId
         */
        String username = user.getUsername();
        //将绑定的数据存入数据库中
        oauthSignUpUtils.doPostSignUp(new ServletWebRequest(request), username);
    }


    @GetMapping("/me")
    public Authentication getMe(Authentication user, HttpServletRequest request) {
        String headr = request.getHeader("Authorization");
        String token = StringUtils.substringAfterLast(headr, "bearer");
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
            String username = (String) claims.get("user_name");
            logger.info("获得的该用户信息" + username);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }
}
