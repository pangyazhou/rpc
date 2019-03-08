package com.ligoo.rpc.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 14:50:01
 * @Description:
 */
public class ServerMain {
    public static void main(String[] args) throws Exception{
        // 创建服务
        HelloService helloService = new HelloServiceImpl();
        // 注册服务
        LocateRegistry.createRegistry(8081);
        RMISocketFactory.setSocketFactory(new CustomerSocketFactory());
        Naming.bind("rmi://localhost:8081/helloService", helloService);
        System.out.println("ServerMain provide RPC service now.");
    }
}
