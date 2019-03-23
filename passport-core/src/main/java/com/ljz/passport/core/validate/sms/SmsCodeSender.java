package com.ljz.passport.core.validate.sms;

/**
 * 短信验证码发送接口
 * @author 李建珍
 * @date 2019/3/23
 */
public interface SmsCodeSender {
    /**
     * 发送短信验证码
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
