package com.ljz.passport.browser.validate;

import com.ljz.passport.core.validate.code.ValidateCode;
import com.ljz.passport.core.validate.code.repository.ValidateCodeRepository;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 李建珍
 * @date 2019/5/15
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy;

    @Override
    public void setValidateCode(ServletWebRequest request, String validateCodeType, ValidateCode code) {
        sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), code);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @param validateCodeType
     * @return
     */
    private String getSessionKey(ServletWebRequest request, String validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toUpperCase();
    }

    @Override
    public ValidateCode getValidateCode(ServletWebRequest request, String validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType));
    }

    @Override
    public void removeValidateCode(ServletWebRequest request, String codeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, codeType));
    }


}