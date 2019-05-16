package com.ljz.passport.core.validate.code;

import com.ljz.passport.core.validate.code.repository.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 对于process的抽象实现
 *
 * @author 李建珍
 * @date 2019/3/23
 */
public abstract class AbsctractValidateCodeProcessor<V extends ValidateCode> implements ValidateCodeProcessor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ValidateCodeRepository validateCodeRepository;
    /**
     * 将依赖中所有的generator获得
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //生成验证码
        V validateCode = generate(request);
        //保存session到别的地方
        save(request, validateCode);
        //发送到浏览器和短信
        send(request, validateCode);
    }

    /**
     * 发送验证码，手机验证码
     *
     * @param request
     * @param validateCode
     */
    protected abstract void send(ServletWebRequest request, V validateCode) throws Exception;

    /**
     * 保存到session存储中
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, V validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.setValidateCode(request, getSessionKey(), code);
    }

    /**
     * 通过请求类型生成对应的验证码
     *
     * @param request
     * @return
     */
    private V generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + type + "CodeGenerator" + "不存在");
        }
        return (V) validateCodeGenerator.generate(request);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @return
     */
    private String getSessionKey() {
        return SESSION_VALIDATE_CODE_KEY_PREFIX + getValidateCodeType().toString().toUpperCase();
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidateCodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 做验证码校验的封装
     *
     * @param request
     * @throws ValidateCodeException
     */
    @Override
    public void validate(ServletWebRequest request) throws ValidateCodeException {
        //从请求中取出之前存入session的验证码
        ValidateCode imageCode = (ValidateCode) validateCodeRepository.getValidateCode(request, getValidateSessionKey());
        //获取form表单中用户输入的验证码
        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), getValidateParameterName());
        } catch (ServletRequestBindingException e) {
            logger.error("验证码参数解析有错，核对图片验证码参数是否绑定错误，绑定name为{}", getValidateParameterName());
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (imageCode == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (imageCode.isExpired()) {
            validateCodeRepository.removeValidateCode(request, getValidateSessionKey());
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(imageCode.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        validateCodeRepository.removeValidateCode(request, getValidateSessionKey());
    }


    /**
     * 获取对应的session的key
     *
     * @return
     */
    protected abstract String getValidateSessionKey();

    /**
     * 获取需要校验的请求字段的值
     *
     * @return
     */
    protected abstract String getValidateParameterName();
}

