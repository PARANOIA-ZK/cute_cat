//package com.paranoia.webfluxreactive.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
///**
// * @author PARANOIA_ZK
// * @date 2018/11/25 15:33
// */
//@Aspect
//@Component
//@Slf4j
//public class FluxAspect {
//
//    @Pointcut("execution(* com.paranoia.webfluxreactive.router..*(..))")
//    public void logPointCut() {
//    }
//
//
//    @Around("logPointCut()")
//    public Object around(ProceedingJoinPoint point) {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//
//        String className = point.getTarget().getClass().getName();
//        String methodName = signature.getName();
//        Object[] args = point.getArgs();
//
//        log.info("class :" + className +
//                " -> method : -> " + methodName +
//                " -> args : -> " + Arrays.toString(args));
//
//        try {
//            return point.proceed();
//        } catch (Throwable throwable) {
//            //经验证，webflux 异常不会在这里被捕获
//            throwable.printStackTrace();
//            log.error("something wrong : " + throwable.getLocalizedMessage());
//            return throwable.getLocalizedMessage();
//        }
//    }
//
//    @AfterThrowing(value = "logPointCut()", throwing = "throwable")
//    public void tryException(Throwable throwable) {
//        System.out.println("throwable = " + throwable);
//    }
//}
