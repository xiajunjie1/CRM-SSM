package com.maker.crm.service.activity;

import com.maker.crm.model.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    public boolean addActivity(Activity activity)throws Exception;
    /**
     * 分页查询业务，map中封装这分页相关的参数以及条件参数
     * */
    public List<Activity> queryActivitySplit(Map<String,Object> map);
    /**
     * 查询总记录数，map中封装这查询条件，可以传null
     * */
    public long queryTotalCount(Map<String,Object> map);

    public int removeActivityByIds(String[] id)throws Exception;

    public Activity queryById(String id);
    /**
     * 一般更新数据，且更新的数据在一个对象之中，都是传入对象，
     * 如果是作为条件进行查询，则一般将传入的参数封装为map
     *
     * */
    public int editActivityById(Activity activity)throws Exception;

    public Map<String,Object> exportAllActivity()throws Exception;

    public Map<String,Object> exportSelectedActivity(String[] ids)throws Exception;

    public int importActivity(List<Activity> activityList)throws Exception;

    public Activity queryActivityById(String id)throws Exception;

    public List<Activity> queryActivityByCid(String cid)throws Exception;

    public List<Activity> queryActivityByNameCid(Map<String,String> map)throws Exception;

    public List<Activity> queryActivityByIds(String[] ids)throws Exception;

    public List<Activity> queryActivityByNameInCid(Map<String,String> map)throws Exception;

    public List<Activity> queryActivityByName(String name)throws Exception;




}
