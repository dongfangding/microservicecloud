package com.ddf.microservicecloud.provider.dao.impl;

import com.ddf.microservicecloud.api.entity.User;
import com.ddf.microservicecloud.provider.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0";
        return jdbcTemplate.query(sql, new User());
    }

    /**
     * 获得指定的用户
     * value用于指定cacheManager， key指定缓存的key，默认是参数
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User getOne(Integer id) {
        log.info("查询用户id为{}的数据。。。", id);
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 AND ID = " + id;
        return (User) jdbcTemplate.queryForObject(sql, new User());
    }

    @Override
    public List<Map<String, Object>> findAllMap() {
        String sql = "SELECT * FROM USER WHERE REMOVED = 0 ";
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = jdbcTemplate.queryForList(sql, mapList);
        return mapList;
    }

    /**
     * 更新cacheManager为user的指定key的缓存数据
     * @param user
     * @return
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public User updateOne(User user) {
        log.info("更新用户，传入用户信息为[{}]", user.toString());
        String sql = "UPDATE USER SET userName = ?, password = ?, gender = ?, tel = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getGender(),
                user.getTel(), user.getId());
        System.out.println(update);
        if (update < 1) {
            throw new RuntimeException("用户更新失败");
        }
        return getOne(user.getId());
    }

    /**
     * 删除cacheManager为user的指定key的缓存数据
     * @param id
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Integer id) {
        String sql = "UPDATE USER SET REMOVED = 1 WHERE ID = ?";
        int update = jdbcTemplate.update(sql, id);
        if (update < 1) {
            throw new RuntimeException("用户删除失败");
        }
    }
}
