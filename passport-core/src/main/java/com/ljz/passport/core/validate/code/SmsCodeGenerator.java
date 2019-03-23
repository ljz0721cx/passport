package com.ljz.passport.core.validate.code;

import com.ljz.passport.core.properties.SecurityProperties;
import com.ljz.passport.core.validate.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author 李建珍
 * @date 2019/3/22
 */
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 生成简单的验证码
     *
     * @param request
     * @return
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }
}
