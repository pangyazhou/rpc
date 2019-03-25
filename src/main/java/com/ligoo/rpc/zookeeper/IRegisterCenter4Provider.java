package com.ligoo.rpc.zookeeper;

import com.ligoo.rpc.zookeeper.model.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 16:59:46
 * @Description: 服务提供方注册中心接口
 */
public interface IRegisterCenter4Provider {
    /**
     * description:注册服务
     * author: Administrator
     * date: 2019/3/12 17:00
     *
     * @param:
     * @return:
     */
    public void registerProvider(final List<ProviderService> serviceMetaData);


    /**
     * description: 获取所有注册的服务
     * author: Administrator
     * date: 2019/3/12 17:00
     *
     * @param:
     * @return:
     */
    public Map<String, List<ProviderService>> getProviderServiceMap();
}
