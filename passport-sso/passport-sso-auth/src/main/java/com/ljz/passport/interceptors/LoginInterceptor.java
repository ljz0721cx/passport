package com.ljz.passport.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 *
 * @author 李建珍
 * @date 2019/5/30
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final String AUTH_CODE_NAME = "code";
    private static final String EXCLUDS_SCRIPT = "\"</?[^>]+>\"";
    private Logger logger = LoggerFactory.getLogger((LoginInterceptor.class));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //是否判断需要授权的token
        //request.getHeader("");
        //如果需要执行后续调用返回true
        String code = request.getParameter(AUTH_CODE_NAME);
        if (null == code || "".equals(code)) {
            return false;
        }
        //处理code中的编码
        String codeInfo = code.replaceAll(EXCLUDS_SCRIPT, "");
        //重写入code
        if (null != codeInfo || !"".equals(codeInfo)) {
            //不能继续进行 需要输出信息
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
