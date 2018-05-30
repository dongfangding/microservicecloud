package com.ddf.microservicecloud.api.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author DDf on 2018/5/14
 */
public class User implements RowMapper {
    private Integer id;
    private String userName;
    private String password;
    private String gender;
    private String tel;
    private String dbSource;
    private Integer removed;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public User() {
    }

    public User(Integer id, String userName, String password, String gender, String tel) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.dbSource = "dbSource-01";
        this.removed = 0;
    }

    public User(Integer id, String userName, String password, String gender, String tel, String dbSource, Integer removed) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.tel = tel;
        this.dbSource = dbSource;
        this.removed = removed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public Integer getRemoved() {
        return removed;
    }

    public void setRemoved(Integer removed) {
        this.removed = removed;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", dbSource='" + dbSource + '\'' +
                ", removed=" + removed +
                '}';
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        user.setGender(rs.getString("gender"));
        user.setTel(rs.getString("tel"));
        user.setDbSource(rs.getString("dbSource"));
        user.setRemoved(rs.getInt("removed"));
        return user;
    }
}
