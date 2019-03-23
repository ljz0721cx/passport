package com.ljz.passport.core.validate.code.sms;

import com.ljz.passport.core.validate.code.AbsctractValidateCodeProcessor;
import com.ljz.passport.core.validate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 李建珍
 * @date 2019/3/23
 */
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbsctractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
