package com.ljz.passport.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李建珍
 * @date 2019/5/26
 */
@RestController
public class UserController {


    @GetMapping("/user")
    public Authentication getMe(Authentication user, HttpServletRequest request) {
        return user;
    }
}
