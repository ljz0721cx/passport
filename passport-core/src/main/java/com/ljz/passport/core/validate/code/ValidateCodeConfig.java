package com.ljz.passport.core.validate.code;

import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.validate.code.image.ImageCodeGenerator;
import com.ljz.passport.core.validate.code.sms.SmsCodeSender;
import com.ljz.passport.core.validate.code.sms.SmsDefaultCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码默认配置的Bean
 *
 * @author 李建珍
 * @date 2019/3/22
 */

@Configuration
public class ValidateCodeConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 设置如果系统中没有的情况下才会加载当前的bean
     * 如果有自定义需要的验证码需要自己重新实现ValidateCodeGenerator的接口
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }


    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        SmsCodeSender smsCodeSender = new SmsDefaultCodeSender();
        return smsCodeSender;
    }

}
