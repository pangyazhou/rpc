package com.ligoo.rpc.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 14:34:54
 * @Description: 默认的java对象实例化方法
 */
public class DefaultJavaSerializer implements ISerializer {
    /**
     * description: 序列化
     * author: Administrator
     * date: 2019/3/8 14:35
     *
     * @param:
     * @return:
     */
    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * description: 反序列化
     * author: Administrator
     * date: 2019/3/8 14:35
     *
     * @param:
     * @return:
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
