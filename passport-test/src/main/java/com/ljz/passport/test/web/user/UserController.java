package com.ljz.passport.test.web.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.ljz.passport.test.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李建珍
 * @date 2019/3/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /*@GetMapping("/me")
    public Object getMe() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }*/
    /*@GetMapping("/me")
    public Object getMe(Authentication authentication) {
        return authentication;
    }*/
    @GetMapping("/me")
    public Object getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
    /**
     * 查询用户的信息列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(User.class)
    public List<User> list() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        return users;
    }


    /**
     * FIXME
     * 注册用户
     * providerSignInUtils用于第三方登录，不需要进行处理了。
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
        providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
    }

}
