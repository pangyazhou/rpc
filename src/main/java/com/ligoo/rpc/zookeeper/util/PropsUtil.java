package com.ligoo.rpc.zookeeper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @Author: Administrator
 * @Date: 2018/12/12 15:24:10
 * @Description: 读取属性文件工具类
 */
public class PropsUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * description: 加载属性文件
     * author: Administrator
     * date: 2018/12/12 15:50
     *
     * @param: fileName 属性文件名
     * @return: 
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;
        try{
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if(is == null){
            throw new FileNotFoundException(fileName + " is not found.");
        }
        props = new Properties();
        props.load(is);
        }catch (Exception e){
            LOGGER.error("load properties file failure", e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close inputstream failure.", e);
                }
            }
        }
        return props;
    }

    /**
     * description: 获取字符型属性, 默认值为空
     * author: Administrator
     * date: 2018/12/12 15:50
     *
     * @param: 
     * @return: 
     */
    public static String getString(Properties props, String key){
        return getString(props, key, "");
    }

    /**
     * description: 获取字符型属性, 可指定默认值
     * author: Administrator
     * date: 2018/12/12 15:51
     *
     * @param:
     * @return:
     */
    public static String getString(Properties props, String key, String defaultValue){
        String value = defaultValue;
        if(props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * description: 获取整型属性, 默认值为0
     * author: Administrator
     * date: 2018/12/12 15:51
     *
     * @param:
     * @return:
     */
    public static int getInt(Properties props, String key){
        return getInt(props, key, 0);
    }

    /**
     * description: 获取整型属性, 可指定默认值
     * author: Administrator
     * date: 2018/12/12 15:51
     *
     * @param:
     * @return:
     */
    public static int getInt(Properties props, String key, int defaultValue){
        int value = defaultValue;
        if(props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }
    /**
     * description: 获取布尔属性, 默认值为false
     * author: Administrator
     * date: 2018/12/12 15:51
     *
     * @param:
     * @return:
     */

    public static boolean getBoolean(Properties props, String key){
        return getBoolean(props, key, false);
    }

    /**
     * description: 获取布尔属性, 可指定默认值
     * author: Administrator
     * date: 2018/12/12 15:51
     *
     * @param:
     * @return:
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue){
        boolean value = defaultValue;
        if(props.containsKey(key)){
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
    public static void main(String[] args){
        Properties properties = PropsUtil.loadProps("smart.properties");
        String url = properties.getProperty("jdbc.url");
        System.out.println(url);
    }
}
