package com.ligoo.rpc.zookeeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @Author: Administrator
 * @Date: 2019/3/12 16:58:56
 * @Description: 服务注册中心的服务提供者的注册信息
 */
public class ProviderService implements Serializable {
    private Class<?> serviceItf;
    private transient Object serviceObject;
    @JsonIgnore
    private transient Method serviceMethod;
    private String serverIp;
    private int serverPort;
    private int timeout;
    // 服务提供者权重
    private int weight;
    // 服务端线程数
    private int workThreads;
    // 服务提供者唯一标识
    private String appKey;
    // 服务分组组名
    private String groupName;

    public ProviderService copy(){
        ProviderService providerService = new ProviderService();
        providerService.setServiceItf(serviceItf);
        providerService.setServiceObject(serviceObject);
        providerService.setServiceMethod(serviceMethod);
        providerService.setServerIp(serverIp);
        providerService.setServerPort(serverPort);
        providerService.setTimeout(timeout);
        providerService.setWeight(weight);
        providerService.setWorkThreads(workThreads);
        providerService.setAppKey(appKey);
        providerService.setGroupName(groupName);
        return providerService;
    }


    public Class<?> getServiceItf() {
        return serviceItf;
    }

    public void setServiceItf(Class<?> serviceItf) {
        this.serviceItf = serviceItf;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public Method getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(Method serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
