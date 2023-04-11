package com.maker.crm.service.customer.impl;

import com.maker.crm.dao.customer.CustomerMapper;
import com.maker.crm.model.Customer;
import com.maker.crm.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
private static final Logger LOGGER= LoggerFactory.getLogger(CustomerServiceImpl.class);
@Autowired
private CustomerMapper customerMapper;
    @Override
    public List<Customer> queryCustomerSplitByCondition(Map<String, Object> map) throws Exception {
        if(map==null || map.isEmpty()){
            LOGGER.error("查询客户信息传入参数为空");
            return null;
        }
        return customerMapper.selectCustomerSplitByCondition(map);
    }

    @Override
    public int queryCustomerCount() throws Exception {
        return customerMapper.selectCustomerCount();
    }

    @Override
    public List<Customer> queryCustomerByName(String customerName) throws Exception {

        return customerMapper.selectAllCustomerByName(customerName);
    }


}
