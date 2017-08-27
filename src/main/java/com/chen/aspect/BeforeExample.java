package com.chen.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeforeExample {

    @Before("com.chen.aspect.SystemArchitecture.businessService()")
    public void doAccessCheck() {
        System.out.println("before service execution");
    }

    @Before("com.chen.aspect.SystemArchitecture.logOperation()")
    public void doLog(){

        System.out.println("log开始了");
    }

}
