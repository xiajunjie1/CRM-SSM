package com.maker.crm.service.customer;

import com.maker.crm.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<Customer> queryCustomerSplitByCondition(Map<String,Object> map)throws Exception;

     int queryCustomerCount()throws Exception;

     List<Customer> queryCustomerByName(String customerName)throws Exception;
}
