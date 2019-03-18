package com.ljz.demo.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author 李建珍
 * @date 2019/3/18
 */
@Component
@Aspect
public class TimeAspect {

    /**
     * 执行环绕的切面方法调用
     *
     * @param pjp
     * @return
     */
    @Around("execution(* com.ljz.demo..*.*(..))")
    public Object handlerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("TimeAspect start");
        long start = System.currentTimeMillis();
        Object obj = pjp.proceed();
        System.out.println("TimeAspect 耗时：" + (System.currentTimeMillis() - start));
        System.out.println("TimeAspect  end");
        return obj;
    }
}
