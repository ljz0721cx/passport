package com.ljz.passport.config;

import com.ljz.passport.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author 李建珍
 * @date 2019/5/31
 */
@Configuration
@Deprecated
public class SecurityConfig extends WebMvcConfigurationSupport {
//     以下WebMvcConfigurerAdapter 比较常用的重写接口
//     /** 解决跨域问题 **/
//     public void addCorsMappings(CorsRegistry registry) ;
//     /** 添加拦截器 **/
//     void addInterceptors(InterceptorRegistry registry);
//     /** 这里配置视图解析器 **/
//     void configureViewResolvers(ViewResolverRegistry registry);
//     /** 配置内容裁决的一些选项 **/
//     void configureContentNegotiation(ContentNegotiationConfigurer
//     configurer);
//     /** 视图跳转控制器 **/
//     void addViewControllers(ViewControllerRegistry registry);
//     /** 静态资源处理 **/
//     void addResourceHandlers(ResourceHandlerRegistry registry);
//     /** 默认静态资源处理器 **/
//     void configureDefaultServletHandling(DefaultServletHandlerConfigurer
//     configurer);

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new LoginInterceptor()).addPathPatterns("/auth/token");
        super.addInterceptors(registry);
    }

}

