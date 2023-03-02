package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.model.ActivityRemark;
import com.maker.crm.model.User;
import com.maker.crm.service.activity.ActivityRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
@Controller
@RequestMapping("/activityRemark/*")
public class ActivityRemarkAction extends AbstractAction {
    @Autowired
    private ActivityRemarkService activityRemarkService;
    private static final Logger LOGGER= LoggerFactory.getLogger(ActivityRemarkAction.class);
    @RequestMapping("saveRemark")
    @ResponseBody
    public Object saveRemark(ActivityRemark remark){
        ReturnObject<ActivityRemark> returnObject=new ReturnObject<>();
        if(remark==null){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("传入市场活动信息为空");

            return returnObject;
        }
        remark.setId(UUIDUtils.getUUID());
        remark.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
        remark.setEditFlag(Constant.EDIT_FLAG_NO_EDITED);
        User u=(User)request.getSession().getAttribute(Constant.SESSION_USER);
        remark.setCreateBy(u.getId());
        try{
            int result=activityRemarkService.addActivityRemark(remark);
            if(result==0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("服务器忙，请稍后重试");
                returnObject.setObject(remark);
            }else{

                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("添加成功");
                returnObject.setObject(remark);
            }
        }catch (Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试");
            returnObject.setObject(remark);
        }
        return returnObject;
    }
    @RequestMapping("removeRemarkById")
    @ResponseBody
    public Object removeRemarkById(String id){
        ReturnObject<ActivityRemark> returnObject=new ReturnObject<>();
        try{
            int result=activityRemarkService.removeActivityRemarkById(id);
            if(result>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("服务器忙，请稍后重试...");
            }
        }catch(Exception e){
            LOGGER.error("删除ID为{}的数据出现异常：{}",id,e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
    @RequestMapping("editRemarkById")
    @ResponseBody
    public Object editRemarkById(ActivityRemark activityRemark){
        ReturnObject<ActivityRemark> returnObject=new ReturnObject<>();
        if(activityRemark==null){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
            LOGGER.error("传入参数为空");
            return returnObject;
        }
        try {
            HttpSession session = request.getSession();
            User u = (User) session.getAttribute(Constant.SESSION_USER);
            activityRemark.setEditBy(u.getId());
            activityRemark.setEditTime(DateUtil.dateToDateTimeStr(new Date()));
            activityRemark.setEditFlag(Constant.EDIT_FLAG_YES_EDITED);
            int result=activityRemarkService.editActivityRemarkById(activityRemark);
            if(result>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                activityRemark.setEditBy(u.getName());
                returnObject.setObject(activityRemark);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("服务器忙，请稍后重试...");
            }
        }catch (Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");

            LOGGER.error("修改备注出现异常：{}",e.getMessage());
        }
        return returnObject;
    }
}
