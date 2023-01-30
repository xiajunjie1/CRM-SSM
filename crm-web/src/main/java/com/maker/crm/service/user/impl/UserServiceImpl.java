package com.maker.crm.service.user.impl;

import com.maker.crm.dao.user.UserMapper;
import com.maker.crm.model.User;
import com.maker.crm.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User queryUserByLoginActAndPwd(Map<String,Object> map){
        User user=userMapper.selectByLoginActAndPwd(map);
        return user;
    }

    @Override
    public boolean addUser(User user) {
       // userMapper.insert(user);


        return false;


    }

    @Override
    public List<User> queryAllUsers() {
        List<User> ulist=null;
        ulist=userMapper.selectAllUsers();
        if(ulist==null){
            ulist=new ArrayList<>();
        }
        return ulist;
    }


}
