package com.maker.crm.service.clue;

import com.maker.crm.model.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    public int addClueRemark(ClueRemark clueRemark)throws Exception;
    public List<ClueRemark> queryClueRemarksByCid(String cid)throws Exception;
    public int removeClueRemarkById(String id)throws Exception;
}
