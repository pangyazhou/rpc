package com.ligoo.rpc.spring.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 16:02:40
 * @Description:
 */
public class RmiInvokerServer {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:rmi-rpc-server.xml");

    }
}
