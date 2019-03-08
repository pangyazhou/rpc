package com.ligoo.rpc.self.service;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 15:09:15
 * @Description:
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String content) {
        return "hello " + content;
    }
}
