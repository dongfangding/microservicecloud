package com.ddf.microservicecloud.service.impl;

import com.ddf.microservicecloud.dao.UserDao;
import com.ddf.microservicecloud.entity.User;
import com.ddf.microservicecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }

    @Override
    public User queryOne(Integer id) {
        return userDao.getOne(id);
    }

    @Override
    public List<Map<String, Object>> queryAllMap() {
        return userDao.findAllMap();
    }
}
