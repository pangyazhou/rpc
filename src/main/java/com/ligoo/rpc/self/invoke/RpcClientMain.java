package com.ligoo.rpc.self.invoke;

import com.ligoo.rpc.self.framework.ConsumerProxy;
import com.ligoo.rpc.self.service.HelloService;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 15:50:09
 * @Description:
 */
public class RpcClientMain {
    public static void main(String[] args) throws Exception {
        HelloService helloService = ConsumerProxy.consume(HelloService.class, "127.0.0.1", 8081);
        for (int i = 0; i < 10; i++){
            String hello = helloService.sayHello("pangyazhou_" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }

    }
}
