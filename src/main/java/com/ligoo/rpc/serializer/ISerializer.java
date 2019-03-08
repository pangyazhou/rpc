package com.ligoo.rpc.serializer;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 14:28:33
 * @Description: 自定义序列化实现接口
 */
public interface ISerializer {

    /**
     * description: 序列化
     * author: Administrator
     * date: 2019/3/8 14:29
     *
     * @param: 
     * @return: 
     */
    public <T> byte[] serialize(T obj);

    /**
     * description: 反序列化
     * author: Administrator
     * date: 2019/3/8 14:29
     *
     * @param:
     * @return:
     */
    public <T> T deserialize(byte[] bytes, Class<T> clazz);
}
