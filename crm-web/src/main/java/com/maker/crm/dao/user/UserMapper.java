package com.maker.crm.dao.user;

import com.maker.crm.model.User;

import java.util.List;
import java.util.Map;

/**
 * 有mybatis逆向工程自动生成的接口
 * */
public interface UserMapper {

    int deleteByPrimaryKey(String id);


    int insert(User record);

    int insertSelective(User record);


    User selectByPrimaryKey(String id);


    int updateByPrimaryKeySelective(User record);


    int updateByPrimaryKey(User record);

    /**
     * 根据账号和密码查询用户
     * */
    public User selectByLoginActAndPwd(Map<String,Object> map);
    public List<User> selectAllUsers();
}