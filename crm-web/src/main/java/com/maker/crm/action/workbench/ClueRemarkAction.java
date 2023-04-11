package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.model.ClueRemark;
import com.maker.crm.model.User;
import com.maker.crm.service.clue.ClueRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RequestMapping("/clueRemark/*")
@Controller
public class ClueRemarkAction extends AbstractAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ClueRemarkAction.class);
    @Autowired
    private ClueRemarkService clueRemarkService;
    @RequestMapping("addClueRemark")
    @ResponseBody
    public Object addClueRemark(ClueRemark clueRemark){
        ReturnObject<ClueRemark> returnObject=new ReturnObject<>();

        try{
            clueRemark.setId(UUIDUtils.getUUID());
            clueRemark.setEditFlag("0");
            clueRemark.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
            HttpSession session= request.getSession();
            User u=(User) session.getAttribute(Constant.SESSION_USER);
            clueRemark.setCreateBy(u.getId());
            int result=clueRemarkService.addClueRemark(clueRemark);
            if(result<1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("添加备注失败，请联系管理员...");

            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setObject(clueRemark);
            }
        }catch (Exception e){
            LOGGER.error("插入线索备注出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }

        return returnObject;
    }
    @RequestMapping("removeClueRemarkById")
    @ResponseBody
    public Object removeClueRemarkById(String id){
        ReturnObject<ClueRemark> returnObject=new ReturnObject<>();
        try{
            int result= clueRemarkService.removeClueRemarkById(id);
            if(result<1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("该线索备注已不存在，请刷新页面...");

            }else{
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }
        }catch (Exception e){
            LOGGER.error("根据id删除线索备注出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
}
