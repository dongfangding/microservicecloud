package com.ddf.microservicecloud.service;

import com.ddf.microservicecloud.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */

public interface UserService {

    List<User> queryAll();

    User queryOne(Integer id);

    List<Map<String, Object>> queryAllMap();
}
