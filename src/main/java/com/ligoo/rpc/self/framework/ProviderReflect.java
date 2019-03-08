package com.ligoo.rpc.self.framework;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Administrator
 * @Date: 2019/3/7 15:22:06
 * @Description:
 */
public class ProviderReflect {
    private static final ExecutorService excutorService = Executors.newCachedThreadPool();

    public static void provider(final Object service, int port) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            final Socket socket = serverSocket.accept();
            excutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try{
                            try {
                                String methodName = input.readUTF();
                                Object[] args = (Object[]) input.readObject();
                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                try {
                                    Object result = MethodUtils.invokeExactMethod(service, methodName, args);
                                    output.writeObject(result);
                                }catch (Throwable t) {
                                    output.writeObject(t);
                                } finally {
                                    output.close();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                input.close();
                            }
                        }finally {
                            socket.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
