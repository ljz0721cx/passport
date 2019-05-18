package com.ljz.passport.oauth.web;

import com.ljz.passport.app.social.OauthSignUpUtils;
import com.ljz.passport.oauth.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李建珍
 * @date 2019/3/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OauthSignUpUtils oauthSignUpUtils;

    @GetMapping("/me")
    public Authentication getMe(Authentication user, HttpServletRequest request) {
        return user;
    }

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

}
