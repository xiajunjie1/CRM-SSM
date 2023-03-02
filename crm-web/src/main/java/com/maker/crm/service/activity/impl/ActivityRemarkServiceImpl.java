package com.maker.crm.service.activity.impl;

import com.maker.crm.dao.activity.ActivityRemarkMapper;
import com.maker.crm.model.ActivityRemark;
import com.maker.crm.service.activity.ActivityRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(ActivityRemarkServiceImpl.class);
    @Override
    public List<ActivityRemark> queryActivityRemarkByAid(String aid) throws Exception {
        if(!StringUtils.hasLength(aid)){
            LOGGER.error("传入的市场活动id为空！");
            throw new Exception("市场活动id为空,无法查询到备注");
        }
        return activityRemarkMapper.selectActivityRemarkByActivityId(aid);
    }

    @Override
    public int addActivityRemark(ActivityRemark activityRemark) throws Exception {
        if(activityRemark==null){
            LOGGER.error("【新增市场活动备注出错】传入市场活动为空");
            return 0;
        }
        return activityRemarkMapper.insertActivityRemark(activityRemark);

    }

    @Override
    public int removeActivityRemarkById(String id) throws Exception {
       if(!StringUtils.hasLength(id)){
           LOGGER.error("传入的id为空");
           return 0;
       }
        return activityRemarkMapper.deleteByPrimaryKey(id);

    }

    @Override
    public int editActivityRemarkById(ActivityRemark activityRemark) throws Exception {
        if(!StringUtils.hasLength(activityRemark.getId())){
            LOGGER.error("传入备注对象id为空");
            return 0;
        }
        if(!StringUtils.hasLength(activityRemark.getNoteContent())){
            LOGGER.error("传入备注对象的备注内容为空");
            return 0;
        }

        return activityRemarkMapper.updateActivityRemarkById(activityRemark);
    }
}
