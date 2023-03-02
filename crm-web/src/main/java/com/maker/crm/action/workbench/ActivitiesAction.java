package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.POIUtils;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.dao.activity.ActivityMapper;
import com.maker.crm.dao.user.UserMapper;
import com.maker.crm.model.Activity;
import com.maker.crm.model.ActivityRemark;
import com.maker.crm.model.User;
import com.maker.crm.service.activity.ActivityRemarkService;
import com.maker.crm.service.activity.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/activity/*")
public class ActivitiesAction extends AbstractAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ActivitiesAction.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRemarkService activityRemarkService;
    @RequestMapping("index")
    public ModelAndView index(){
        List<User> ulist=userMapper.selectAllUsers();
        ModelAndView mav=new ModelAndView("/workbench/activity/index");
        mav.addObject("ulist",ulist);
        return mav;
    }
    @RequestMapping("add")
    @ResponseBody
    public Object add(Activity activity){
        boolean result=false;
        ReturnObject<Activity> returnObject=new ReturnObject<>();
        if(!(notNull(activity.getName())&&notNull(activity.getOwner()))){
            //市场活动名称和市场活动所有者不能为空
            //在此处添加验证是为了防止不通过表单直接请求不规范的数据
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("市场活动名称和所有者不能为空");
            return returnObject;
        }
        try {
           activity.setId(UUIDUtils.getUUID());
           activity.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
           String createBy = ((User) (request.getSession().getAttribute(Constant.SESSION_USER))).getId();
           activity.setCreateBy(createBy);
           result = activityService.addActivity(activity);
       }catch (Exception e){
           LOGGER.error("新建市场活动失败：{}",e.getMessage());
           returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
           returnObject.setMessage("服务器忙，请稍后重试...");

       }
       if(result){//插入成功
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setMessage("新建市场活动成功！");
       }else{
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，稍后重试...");
       }

        return returnObject;
    }

    /**
     * 接收前端传过来的数据
     * pageNo,当前页码
     * pageSize,每页条数
     * */
    @RequestMapping("listSplitByCondition")
    @ResponseBody
    public Object listSplitByCondition(Integer pageNo,Integer pageSize,String name,String owner,String startDate,String endDate){
        if(pageNo==null||pageNo<=0){
            pageNo=1;
        }
        if(pageSize==null || pageSize<=0){
            pageSize=2;//默认一页显示2条
        }
        Map<String,Object> map=new HashMap<>();

        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        //回传给页面，当整页刷新时起作用
        request.setAttribute("pageNo",pageNo);
        request.setAttribute("pageSize",pageSize);
        request.setAttribute("owner",owner);
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        request.setAttribute("url","/activity/listSplitByCondition");//点击跳转的url
        //
        List<Activity> activityList=activityService.queryActivitySplit(map);
        long totalCount= activityService.queryTotalCount(map);
        Map<String ,Object> resultMap=new HashMap<>();
        resultMap.put("activityList",activityList);
        resultMap.put("totalCount",totalCount);
        LOGGER.info("【Test】{}",resultMap);
        return resultMap;
    }
    @RequestMapping("removeActivities")
    @ResponseBody
    public Object removeActivities(String[] id){
        ReturnObject<Integer> returnObject=new ReturnObject<>();
        if(id==null){
            //返回删除失败的信息对象
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
            LOGGER.error("传入id为空");
            return returnObject;
        }

        if(id.length==0){
            //空数组，返回删除失败的信息对象
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
            LOGGER.error("传入id数组没有内容");
            return returnObject;
        }
        try {
            int result = activityService.removeActivityByIds(id);
            if(result>0){
                //正常删除
                returnObject.setMessage("删除成功");
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);

            }else{
                //未正常删除
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
                LOGGER.error("返回的删除条数为0");

            }
        }catch(Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
            LOGGER.error("批量删除出现异常：",e.getMessage());
        }
        return returnObject;
    }
    @RequestMapping("queryActivityById")
    @ResponseBody
    public Object queryActivityById(String id){
        LOGGER.info("【根据id查询】{}",id);
        Activity activity=activityService.queryById(id);
        ReturnObject<Activity> returnObject=new ReturnObject<>();
        if(activity==null){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
            return returnObject;
        }
        returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        returnObject.setObject(activity);
        returnObject.setMessage("查询成功");
        return returnObject;
    }
    @RequestMapping("editActivityById")
    @ResponseBody
    public Object editActivityById(Activity activity){
        int result=0;
        ReturnObject<Activity> returnObject=new ReturnObject<>();

        try{
            activity.setEditTime(DateUtil.dateToDateTimeStr(new Date()));
            User u=(User) request.getSession().getAttribute(Constant.SESSION_USER);
            activity.setEditBy(u.getId());
        result=activityService.editActivityById(activity);
        if(result==1){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setMessage("更新成功");
        }else{
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
    }catch (Exception e){
            LOGGER.error("更新数据异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
    /**
     * 如果将数据导出和文件下载合并到一起，可以进行优化
     * 将workbook生成的数据直接输出到response提供的输出流中输出
     * 这个过程中省去了跟磁盘的交互，可以提升一定的效率，且不需要生成文件
     * */
    @RequestMapping("exportAllActivity")
    @ResponseBody
    public Object exportAllActivity(){
       Map<String,Object> returnObject=new HashMap<>();

        try{
            returnObject=activityService.exportAllActivity();
        }catch (Exception e){
            LOGGER.error("导出所有市场活动信息时出错：{}",e.getMessage());
            returnObject.put("flag",false);


        }
        return returnObject;
    }

    @RequestMapping("exportSelectedActivity")
    @ResponseBody
    public Object exportSelectedActivity(String[] id){

        Map<String,Object> returnObject=new HashMap<>();
        if(id==null || id.length==0){
            LOGGER.error("【导出选中市场活动出错】未传入选中市场活动的id");
            returnObject.put("flag",false);
            return returnObject;
        }
        try{
            returnObject=activityService.exportSelectedActivity(id);
        }catch (Exception e){
            LOGGER.error("导出所有市场活动信息时出错：{}",e.getMessage());
            returnObject.put("flag",false);


        }
        return returnObject;
    }
    /**
     * 此处不能使用父类中application注入的response，否则下载会报网络错误
     * */
    @RequestMapping("downloadActivityFile")
    public void downloadActivityFile(HttpServletResponse response,String fileName){
        try{
        if(!StringUtils.hasLength(fileName)){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("未传入下载文件名");
            LOGGER.error("未传入下载文件名");
            return;
        }
        LOGGER.info("文件名：{}",fileName);
        response.setContentType("application/octet-stream;charset=UTF-8");//设置响应二进制流数据
        response.addHeader("Content-Disposition","attachment;filename="+fileName);//设置返回文件并进行下载
        OutputStream out=response.getOutputStream();
        FileInputStream in=new FileInputStream("D:"+ File.separator+"exportExcel"+File.separator+fileName);
        byte[] data=new byte[256];//文件读取到此缓冲区
            int len=0;
            while( (len=in.read(data))!=-1){
                out.write(data,0,len);
            }
            in.close();
            out.flush();

        }catch (Exception e){
            LOGGER.error("下载文件出错：{}",e.getMessage());
        }
    }

    /**
     * 批量插入导入数据
     * */
    @RequestMapping("importActivity")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile){
        ReturnObject<Map<String,String>> returnObject=new ReturnObject<>();

        if(activityFile==null){
            LOGGER.error("未获取到上传的文件");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");

            //returnObject.setObject();
            return returnObject;
        }
        String fileName=activityFile.getOriginalFilename();
        LOGGER.info("上传文件名：{}",fileName);
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        if(!("xls".equalsIgnoreCase(suffix))&&!("xlsx".equalsIgnoreCase(suffix))){
            //不是Excel文件
            LOGGER.error("不是上传的Excel文件");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("上传的文件非Excel文件");
            //returnObject.setObject(0);
        }else{
            try{
                //获取当前上传用户的信息
                HttpSession session=request.getSession();
                User currentUser=(User) session.getAttribute(Constant.SESSION_USER);
                InputStream is=activityFile.getInputStream();
                //获取上传的文件
                Workbook workbook=new HSSFWorkbook(is);
                Sheet sheet=workbook.getSheetAt(0);
                Row row= null;
                Cell cell=null;
                List<Activity> activityList=new ArrayList<>();
                StringBuilder faileRows=new StringBuilder();
                //从第二行开始遍历
                for(int i=1;i<=sheet.getLastRowNum();i++){//最后一行的下标
                    row=sheet.getRow(i);//从第二行开始读取数据
                    System.err.println(sheet.getLastRowNum());
                    Activity activity=new Activity();
                    activity.setCreateBy(currentUser.getId());
                    activity.setOwner(currentUser.getId());
                    activity.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
                    //记录每一行的信息
                    String id="";
                    String name="";
                    String startDate="";
                    String endDate="";
                    String cost="";
                    String description="";
                    //模板中，所有者、创建时间、创建人均不用填写，所有者创建人定为当前上传的用户，编辑时间和编辑人置为空
                    for(int j=0;j<row.getLastCellNum();j++){
                       System.err.println(row.getLastCellNum());
                        cell=row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        //最后一列的下标加1

                        if(j==0){//第一列表示id
                            id=POIUtils.getValue(cell);
                            if(id==null || "".equals(id)){
                                activity.setId(UUIDUtils.getUUID());
                            } else {
                                if(id.length()!=32){
                                    //id不合法，不允许插入
                                    faileRows.append((i+1)+",");
                                    break;
                                }
                                activity.setId(id);
                            }
                        }
                        if(j==1){//第二列名称
                            name=POIUtils.getValue(cell);
                            if(name==null||"".equals(name)){
                                //市场活动名称不能为空
                                faileRows.append((i+1)+",");
                                break;
                            }
                            activity.setName(name);
                        }

                        if(j==2){//第三列开始日期
                            startDate=POIUtils.getValue(cell);
                            //activity.setStartDate(startDate);
                        }
                        if(j==3){
                            endDate=POIUtils.getValue(cell);
                            if(StringUtils.hasLength(startDate)&&StringUtils.hasLength(endDate)){
                                if(startDate.compareTo(endDate)>0){
                                    //开始时间大于结束时间，不允许插入
                                    faileRows.append((i+1)+",");
                                    break;
                                }
                            }
                            activity.setStartDate(startDate);
                            activity.setEndDate(endDate);
                        }
                        if(j==4){
                            cost=POIUtils.getValue(cell);
                            activity.setCost(cost);
                        }
                        if(j==5){
                            description=POIUtils.getValue(cell);
                            activity.setDescription(description);
                        }


                    }
                    activityList.add(activity);

                }
                System.err.println(activityList);
                //完成Excel文件的解析
                LOGGER.info("Excel文件解析完毕！");
                workbook.close();
                //将解析到的数据导入到数据库中
                int successResult=activityService.importActivity(activityList);
                LOGGER.info("成功导入的数据为：{}、导入失败的行：{}",successResult,faileRows);
                Map<String,String> ret=new HashMap<>();
                ret.put("success",String.valueOf(successResult));
                ret.put("fail",faileRows.toString());
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("成功导入"+successResult+"条数据");
                returnObject.setObject(ret);

            }catch (Exception e){
                LOGGER.error("进行导入数据插入时出错：{}",e.getMessage());
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("服务器忙，请稍后重试...");
                //returnObject.setObject(0);

            }

        }
        return returnObject;
    }
    @RequestMapping("activityDetails")
    public ModelAndView activityDetails(String id){
        ModelAndView mav=new ModelAndView();
        try{
           Activity activity= activityService.queryActivityById(id);
           List<ActivityRemark> remarkList=activityRemarkService.queryActivityRemarkByAid(id);
           mav.setViewName("/workbench/activity/detail");
           mav.addObject("activity",activity);
           mav.addObject("activityRemarks",remarkList);

        }catch(Exception e){
            LOGGER.error("出现异常：{}",e.getMessage());
            mav.setViewName("/errors/exception");
        }

        return mav;
    }
}
