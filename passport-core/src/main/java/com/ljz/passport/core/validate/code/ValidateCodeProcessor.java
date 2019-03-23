package com.ljz.passport.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author 李建珍
 * @date 2019/3/23
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时候的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     *
     * @param request 封装请求和响应的对象
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;
}
