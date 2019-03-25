package com.ligoo.rpc.serializer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:15:34
 * @Description:
 */
public class JSON2SerializerTest {
    private JSON2Serializer json2Serializer = new JSON2Serializer();
    private byte[] data;

    @Test
    public void serialize() {
        User user = new User("pangyazhou", "male", 30);
        data = json2Serializer.serialize(user);
        System.out.println(new String(data));
    }

    @Test
    public void deserialize() {
        serialize();
        User user = json2Serializer.deserialize(data, User.class);
        System.out.println(user);
    }
}