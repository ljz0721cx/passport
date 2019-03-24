package com.ljz.passport.core.validate.code.sms;

/**
 * 短信验证码发送接口
 *
 * @author 李建珍
 * @date 2019/3/23
 */
public interface SmsCodeSender {
    /**
     * 发送短信验证码,这里建议提供对应的服务，做转发和条数的限制。
     * 调用单独的短信和邮件服务器
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
