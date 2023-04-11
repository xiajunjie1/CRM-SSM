package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.model.Customer;
import com.maker.crm.model.User;
import com.maker.crm.service.customer.CustomerService;
import com.maker.crm.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/*")
public class CustomerAction {
   private static final Logger LOGGER= LoggerFactory.getLogger(CustomerAction.class);
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mav=new ModelAndView("/workbench/customer/index");
        List<User> userList=userService.queryAllUsers();
        mav.addObject("userList",userList);
        return mav;
    }
    @RequestMapping("queryCustomerSplit")
    @ResponseBody
    public Object queryCustomerSplit(@RequestBody Map<String,Object> map){
        Map<String,Object> result=new HashMap<>();
        try {
            int pageNo=Integer.parseInt(map.get("pageNo").toString());
            int pageSize=Integer.parseInt(map.get("pageSize").toString());
            int beginNo=(pageNo-1)*pageSize;
            map.put("beginNo",beginNo);
           List<Customer> customerList= customerService.queryCustomerSplitByCondition(map);
           result.put("customerList",customerList);
           result.put("code", Constant.RETURN_OBJECT_CODE_SUCCESS);
           int count= customerService.queryCustomerCount();
           result.put("count",count);
        }catch (Exception e){
            LOGGER.error("查询顾客信息出现异常：{}",e.getMessage());
            result.put("code",Constant.RETURN_OBJECT_CODE_FAIL);
            result.put("message","服务器忙，请稍后重试...");

        }
        return result;
    }
    @RequestMapping("queryAllCustomerByName")
    @ResponseBody
    public Object queryAllCustomerByName(String customerName){
        try{
            List<Customer> customerList=customerService.queryCustomerByName(customerName);
            return  customerList;
        }catch (Exception e){
            LOGGER.error("根据名称查询客户出现异常：{}",e.getMessage());
            return null;
        }
    }
}
