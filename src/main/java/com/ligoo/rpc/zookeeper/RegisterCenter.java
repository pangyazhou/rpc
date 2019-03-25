package com.ligoo.rpc.zookeeper;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.ligoo.rpc.zookeeper.helper.ConfigHelper;
import com.ligoo.rpc.zookeeper.helper.IPHelper;
import com.ligoo.rpc.zookeeper.model.InvokerService;
import com.ligoo.rpc.zookeeper.model.ProviderService;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 17:13:51
 * @Description: 自定义实现注册中心
 */
public class RegisterCenter implements IRegisterCenter4Provider, IRegisterCenter4Consume {
    private static final Logger logger = LoggerFactory.getLogger(RegisterCenter.class);

    private static final RegisterCenter registerCenter = new RegisterCenter();
    // 服务提供者列表: key: 服务提供者接口, value: 服务提供者提供服务方法列表
    private static final Map<String, List<ProviderService>> providerServiceMap = new ConcurrentHashMap<>();
    // 服务端zk服务元信息
    private static final Map<String, List<ProviderService>> serviceMetaDataMap4Consume = new ConcurrentHashMap<>();
    // 从配置文件中获取注册中心的服务地址列表
    private static  String ZK_SERVICE = ConfigHelper.getZkService();
    // 从配置文件中读取zk的会话超时时间
    private static  int ZK_SESSION_TIME_OUT = ConfigHelper.getSessionTimeout();
    // 从配置文件中读取zk的连接超时时间
    private static  int ZK_CONNECTION_TIME_OUT = ConfigHelper.getConnectionTimeout();
    // 组装zk根路径
    private static  String ROOT_PATH = "/config_register";
    private static  String PROVIDER_TYPE = "/provider";
    private static  String CONSUMER_TYPE = "/consumer";
    private static String remoteAppKey = "app";
    private static volatile ZkClient zkClient = null;

    // 构造方法
    private RegisterCenter() {
    }

    public static RegisterCenter singleton(){
        return registerCenter;
    }

    @Override
    public void initProviderMap() {
        if (CollectionUtils.isEmpty(serviceMetaDataMap4Consume)){
            serviceMetaDataMap4Consume.putAll(fetchOrUpdateServiceMetaData(remoteAppKey));
        }
    }

    @Override
    public Map<String, List<ProviderService>> getServiceMetaData4Consume() {
        return serviceMetaDataMap4Consume;
    }

    /**
     * description: 在服务注册中心注册服务消费者
     * author: Administrator
     * date: 2019/3/18 14:33
     *
     * @param: 
     * @return: 
     */
    @Override
    public void registerInvoker(InvokerService invokerService) {
        if (invokerService == null){
            return;
        }
        synchronized (RegisterCenter.class){
            if (zkClient == null){
                zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }
            // 创建ZK命名空间
            boolean exist = zkClient.exists(ROOT_PATH);
            if (!exist){
                zkClient.createPersistent(ROOT_PATH, true);
            }
            // 创建服务消费者节点
            String remoteAppKey = invokerService.getRemoteAppKey();
            String serviceNode = invokerService.getServiceItf().getName();
            String servicePath = ROOT_PATH + "/" + remoteAppKey + "/" + serviceNode + "/" + CONSUMER_TYPE;
            exist = zkClient.exists(servicePath);
            if (!exist){
                zkClient.createPersistent(servicePath, true);
            }

            // 创建当前服务器节点
            String localIp = IPHelper.localIp();
            String currentServiceIpNode = servicePath + "/" + localIp;
            exist = zkClient.exists(currentServiceIpNode);
            if (!exist){
                zkClient.createEphemeral(currentServiceIpNode);
            }
        }
    }
    
