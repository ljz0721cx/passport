package com.ljz.passport.app.social.config;

import com.ljz.passport.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * spring容器初始化之前和初始化之后时候替换signuputils
 *
 * @author 李建珍
 * @date 2019/5/17
 */
@Component
public class SpringSocialBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    /**
     * 在MySpringSocialConfigurer初始化好之后将signupUrl改掉
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //找到这个bean
        if (StringUtils.equals(beanName, "springSocialConfigurer")) {
            MySpringSocialConfigurer configurer = (MySpringSocialConfigurer) bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
