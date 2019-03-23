package com.ljz.passport.core.validate.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 对于process的抽象实现
 *
 * @author 李建珍
 * @date 2019/3/23
 */
public abstract class AbsctractValidateCodeProcessor<V> implements ValidateCodeProcessor {

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
     * 保存验证码到session中
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, V validateCode) {
        /**
         * 拼接请求类型到sessionkey中
         */
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX.concat(getProcessorType(request).toUpperCase()), validateCode);
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
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/validate/");
    }
}
