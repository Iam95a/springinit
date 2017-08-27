package com.chen;

import com.chen.aspect.BeforeExample;
import com.chen.service.UserAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws Exception{
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application.xml"});
        UserAccountService service=context.getBean("userAccountService",UserAccountService.class);

        System.out.println(service.getUser());
//        context.getBean("beforeExample", BeforeExample.class).doAccessCheck();
    }

}
