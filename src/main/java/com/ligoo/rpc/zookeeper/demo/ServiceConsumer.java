package com.ligoo.rpc.zookeeper.demo;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 16:38:35
 * @Description:
 */
public class ServiceConsumer {
    private static final String zkServer = "192.168.1.24:2181";
    private static final int connectionTimeout = 3000;

    public static void main(String[] args){
        String path = "/provider";
        new ServiceConsumer().subscribe(path);
    }

    public void subscribe(String path){
        ZkClient zkClient = new ZkClient(zkServer, connectionTimeout);
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("handleDataChange: path = " + s + " data = " + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("handleDataDeleted: path = " + s);
            }
        });
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
