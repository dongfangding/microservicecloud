package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DDf on 2018/5/14
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER ";
        return jdbcTemplate.query(sql, new User());
    }

    @Override
    public User getOne(Integer id) {
        String sql = "SELECT * FROM USER WHERE ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }
}
