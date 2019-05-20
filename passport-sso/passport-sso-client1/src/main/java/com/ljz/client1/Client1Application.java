package com.ljz.client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李建珍
 * @date 2019/5/20
 */
@SpringBootApplication
@RestController
public class Client1Application {


    /**
     * 请求认证信息
     *
     * @param authentication
     * @return
     */
    @GetMapping("/user")
    public Authentication user(Authentication authentication) {
        return authentication;
    }

    public static void main(String[] args) {
        SpringApplication.run(Client1Application.class, args);
    }

}
