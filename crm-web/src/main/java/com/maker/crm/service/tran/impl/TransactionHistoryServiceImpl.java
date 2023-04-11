package com.maker.crm.service.tran.impl;

import com.maker.crm.dao.tran.TransactionHistoryMapper;
import com.maker.crm.model.TransactionHistory;
import com.maker.crm.service.tran.TransactionHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);
    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;
    @Override
    public List<TransactionHistory> queryTransactionHistoryByTranId(String tranId) throws Exception {
        if(!StringUtils.hasLength(tranId)){
            LOGGER.error("查询交易历史传入的交易ID为空");
            return null;
        }
        return transactionHistoryMapper.selectTransactionHistoryByTranId(tranId);
    }
}
