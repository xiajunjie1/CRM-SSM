package com.maker.test;

import com.maker.crm.context.config.ApplicationConfig;
import com.maker.crm.model.User;
import com.maker.crm.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@ContextConfiguration(classes = {ApplicationConfig.class})
@ExtendWith(SpringExtension.class)
public class TransactionTest {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionTest.class);
    @Test
    public void transactionTest(){
        User user=new User();
        String id=UUID.randomUUID().toString().replaceAll("-","");
        user.setId(id);
        LOGGER.info("uuid值：{}、长度：{}",id,id.length());

        user.setLoginAct("zhangsan");
        user.setLoginPwd(DigestUtils.md5DigestAsHex("xia123".getBytes(StandardCharsets.UTF_8)));
        userService.addUser(user);
    }
}
