package com.ligoo.rpc.self.framework;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 15:11:17
 * @Description:
 */
public class ConsumerProxy {
    /**
     * description: 服务消费代理接口
     * author: Administrator
     * date: 2019/3/7 15:11
     *
     * @param:
     * @return:
     */
    public static <T> T consume(final Class<T> interfaceClass, final String host, final int port) throws Exception{
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host, port);
                try{
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        output.writeUTF(method.getName());
                        output.writeObject(args);
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try{
                            Object result = input.readObject();
                            if (result instanceof Throwable){
                                throw (Throwable) result;
                            }
                            return result;
                        }finally {
                            input.close();
                        }
                    }finally {
                        output.close();
                    }
                }finally {
                    socket.close();
                }
            }
        });
    }
}
