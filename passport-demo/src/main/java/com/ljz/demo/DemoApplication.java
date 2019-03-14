package com.ljz.demo;

/**
 * Created by ljz07 on 2019/3/15.
 */
@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @GetMapping("/hello")
    public String helo() {
        return "hello spring security";
    }

}
