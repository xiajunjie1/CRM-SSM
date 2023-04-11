package com.maker.crm.service.clue.impl;

import com.maker.crm.dao.clue.ClueActivityRelationMapper;
import com.maker.crm.model.ClueActivityRelation;
import com.maker.crm.service.clue.ClueActivityRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
   private static final Logger LOGGER= LoggerFactory.getLogger(ClueActivityRelationServiceImpl.class);
   @Autowired
   private ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public int addClueActivityRelationBatch(List<ClueActivityRelation> clueActivityRelations) throws Exception {
       if(clueActivityRelations==null || clueActivityRelations.isEmpty()){
           LOGGER.error("需要关联的线索市场活动关系为空");
           return 0;
       }
        return clueActivityRelationMapper.insertClueActivityRelationBatch(clueActivityRelations);
    }

    @Override
    public int removeClueActivityRelation(ClueActivityRelation clueActivityRelation) throws Exception {
        if(clueActivityRelation==null){
            LOGGER.error("解除关联关系传入线索市场活动关系为空");
            return 0;
        }
        if(!StringUtils.hasLength(clueActivityRelation.getActivityId())||!StringUtils.hasLength(clueActivityRelation.getClueId())){
            LOGGER.error("解除关联关系传入线索id或者市场活动id为空");
            return 0;
        }
        return clueActivityRelationMapper.deleteClueActivityRelationByAidCid(clueActivityRelation);
    }
}
