package com.ligoo.rpc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 14:43:44
 * @Description:
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService{

    private static final long serialVersionUID = 6545184187138929195L;

    public HelloServiceImpl() throws RemoteException{
        super();
    }
    @Override
    public String sayHello(String someOne) throws RemoteException {
        return "hello " + someOne;
    }
}
