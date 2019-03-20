package com.ljz.passport.demo.web.config;

import com.ljz.passport.demo.web.filter.TimeFilter;
import com.ljz.passport.demo.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李建珍
 * @date 2019/3/17
 * 1.filter初始化
 * 2.filter 开始
 * 3.Interceptor preHandle
 * 4.Interceptor postHandle
 * 5.Interceptor afterCompletion
 * 6.filter多次调用
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

   /* @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerCallableInterceptors();
        configurer.registerDeferredResultInterceptors()

    }*/

    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);
        List<String> urls = new ArrayList();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }


    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 配置拦截器，组件注入不能生效，所以下边的方法必须有声明实现
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor)
                .addPathPatterns("/*");
    }
}
