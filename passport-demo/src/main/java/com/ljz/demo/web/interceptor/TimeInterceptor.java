package com.ljz.demo.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李建珍
 * @date 2019/3/17
 */

/**
 * 这里光申明spring的组件不起作用，还需要进行别的配置
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    /**
     * 拦截器和filter的区别是在当前可以有handler处理
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("TimeInterceptor preHandle start");
        System.out.println(((HandlerMethod) handler).getMethod().getName());
        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        request.setAttribute("startTime", System.currentTimeMillis());
        //后续调用返回true
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("TimeInterceptor postHandle");
        long start = (Long) request.getAttribute("startTime");
        System.out.println("Time intercept 耗时：" + (System.currentTimeMillis() - start));

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("TimeInterceptor afterCompletion");
        long start = (Long) request.getAttribute("startTime");
        System.out.println("Time intercept 耗时：" + (System.currentTimeMillis() - start));

        System.out.println("ex is :" + ex);
    }
}
