package com.ligoo.rpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 14:42:37
 * @Description:
 */
public interface HelloService extends Remote {
    String sayHello(String someOne) throws RemoteException;
}
