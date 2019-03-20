package com.ljz.passport.test.web.user;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

import com.ljz.passport.test.dto.User;
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
