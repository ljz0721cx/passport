package com.ljz.passport.core.validate.code.repository;

import com.ljz.passport.core.validate.code.ValidateCode;
import com.ljz.passport.core.validate.code.ValidateCodeType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码的存储接口声明
 * 基于session的存储实现
 * 基于token登录的实现
 *
 * @author 李建珍
 * @date 2019/5/15
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateKey
     */
    void setValidateCode(ServletWebRequest request, String validateKey, ValidateCode code);

    /**
     * 获取验证码
     *
     * @param request
     * @param validateKey
     * @return
     */
    Object getValidateCode(ServletWebRequest request, String validateKey);

    /**
     * 移除验证码
     *
     * @param request
     * @param validateKey
     */
    void removeValidateCode(ServletWebRequest request, String validateKey);

}
