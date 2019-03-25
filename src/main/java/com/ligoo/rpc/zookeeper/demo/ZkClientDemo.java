package com.ligoo.rpc.zookeeper.demo;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 16:09:50
 * @Description:
 */
public class ZkClientDemo {
    public static void main(String[] args) throws InterruptedException {
        new ZkClientDemo().await();
    }

    public void await() throws InterruptedException {
        // 注册服务器地址
        String zkServer = "192.168.1.24:2181";
        // 连接超时
        int connectionTimeout = 3000;
        // 客户端对象
        ZkClient zkClient = new ZkClient(zkServer, connectionTimeout);
        // 数据节点
        String path = "/zk-data";

        // 检测是否节点已存在
        if (zkClient.exists(path)){
            zkClient.delete(path);
        }
        // 创建永久节点
        zkClient.createPersistent(path);
        // 写入节点数据
        zkClient.writeData(path, "test_data_1");
        // 读取节点数据
        String data = zkClient.readData(path, true);
        System.out.println(data);

        // 监听节点数据变化
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("handleDataChange: dataPath = " + dataPath + " data = " + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("handleDataDeleted: dataPath = " + dataPath);
            }
        });

        zkClient.writeData(path, "test_data_2");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
