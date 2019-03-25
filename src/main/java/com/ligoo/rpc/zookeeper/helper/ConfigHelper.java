package com.ligoo.rpc.zookeeper.helper;


import com.ligoo.rpc.zookeeper.util.ConfigConstant;
import com.ligoo.rpc.zookeeper.util.PropsUtil;

import java.util.Properties;

/**
 * @Author: Administrator
 * @Date: 2018/12/13 10:39:53
 * @Description:属性文件帮助类
 */
public class ConfigHelper {
    // 配置文件属性类
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * description: 从配置文件中读取zk服务地址列表
     * author: Administrator
     * date: 2019/3/14 10:26
     *
     * @param:
     * @return:
     */
    public static String getZkService(){
        return getString(ConfigConstant.ZK_SERVICE);
    }


    /**
     * description: 获取session超时时间
     * author: Administrator
     * date: 2019/3/14 10:32
     *
     * @param:
     * @return:
     */

    public static int getSessionTimeout(){
        return getInt(ConfigConstant.ZK_SESSION_TIME_OUT);
    }

    /**
     * description: 获取连接超时时间
     * author: Administrator
     * date: 2019/3/14 10:32
     *
     * @param:
     * @return:
     */
    public static int getConnectionTimeout(){
        return getInt(ConfigConstant.ZK_CONNECTION_TIME_OUT);
    }

    /**
     * description: 获取应用名称
     * author: Administrator
     * date: 2019/3/14 10:33
     *
     * @param:
     * @return:
     */
    public static String getAppName(){
        return getString(ConfigConstant.APP_NAME);
    }

    /**
     * description: 根据属性名获取String类型值
     * author: Administrator
     * date: 2018/12/24 16:27
     *
     * @param:
     * @return:
     */
    public static String getString(String key){
        return PropsUtil.getString(CONFIG_PROPS, key);
    }

    /**
     * description: 根据属性名获取Int类型值
     * author: Administrator
     * date: 2018/12/24 16:27
     *
     * @param:
     * @return:
     */
    public static int getInt(String key){
        return PropsUtil.getInt(CONFIG_PROPS, key);
    }

    /**
     * description: 根据属性名获取Boolean类型值
     * author: Administrator
     * date: 2018/12/24 16:27
     *
     * @param:
     * @return:
     */
    public static boolean getBoolean(String key){
        return PropsUtil.getBoolean(CONFIG_PROPS, key);
    }

}
