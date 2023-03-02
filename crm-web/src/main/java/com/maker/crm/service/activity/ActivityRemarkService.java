package com.maker.crm.service.activity;

import com.maker.crm.model.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    public List<ActivityRemark> queryActivityRemarkByAid(String aid)throws Exception;
    public int addActivityRemark(ActivityRemark activityRemark)throws Exception;

    public int removeActivityRemarkById(String id)throws Exception;

    public int editActivityRemarkById(ActivityRemark activityRemark)throws Exception;
}
