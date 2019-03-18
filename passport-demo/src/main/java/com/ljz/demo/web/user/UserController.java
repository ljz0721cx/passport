package com.ljz.demo.web.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.ljz.demo.dto.User;
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
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @JsonView(User.class)
    public List<User> list() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        return users;
    }


}
