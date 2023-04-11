package com.maker.crm.service.tran;

import com.maker.crm.model.TransactionRemark;

import java.util.List;

public interface TransactionRemarkService {
    public List<TransactionRemark> queryTransactionRemarkByTranId(String tranId)throws Exception;

}
