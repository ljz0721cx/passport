package com.ljz.passport.test.web.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李建珍
 * @date 2019/3/18
 */
@RestController
public class OrderController {

    @GetMapping("/order")
    public String order() {
        return "order ";
    }
}
