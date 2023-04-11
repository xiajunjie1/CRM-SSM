package com.maker.crm.service.activity.impl;

import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.dao.activity.ActivityMapper;
import com.maker.crm.model.Activity;
import com.maker.crm.service.abs.AbstractService;
import com.maker.crm.service.activity.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl extends AbstractService implements ActivityService {
    private static final Logger LOGGER= LoggerFactory.getLogger(ActivityServiceImpl.class);
    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public boolean addActivity(Activity activity)throws Exception {
        int result=activityMapper.insertNewActivity(activity);
        return result>0;
    }

    @Override
    public List<Activity> queryActivitySplit(Map<String, Object> map) {
        return activityMapper.selectActivitySplit(map);
    }

    @Override
    public long queryTotalCount(Map<String,Object> map) {
        return activityMapper.selectActivityCountByCondition(map);
    }

    @Override
    public int removeActivityByIds(String[] id) throws Exception {
        return activityMapper.deleteActivityByIds(id);
    }

    @Override
    public Activity queryById(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int editActivityById(Activity activity) throws Exception {
        if(activity==null){
            LOGGER.error("传入参数对象为空");
            return 0;
        }
        if(isNull(activity.getId())){
            LOGGER.error("传入对象id为空");
            return 0;
        }
        if(isNull(activity.getOwner())){
                LOGGER.error("传入对象所有者为空");
                return 0;
        }
        if(isNull(activity.getEditBy())){
            LOGGER.error("传入对象的编辑者信息为空");
            return 0;
        }
        if(isNull(activity.getEditTime())){
            LOGGER.error("传入对象的修改时间为空");
            return 0;
        }

        return activityMapper.updateActivityById(activity);
    }

    @Override
    public Map<String, Object> exportAllActivity() throws Exception {
        Map<String,Object> result=new HashMap<>();
        boolean flag=false;
        List<Activity> activityList=activityMapper.selectAllActivity();
        //使用poi组件进行Excel对象的生成
        Workbook wb=new HSSFWorkbook();
        Sheet sheet=wb.createSheet("市场活动信息");
        Row row=sheet.createRow(0);//第一行，表头信息
        Cell cell=row.createCell(0);//第一列
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建人");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改人");
        Activity activity=null;
        if(activityList!=null && activityList.size()>0){
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                row=sheet.createRow(i+1);
                cell=row.createCell(0);//第一列
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());


            }
        }
        String fileName="activityList"+new Date().getTime()+".xlsx";
        FileOutputStream fileOutputStream=new FileOutputStream("D:"+ File.separator+"exportExcel"+File.separator+fileName);
        wb.write(fileOutputStream);
        flag=true;
        result.put("flag",flag);
        result.put("fileName",fileName);
        fileOutputStream.close();
        wb.close();
        LOGGER.info("已完成数据导出！");
        return result;
    }

    @Override
    public Map<String, Object> exportSelectedActivity(String[] ids) throws Exception {
        Map<String,Object> result=new HashMap<>();
        boolean flag=false;
        List<Activity> activityList=activityMapper.selectActivityByIds(ids);
        //使用poi组件进行Excel对象的生成
        Workbook wb=new HSSFWorkbook();
        Sheet sheet=wb.createSheet("市场活动信息");
        Row row=sheet.createRow(0);//第一行，表头信息
        Cell cell=row.createCell(0);//第一列
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建人");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改人");
        Activity activity=null;
        if(activityList!=null && activityList.size()>0){
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                row=sheet.createRow(i+1);
                cell=row.createCell(0);//第一列
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());


            }
        }
        String fileName="activityList"+new Date().getTime()+".xlsx";
        FileOutputStream fileOutputStream=new FileOutputStream("D:"+ File.separator+"exportExcel"+File.separator+fileName);
        wb.write(fileOutputStream);
        flag=true;
        result.put("flag",flag);
        result.put("fileName",fileName);
        fileOutputStream.close();
        wb.close();
        LOGGER.info("已完成数据导出！");
        return result;

    }

    @Override
    public int importActivity(List<Activity> activityList) throws Exception {
        if(activityList!=null&&activityList.size()>0) {
            return activityMapper.insertImportActivity(activityList);
        }
        return 0;
        }

    @Override
    public Activity queryActivityById(String id) throws Exception {
        if(!StringUtils.hasLength(id)){
            return null;
        }
        return activityMapper.selectActivityById(id);
    }

    @Override
    public List<Activity> queryActivityByCid(String cid) throws Exception {
        if(!StringUtils.hasLength(cid)){
            LOGGER.error("根据线索id查询市场活动，传入的线索id为空");
            return null;
        }

        return activityMapper.selectActivityByClueId(cid);
    }

    @Override
    public List<Activity> queryActivityByNameCid(Map<String, String> map) throws Exception {

        return activityMapper.selectActivityByNameCid(map);
    }

    @Override
    public List<Activity> queryActivityByIds(String[] ids) throws Exception {
       if(ids==null || ids.length==0){
           LOGGER.error("根据id查询市场活动传入id为空");
           return null;
       }
        return activityMapper.selectActivityByIds(ids);
    }

    @Override
    public List<Activity> queryActivityByNameInCid(Map<String, String> map) throws Exception {

        return activityMapper.selectActivityByNameInCid(map);
    }

    @Override
    public List<Activity> queryActivityByName(String name) throws Exception {

        return activityMapper.selectActivityByName(name);
    }
}
