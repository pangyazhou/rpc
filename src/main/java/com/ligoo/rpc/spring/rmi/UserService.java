package com.ligoo.rpc.spring.rmi;

import com.ligoo.rpc.model.User;

/**
 * @Author: Administrator
 * @Date: 2019/3/8 15:54:04
 * @Description: 服务接口
 */
public interface UserService {
     User findByName(String name);
}
