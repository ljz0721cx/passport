package com.ljz.passport.core.validate.code;

import com.ljz.passport.core.auth.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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
public abstract class AbsctractValidateCodeProcessor<V> implements ValidateCodeProcessor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 将依赖中所有的generator获得
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //生成验证码
        V validateCode = generate(request);
        //保存session和别的地方
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
     * TODO 保存验证码到session中  回看
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, V validateCode) {
        /**
         * 拼接请求类型到sessionkey中
         */
        sessionStrategy
                .setAttribute(request, SESSION_VALIDATE_CODE_KEY_PREFIX.concat(getProcessorType(request).toUpperCase()),
                        validateCode);
    }

    /**
     * 通过请求类型生成对应的验证码
     *
     * @param request
     * @return
     */
    private V generate(ServletWebRequest request) {
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        return (V) validateCodeGenerator.generate(request);
    }

    /**
     * 获取请求路径的后半段
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils
                .substringAfter(request.getRequest().getRequestURI(),
                        "/validate/");
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
        ValidateCode imageCode = (ValidateCode) sessionStrategy.getAttribute(request, getValidateSeesionKey());
        //获取form表单中用户输入的验证码
        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),getValidateParameterName());
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
            sessionStrategy.removeAttribute(request, getValidateSeesionKey());
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(imageCode.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, getValidateSeesionKey());
    }


    /**
     * 获取对应的session的key
     *
     * @return
     */
    protected abstract String getValidateSeesionKey();

    /**
     * 获取需要校验的请求字段的值
     *
     * @return
     */
    protected abstract String getValidateParameterName();
}

