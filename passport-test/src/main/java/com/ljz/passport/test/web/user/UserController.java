package com.ljz.passport.test.web.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.ljz.passport.test.dto.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李建珍
 * @date 2019/3/17
 */
@RestController
@RequestMapping("/user")
public class UserController {


     @GetMapping("/me")
     public Object getMe() {
         return SecurityContextHolder.getContext().getAuthentication();
     }
    /*@GetMapping("/me")
    public Object getMe(Authentication authentication) {
        return authentication;
    }*/
    /*@GetMapping("/me")
    public Object getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }*/

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


}
