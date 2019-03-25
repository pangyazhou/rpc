package com.ligoo.rpc.spring.factorybean;

import com.ligoo.rpc.model.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:37:31
 * @Description:
 */
public class UserFactoryBean implements FactoryBean<User> {
    private static final User user = new User();
    private String username;
    private String sex;
    private int age;

    @Override
    public User getObject() throws Exception {
        user.setUsername(username);
        user.setSex(sex);
        user.setAge(age);
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
