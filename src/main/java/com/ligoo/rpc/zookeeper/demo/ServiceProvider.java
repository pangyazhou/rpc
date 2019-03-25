package com.ligoo.rpc.zookeeper.demo;

import org.I0Itec.zkclient.ZkClient;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 16:37:35
 * @Description:
 */
public class ServiceProvider {
    private static final String zkServer = "192.168.1.24:2181";
    private static final int connectionTimeout = 3000;

    public static void main(String[] args){
        String path = "/provider";
        String url = "http://127.0.0.1:8080/helloService";
        new ServiceProvider().provider(path, url);
    }
    public void provider(String path, String url){
        ZkClient zkClient = new ZkClient(zkServer, connectionTimeout);
        if (zkClient.exists(path)){
            zkClient.delete(path);
        }
        zkClient.createEphemeral(path);
        zkClient.writeData(path, url);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
