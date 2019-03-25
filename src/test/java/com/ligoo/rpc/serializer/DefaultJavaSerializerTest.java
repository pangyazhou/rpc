package com.ligoo.rpc.serializer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 14:43:16
 * @Description:
 */
public class DefaultJavaSerializerTest {

    private DefaultJavaSerializer defaultJavaSerializer = new DefaultJavaSerializer();
    private byte[] data;

    @Test
    public void serialize() {
        User user = new User("pangyazhou", "male", 30);
        data = defaultJavaSerializer.serialize(user);
        System.out.println(new String(data));
    }

    @Test
    public void deserialize() {
        serialize();
        User user = defaultJavaSerializer.deserialize(data, User.class);
        System.out.println(user);
    }
}