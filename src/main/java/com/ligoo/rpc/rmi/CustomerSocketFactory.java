package com.ligoo.rpc.rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 14:59:05
 * @Description:
 */
public class CustomerSocketFactory extends RMISocketFactory {
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        if (port == 0){
            port = 8051;
        }
        System.out.println("rmi notify port:" + port);
        return new ServerSocket(port);
    }
}
