package com.ligoo.rpc.spring.factorybean;

import com.ligoo.rpc.model.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:43:50
 * @Description:
 */
public class UserFactoryBeanTest {

    @Test
    public void getObject() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
    }
}