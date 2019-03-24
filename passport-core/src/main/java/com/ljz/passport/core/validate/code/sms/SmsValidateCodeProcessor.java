package com.ljz.passport.core.validate.code.sms;

import com.ljz.passport.core.auth.SecurityConstants;
import com.ljz.passport.core.validate.code.ValidateCodeType;
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
public class SmsValidateCodeProcessor
        extends AbsctractValidateCodeProcessor<ValidateCode> {
    /**
     * 设置session的key
     */
    private final String SMS_SESSION_VALIDATE_CODE_KEY = SESSION_VALIDATE_CODE_KEY_PREFIX.concat(ValidateCodeType.SMS.name());
    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 发送短信验证码到用户手机
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils
                .getRequiredStringParameter(request.getRequest(),
                        SecurityConstants.DEFAULT_MOBILE_AUTH_LOGIN_PARAMETER_NAME);
        smsCodeSender.send(mobile, validateCode.getCode());
    }

    @Override
    protected String getValidateSeesionKey() {
        return SMS_SESSION_VALIDATE_CODE_KEY;
    }

    @Override
    protected String getValidateParameterName() {
        return SecurityConstants.DEFAULT_SMS_CODE_PARAMETER_NAME;
    }
}
