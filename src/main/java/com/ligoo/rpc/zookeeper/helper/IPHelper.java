package com.ligoo.rpc.zookeeper.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: Administrator
 * @Date: 2019/3/18 10:28:21
 * @Description:
 */
public class IPHelper {
    private static final Logger logger = LoggerFactory.getLogger(IPHelper.class);
    private static String hostIp = StringUtils.EMPTY;

    /**
     * description: 获取本机IP
     * author: Administrator
     * date: 2019/3/18 10:30
     *
     * @param:
     * @return:
     */
    public static String localIp(){
        return hostIp;
    }

    public static String getRealIp(){
        String localIp = null; // 本地ip,没有外网ip则返回它
        String netIp = null;    // 外网ip
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;     // 发现外网地址
            while (netInterfaces.hasMoreElements() && !finded){
                NetworkInterface network = netInterfaces.nextElement();
                Enumeration<InetAddress> address = network.getInetAddresses();
                while (address.hasMoreElements()){
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && !ip.getHostAddress().contains(":")){ // 外网ip
                        netIp = ip.getHostAddress();
                        finded = true;
                        break;
                    }else if(ip.isSiteLocalAddress()
                            &&  !ip.isLoopbackAddress()
                            &&  !ip.getHostAddress().contains(":")) {   // 内网ip
                        localIp = ip.getHostAddress();
                    }
                }
            }
            if (netIp != null && !"".equals(netIp)){
                return netIp;
            }else {
                return localIp;
            }

        }catch (SocketException e){
            logger.warn("获取本机Ip失败.异常信息: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static {
        String ip = null;
        Enumeration allNetInterfaces;
        try{
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> inetAddresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress add: inetAddresses){
                    InetAddress address = add.getAddress();
                    if (address != null && address instanceof Inet4Address){
                        if (StringUtils.equals(address.getHostAddress(), "127.0.0.1")){
                            continue;
                        }
                        ip = address.getHostAddress();
                        break;
                    }
                }
            }
        }catch (SocketException e){
            logger.warn("获取本地Ip失败.异常信息: " + e.getMessage());
            throw new RuntimeException(e);
        }
        hostIp = ip;
    }

    /**
     * description: 获取第一个有效ip
     * author: Administrator
     * date: 2019/3/18 10:31
     *
     * @param:
     * @return:
     */
    public static String getHostFirstIP(){
        return hostIp;
    }

    public static void main(String[] args){
        System.out.println(getRealIp());
        System.out.println(getHostFirstIP());
    }
}
