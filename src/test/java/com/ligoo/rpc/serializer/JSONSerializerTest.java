package com.ligoo.rpc.serializer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:07:27
 * @Description:
 */
public class JSONSerializerTest {
    private JSONSerializer jsonSerializer = new JSONSerializer();
    private byte[] data;
    @Test
    public void serialize() {
        User user = new User("pangyazhou", "male", 30);
        data = jsonSerializer.serialize(user);
        System.out.println(new String(data));
    }

    @Test
    public void deserialize() {
        serialize();
        User user = jsonSerializer.deserialize(data, User.class);
        System.out.println(user);
    }
}