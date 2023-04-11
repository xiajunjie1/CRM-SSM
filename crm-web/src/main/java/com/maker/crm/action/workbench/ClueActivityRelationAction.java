package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.model.Activity;
import com.maker.crm.model.ClueActivityRelation;
import com.maker.crm.service.activity.ActivityService;
import com.maker.crm.service.clue.ClueActivityRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/clue/clueActivityRelation/*")
public class ClueActivityRelationAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ClueActivityRelationAction.class);
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;
    @Autowired
    private ActivityService activityService;
    @RequestMapping("addRelation")
    @ResponseBody
    public Object addRelation(String[] ids,String cid){
        List<ClueActivityRelation> clueActivityRelations=new ArrayList<>();
       ClueActivityRelation clueActivityRelation=null;
        ReturnObject<List<Activity>> returnObject=new ReturnObject<>();

        try{
            for(String aid : ids){
                clueActivityRelation=new ClueActivityRelation();
                clueActivityRelation.setId(UUIDUtils.getUUID());
                clueActivityRelation.setActivityId(aid);
                clueActivityRelation.setClueId(cid);
                clueActivityRelations.add(clueActivityRelation);
            }
            int res=clueActivityRelationService.addClueActivityRelationBatch(clueActivityRelations);
            if(res>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList=activityService.queryActivityByIds(ids);
                returnObject.setObject(activityList);
            }else{
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("关联失败，请联系系统管理员");
            }
        }catch (Exception e){
            LOGGER.error("关联线索和市场活动出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("removeRelationByAidCid")
    @ResponseBody
    public Object removeRelationByAidCid(ClueActivityRelation clueActivityRelation){
        ReturnObject<ClueActivityRelation> returnObject=new ReturnObject<>();
        try{
            int res=clueActivityRelationService.removeClueActivityRelation(clueActivityRelation);
            if(res>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);

            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("接触关联失败，请联系系统管理员...");
            }
        }catch (Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
}
