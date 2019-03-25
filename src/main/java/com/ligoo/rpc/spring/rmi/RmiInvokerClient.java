package com.ligoo.rpc.spring.rmi;

import com.ligoo.rpc.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 16:02:52
 * @Description:
 */
public class RmiInvokerClient {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:rmi-rpc-client.xml");
        UserService userService = (UserService) context.getBean("userRmiServiceProxy");
        User user = userService.findByName("pangyazhou");
        System.out.println(user);
    }
}
