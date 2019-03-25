package com.ligoo.rpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:01:59
 * @Description: jackson序列化实现
 */
public class JSONSerializer implements ISerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * description: 序列化
     * author: Administrator
     * date: 2019/3/8 15:02
     *
     * @param:
     * @return:
     */
    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null){
            return new byte[0];
        }
        try{
            String json = objectMapper.writeValueAsString(obj);
            return json.getBytes();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * description: 反序列化
     * author: Administrator
     * date: 2019/3/8 15:02
     *
     * @param:
     * @return:
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String json = new String(data);
        try {
            return (T)objectMapper.readValue(json, clazz);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
