package com.paranoia.upupup.aspect.one;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author ZHANGKAI
 * @date 2019/6/14
 * @description
 */
@Aspect
@Component
public class InjectAspect {


    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }


    @Before("restController()")
    public void restController(JoinPoint point) throws Throwable {

        Object[] args = point.getArgs();
        for (Object arg : args) {
            Inject annotation = arg.getClass().getAnnotation(Inject.class);
            if (annotation != null) {
                Field userNameField = arg.getClass().getDeclaredField("userName");
                userNameField.setAccessible(true);
                String userName = ((String) userNameField.get(arg));
                System.out.println("userName = " + userName);
                userNameField.set(arg, userName + "--update");


                Field createdAtField = arg.getClass().getSuperclass().getDeclaredField("createdAt");
                createdAtField.setAccessible(true);
                String createdAtFields = ((String) createdAtField.get(arg));
                System.out.println("createdAtFields = " + createdAtFields);
                createdAtField.set(arg, new Date());
            }
        }
    }
}
