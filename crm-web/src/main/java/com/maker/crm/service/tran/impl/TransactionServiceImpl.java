package com.maker.crm.service.tran.impl;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.dao.customer.CustomerMapper;
import com.maker.crm.dao.tran.TransactionHistoryMapper;
import com.maker.crm.dao.tran.TransactionMapper;
import com.maker.crm.model.*;
import com.maker.crm.service.tran.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;
    @Override
    public List<Transaction> queryTransactionSplitByCondition(Map<String, Object> map) throws Exception {
        if(map==null || map.isEmpty()){
            LOGGER.error("根据条件分页查询交易传入的参数为空");
            return null;
        }
        if(map.get("beginNo")==null){
            LOGGER.error("分页查询交易传入开始条目出现异常");
            map.put("beginNo",0);
        }
        if (map.get("pageSize")==null){
            LOGGER.error("分页查询交易传入每页条目出现异常");
            map.put("pageSize",2);
        }
        return transactionMapper.selectTransactionSplitByCondition(map) ;
    }

    @Override
    public int queryCountByCondition(Map<String, Object> map) throws Exception {
        return transactionMapper.selectTransactionCountByCondition(map);
    }

    @Override
    public void addTransaction(Map<String,Object> map) throws Exception {
        Transaction transaction=(Transaction) map.get("tran");
        String cusName=map.get("cusName").toString();
        User user=(User) map.get(Constant.SESSION_USER);

        if(transaction==null){
            throw new RuntimeException("添加交易传入参数为空");
        }
        if(cusName==null || "".equals(cusName)){
            throw new RuntimeException("添加交易客户名称为空");
        }
        Customer customer=customerMapper.selectOneCustomerByName(cusName);
        if(customer==null){
            //该客户名不存在，添加客户
            customer=new Customer();
            customer.setId(UUIDUtils.getUUID());
            customer.setName(cusName);
            customer.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
            customer.setCreateBy(user.getId());
            customer.setOwner(user.getId());
            customerMapper.insertCustomer(customer);
        }
        transaction.setId(UUIDUtils.getUUID());
        transaction.setCustomerId(customer.getId());
        transaction.setOwner(user.getId());
        transaction.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
        transaction.setCreateBy(user.getId());
        transactionMapper.insertTransaction(transaction);
        //向交易历史表中添加数据
        TransactionHistory transactionHistory=new TransactionHistory();
        transactionHistory.setId(UUIDUtils.getUUID());
        transactionHistory.setCreateBy(user.getId());
        transactionHistory.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
        transactionHistory.setStage(transaction.getStage());
        transactionHistory.setMoney(transaction.getMoney());
        transactionHistory.setExpectedDate(transaction.getExpectedDate());
        transactionHistory.setTranId(transaction.getId());
        transactionHistoryMapper.insert(transactionHistory);
    }

    @Override
    public Transaction queryTransactionDetailById(String id) throws Exception {
        if(!StringUtils.hasLength(id)){
            LOGGER.error("查询交易细节，传入id为空");
            return null;
        }
        return transactionMapper.selectTransactionDetailById(id);
    }

    @Override
    public void editTransactionStageByTranId(TransactionHistory transactionHistory) throws Exception {
        if(transactionHistory==null){
            throw new RuntimeException("更改交易状态，传入参数为空");
        }
        if(!StringUtils.hasLength(transactionHistory.getStage())){
            throw new RuntimeException("更改交易状态，传入的状态为空");
        }
        if(!StringUtils.hasLength(transactionHistory.getTranId())){
            throw new RuntimeException("更改交易状态，传入交易Id为空");
        }
        transactionMapper.updateTransactionStageByTranId(transactionHistory.getTranId(), transactionHistory.getStage());
        transactionHistoryMapper.insert(transactionHistory);
    }

    @Override
    public List<FunnelVo> queryTransactionFunnelVo() throws Exception {
        return transactionMapper.selectTransactionFunnelVo();
    }


}
