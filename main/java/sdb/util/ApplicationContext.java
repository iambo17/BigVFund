package sdb.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContext {
    private static ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
    public static ClassPathXmlApplicationContext getApp(){
        return app;
    }
}
