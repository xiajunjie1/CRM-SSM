package com.maker.crm.service.tran.impl;

import com.maker.crm.dao.tran.TransactionRemarkMapper;
import com.maker.crm.model.TransactionRemark;
import com.maker.crm.service.tran.TransactionRemarkService;
import com.maker.crm.service.tran.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TransactionRemarkServiceImpl implements TransactionRemarkService {
   @Autowired
    private TransactionRemarkMapper transactionRemarkMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionRemarkServiceImpl.class);
   @Override
    public List<TransactionRemark> queryTransactionRemarkByTranId(String tranId) throws Exception {
       if(!StringUtils.hasLength(tranId)){
           LOGGER.error("查询交易备注传入交易ID为空");
           return null;
       }
       return transactionRemarkMapper.selectTransactionRemarkByTranId(tranId);
    }
}
