package com.fzg.imooc.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAdvice {
    private Logger logger = LoggerFactory.getLogger(MyAdvice.class);

    // 定义切面
    @Pointcut(value = "execution( * com.fzg.imooc.aop.controller.*.*(..))")
    public void myPointcut(){

    }
    // 定义通知
    @Around("myPointcut()")
    public Object myLogger(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().toString();
        String methodName = pjp.getSignature().getName();
        Object array = pjp.getArgs();

        ObjectMapper mapper = new ObjectMapper();
        logger.info("调用前：" + className + methodName + "传递的参数为：" + mapper.writeValueAsString(array));

        Object obj = pjp.proceed();

        logger.info("调用后：" + className + methodName + "返回值为：" + mapper.writeValueAsString(obj));

        return obj;
    }
}