   /**
    * description: 在服务注册中心注册服务提供者
    * author: Administrator
    * date: 2019/3/18 11:02
    *
    * @param: 
    * @return: 
    */ 
    @Override
    public void registerProvider(List<ProviderService> serviceMetaData) {
        if (CollectionUtils.isEmpty(serviceMetaData)){
            return;
        }
        // 连接ZK,注册服务
        synchronized (RegisterCenter.class){
            for (ProviderService provider: serviceMetaData){
                String serviceItf = provider.getServiceItf().getName();
                List<ProviderService> providers = providerServiceMap.get(serviceItf);
                if (providers == null){
                    providers = Lists.newArrayList();
                }
                providers.add(provider);
                providerServiceMap.put(serviceItf, providers);
            }
            if (zkClient == null){
                zkClient = new ZkClient(ZK_SERVICE,ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }
            // 创建Zk命名空间 / 当前部署应用APP命名空间
            String APP_KEY = serviceMetaData.get(0).getAppKey();
            String ZK_PATH = ROOT_PATH + "/" + APP_KEY;
            boolean exist = zkClient.exists(ZK_PATH);
            if (!exist){
                zkClient.createPersistent(ZK_PATH, true);
            }
            for (Map.Entry<String, List<ProviderService>> entry: providerServiceMap.entrySet()){
                // 创建服务提供者节点
                String serviceNode = entry.getKey();
                String servicePath = ZK_PATH + "/" + serviceNode + "/" + PROVIDER_TYPE;
                exist = zkClient.exists(servicePath);
                if (!exist){
                    zkClient.createPersistent(servicePath, true);
                }
                // 创建当前服务器节点
                int serverPort = entry.getValue().get(0).getServerPort();
                String localIp = IPHelper.localIp();
                String currentServiceIpNode = servicePath + "/" + localIp + "|" + serverPort;
                exist = zkClient.exists(currentServiceIpNode);
                if (!exist){
                    zkClient.createEphemeral(currentServiceIpNode);
                }

                // 监听注册服务的变化,同时更新数据到本地缓存
                zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
                    @Override
                    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                        if (currentChilds == null){
                            currentChilds = Lists.newArrayList();
                        }
                        List<String> activityServiceIpList = Lists.newArrayList();
                        for (String currentChild: currentChilds){
                            activityServiceIpList.add(currentChild.split("|")[0]);
                        }
                        refreshActivityService(activityServiceIpList);
                    }
                });
            }

        }

    }

    /**
     * description: 利用ZK自动刷新当前存活的服务提供者列表数据
     * author: Administrator
     * date: 2019/3/18 14:10
     *
     * @param:
     * @return:
     */
    private void refreshActivityService(List<String> serviceIpList){
        refreshMap(serviceIpList, providerServiceMap);
        logger.debug("currentServiceMetaDataMap: " + JSON.toJSONString(providerServiceMap));
    }

    private void refreshServiceMetaDataMap(List<String> serviceIpList){
        refreshMap(serviceIpList, serviceMetaDataMap4Consume);
    }
    private void refreshMap(List<String> serviceIpList, Map<String, List<ProviderService>> metaDataMap){
        if (serviceIpList == null){
            serviceIpList = Lists.newArrayList();
        }
        Map<String, List<ProviderService>> currentServiceMetaDataMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, List<ProviderService>> entry: metaDataMap.entrySet()){
            String key = entry.getKey();
            List<ProviderService> providerServices = entry.getValue();

            List<ProviderService> serviceMetaDataModelList = currentServiceMetaDataMap.get(key);
            if (serviceMetaDataModelList == null){
                serviceMetaDataModelList = Lists.newArrayList();
            }
            for (ProviderService serviceMetaData: providerServices){
                if (serviceIpList.contains(serviceMetaData.getServerIp())){
                    serviceMetaDataModelList.add(serviceMetaData);
                }
            }
            currentServiceMetaDataMap.put(key, serviceMetaDataModelList);
        }
        metaDataMap.clear();
        metaDataMap.putAll(currentServiceMetaDataMap);
    }

    /**
     * description: 获取或更新注册中心的服务提供者提供的服务信息
     * author: Administrator
     * date: 2019/3/18 15:01
     *
     * @param:
     * @return:
     */
    private Map<String, List<ProviderService>> fetchOrUpdateServiceMetaData(String appKey){
        final Map<String, List<ProviderService>> providerServiceMap = new ConcurrentHashMap<>();
        // 连接ZK
        synchronized (RegisterCenter.class){
            if (zkClient != null){
                zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }
        }
        // 从ZK获取服务提供者列表
        String providerPath = ROOT_PATH + "/" + appKey;
        List<String> providerServices = zkClient.getChildren(providerPath);
        for (String serviceName: providerServices){
            String servicePath = providerPath + "/" + serviceName + "/" + PROVIDER_TYPE;
            List<String> ipPathList = zkClient.getChildren(servicePath);
            for (String ipPath: ipPathList){
                String serverIp = StringUtils.split(ipPath, "|")[0];
                String serverPort = StringUtils.split(ipPath, "|")[1];

                List<ProviderService> providerServiceList = providerServiceMap.get(serviceName);
                if (providerServiceList == null){
                    providerServiceList = Lists.newArrayList();
                }
                ProviderService providerService = new ProviderService();
                try {
                    providerService.setServiceItf(ClassUtils.getClass(serviceName));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                providerService.setAppKey(appKey);
                providerService.setServerIp(serverIp);
                providerService.setServerPort(Integer.parseInt(serverPort));
                providerServiceList.add(providerService);
                providerServiceMap.put(serviceName, providerServiceList);
            }

            zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    if (currentChilds == null){
                        currentChilds = Lists.newArrayList();
                    }
                    List<String> activityServiceIpList = Lists.newArrayList();
                    for (String currentChild: currentChilds){
                        activityServiceIpList.add(currentChild.split("|")[0]);
                    }
                    refreshServiceMetaDataMap(activityServiceIpList);
                }
            });
        }
        return providerServiceMap;
    }

    @Override
    public Map<String, List<ProviderService>> getProviderServiceMap() {
        return providerServiceMap;
    }
}
