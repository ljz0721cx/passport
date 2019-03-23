package com.ljz.passport.core.validate.sms;

/**
 * @author 李建珍
 * @date 2019/3/23
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机" + mobile + "发送手机验证码" + code);
    }
}
