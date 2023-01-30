package com.maker.crm.action.workbench;

import com.maker.crm.dao.user.UserMapper;
import com.maker.crm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/activity/*")
public class ActivitiesAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ActivitiesAction.class);
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("index")
    public ModelAndView index(){
        List<User> ulist=userMapper.selectAllUsers();
        ModelAndView mav=new ModelAndView("/workbench/activity/index");
        mav.addObject("ulist",ulist);
        return mav;
    }
}
