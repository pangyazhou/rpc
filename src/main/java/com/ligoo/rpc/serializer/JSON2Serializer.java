package com.ligoo.rpc.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:11:57
 * @Description:fastjson序列化实现
 */
public class JSON2Serializer implements ISerializer{
    /**
     * description: 序列化
     * author: Administrator
     * date: 2019/3/8 15:12
     *
     * @param:
     * @return:
     */
    @Override
    public <T> byte[] serialize(T obj) {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat).getBytes();
    }


    /**
     * description: 反序列化
     * author: Administrator
     * date: 2019/3/8 15:12
     *
     * @param:
     * @return:
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)JSONObject.parseObject(data, clazz);
    }
}
