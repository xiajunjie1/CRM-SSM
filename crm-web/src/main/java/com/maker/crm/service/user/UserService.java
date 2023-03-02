package com.maker.crm.service.user;

import com.maker.crm.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User queryUserByLoginActAndPwd(Map<String,Object> map);

    public boolean addUser(User user)throws Exception;
    public List<User> queryAllUsers();

}
