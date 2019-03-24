package com.ljz.passport.core.validate.code.sms;

/**
 * 这里简单的实现默认的短信发送
 * TODO 需要接入真是的短信发送
 *
 * @author 李建珍
 * @date 2019/3/23
 */
public class SmsDefaultCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机" + mobile + "发送手机验证码" + code);
    }
}
