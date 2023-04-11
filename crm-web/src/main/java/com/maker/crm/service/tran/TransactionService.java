package com.maker.crm.service.tran;

import com.maker.crm.model.FunnelVo;
import com.maker.crm.model.Transaction;
import com.maker.crm.model.TransactionHistory;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    public List<Transaction> queryTransactionSplitByCondition(Map<String,Object> map)throws Exception;
    public int queryCountByCondition(Map<String,Object> map)throws Exception;

    public void addTransaction(Map<String,Object> map)throws Exception;

    public Transaction queryTransactionDetailById(String id)throws Exception;

    public void editTransactionStageByTranId(TransactionHistory transactionHistory)throws Exception;

    public List<FunnelVo> queryTransactionFunnelVo()throws Exception;

}
