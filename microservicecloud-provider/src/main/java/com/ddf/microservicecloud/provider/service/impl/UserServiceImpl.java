package com.ddf.microservicecloud.provider.service.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import com.ddf.microservicecloud.provider.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
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

    @Transactional
    @Override
    public User updateOne(User user) {
        return userDao.updateOne(user);
    }


    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }


}
