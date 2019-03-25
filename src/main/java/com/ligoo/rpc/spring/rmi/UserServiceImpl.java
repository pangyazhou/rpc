package com.ligoo.rpc.spring.rmi;

import com.ligoo.rpc.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:54:58
 * @Description: 服务接口实现
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Map<String, User> userMap = new HashMap<>();

    static {
        userMap.put("pangyazhou", new User("pangyazhou", "male", 30));
        userMap.put("wangqiao", new User("wangqiao", "female", 27));
    }
    @Override
    public User findByName(String name) {
        return userMap.get(name);
    }
}
