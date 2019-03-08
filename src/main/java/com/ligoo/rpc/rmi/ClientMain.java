package com.ligoo.rpc.rmi;

import java.rmi.Naming;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 14:52:52
 * @Description:
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8081/helloService");
        System.out.println("RMI服务器返回的结果是: " + helloService.sayHello("lixiaolong"));
    }
}
