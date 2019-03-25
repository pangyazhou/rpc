package com.ligoo.rpc.zookeeper;

import com.ligoo.rpc.zookeeper.model.InvokerService;
import com.ligoo.rpc.zookeeper.model.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 17:04:43
 * @Description: 消费端服务注册中心
 */
public interface IRegisterCenter4Consume {
    /**
     * description: 消费端初始化服务提供者信息本地缓存
     * author: Administrator
     * date: 2019/3/12 17:05
     *
     * @param: 
     * @return: 
     */
    public void initProviderMap();


    /**
     * description: 消费端获取服务提供者信息
     * author: Administrator
     * date: 2019/3/12 17:05
     *
     * @param:
     * @return:
     */
    public Map<String, List<ProviderService>> getServiceMetaData4Consume();

    /**
     * description: 消费端讲消费信息注册到zk响应的节点下
     * author: Administrator
     * date: 2019/3/12 17:06
     *
     * @param:
     * @return:
     */
    public void registerInvoker(final InvokerService invokerService);
}
