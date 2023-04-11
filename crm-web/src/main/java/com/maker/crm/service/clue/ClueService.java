package com.maker.crm.service.clue;

import com.maker.crm.model.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    public int addClue(Clue clue)throws Exception;
    public List<Clue> queryClueSplit(Map<String,Object> map)throws Exception;
    public long queryClueCountByCondition(Map<String,Object> map)throws Exception;

    public Clue queryClueById(String id)throws Exception;

    /**
     * 实现线索的转换功能
     *  1.将线索中的信息提取出来，存储到客户表中
     *  2.将线索中的信息提取出来，存储到联系人表中
     *  3.根据出入的参数是否需要创建交易，若要创建交易，将相关的信息存储到交易中
     *  4.根据ClueId查询所有的线索备注
     *  5.把所有线索下的备注转换到客户备注表中一份
     *  6.把所有线索下的备注转换到联系人备注表中一份
     *  7.根据ClueId查询线索市场活动关联表
     *  8.把线索和市场活动的关系转换到联系人和市场活动关联关系
     *  9.如果需要创建交易，则往交易表中添加一条记录
     *  10.如需创建交易，则还需把线索表中所有的备注转换到交易备注表中
     *  11.删除该线索下所有的备注
     *  12.删除该线索和市场活动的关系
     *  13.删除该线索
     *
     * */
    public void saveClueConvert(Map<String,Object> map)throws Exception;
}
