package com.maker.crm.service.clue.impl;

import com.maker.crm.dao.clue.ClueRemarkMapper;
import com.maker.crm.model.ClueRemark;
import com.maker.crm.service.clue.ClueRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
   private static final Logger LOGGER= LoggerFactory.getLogger(ClueRemarkServiceImpl.class);
   @Autowired
   private ClueRemarkMapper clueRemarkMapper;
    @Override
    public int addClueRemark(ClueRemark clueRemark) throws Exception {
       if(clueRemark==null){
        LOGGER.error("添加线索备注传入传输为空");
        return 0;
       }

        return clueRemarkMapper.insertClueRemark(clueRemark);
    }

    @Override
    public List<ClueRemark> queryClueRemarksByCid(String cid) throws Exception {
       if(!StringUtils.hasLength(cid)){
           LOGGER.error("根据线索id查询备注，传入的线索id为空！");
           return null;
       }
        return clueRemarkMapper.selectRemarkByClueId(cid);
    }

    @Override
    public int removeClueRemarkById(String id) throws Exception {
        if(!StringUtils.hasLength(id)){
            LOGGER.error("根据id删除线索备注传入id为空");
            return 0;
        }
        return clueRemarkMapper.deleteByPrimaryKey(id);
    }
}
