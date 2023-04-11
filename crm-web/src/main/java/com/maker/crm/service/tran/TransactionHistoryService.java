package com.maker.crm.service.tran;

import com.maker.crm.model.TransactionHistory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TransactionHistoryService {

    public List<TransactionHistory> queryTransactionHistoryByTranId(String tranId)throws Exception;
}
