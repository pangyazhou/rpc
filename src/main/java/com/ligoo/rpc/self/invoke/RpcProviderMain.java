package com.ligoo.rpc.self.invoke;

import com.ligoo.rpc.self.framework.ProviderReflect;
import com.ligoo.rpc.self.service.HelloService;
import com.ligoo.rpc.self.service.HelloServiceImpl;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 15:49:52
 * @Description:
 */
public class RpcProviderMain {
    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        ProviderReflect.provider(helloService, 8081);
    }
}
