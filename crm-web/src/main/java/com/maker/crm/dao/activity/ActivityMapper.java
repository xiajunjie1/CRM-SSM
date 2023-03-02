package com.maker.crm.dao.activity;

import com.maker.crm.model.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Mon Jan 30 09:42:14 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Mon Jan 30 09:42:14 CST 2023
     */
    int insertNewActivity(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Mon Jan 30 09:42:14 CST 2023
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Mon Jan 30 09:42:14 CST 2023
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Mon Jan 30 09:42:14 CST 2023
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Mon Jan 30 09:42:14 CST 2023
     */
    int updateByPrimaryKey(Activity record);

    /**
     * 分页查询
     * */
    List<Activity> selectActivitySplit(Map<String,Object> map);

    long selectActivityCountByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids)throws Exception;

    int updateActivityById(Activity activity)throws Exception;

    List<Activity> selectAllActivity()throws Exception;

    List<Activity> selectActivityByIds(String[] ids)throws Exception;

    /**
     * 插入市场活动信息，主键存在即更新：insert into tbl_user (id, status) values (1, 1) on duplicate key update status = 1;
     * */
    int insertImportActivity(List<Activity> activityList)throws Exception;

    Activity selectActivityById(String id)throws Exception;
}