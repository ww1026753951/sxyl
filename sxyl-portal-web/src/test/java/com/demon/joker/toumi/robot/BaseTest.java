package com.demon.joker.toumi.robot;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTest {
    protected static ApplicationContext appContext;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            long start = System.currentTimeMillis();
            appContext = new ClassPathXmlApplicationContext(new String[]{"spring-config.xml"});
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ItemManager categoryService = (ItemManager) appContext.getBean("itemManager");
    }

    @Before
    public void autoSetBean() {
        appContext.getAutowireCapableBeanFactory().autowireBeanProperties(this, DefaultListableBeanFactory.AUTOWIRE_BY_NAME, false);

    }
//    protected abstract void init();
}
