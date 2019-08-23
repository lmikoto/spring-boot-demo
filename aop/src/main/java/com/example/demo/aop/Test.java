package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class Test {

    @Pointcut(value="execution(public String test* (..))")
    public void testCut(){

    }

    @Before("testCut()")
    public void before(){
        log.info("before cut");
    }

    @After("testCut()")
    public void after(){
        log.info("after cut");
    }

    @Around("testCut()")
    public Object arount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long start = System.currentTimeMillis();
        log.info("start {}",proceedingJoinPoint.getSignature().getName());
        Object obj = null;
        try {
           obj =  proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
//            throwable.printStackTrace();
            throw throwable;
        }
        log.info("end {}, {}",proceedingJoinPoint.getSignature().getName(),System.currentTimeMillis() - start);
        return obj;
    }

    @AfterReturning(pointcut = "testCut()")
    public void afterReturn(){
        log.info("after return");
    }

    @AfterThrowing(throwing = "e",pointcut = "testCut()")
    public void exception(Throwable e){
        log.error("error is {}",e.getMessage());
    }
}
