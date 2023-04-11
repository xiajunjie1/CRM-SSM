package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.model.*;
import com.maker.crm.service.activity.ActivityService;
import com.maker.crm.service.clue.ClueRemarkService;
import com.maker.crm.service.clue.ClueService;
import com.maker.crm.service.dictionary.DictionaryValueService;
import com.maker.crm.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clue/*")
public class ClueAction extends AbstractAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ClueAction.class);
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mav=new ModelAndView("/workbench/clue/index");
        try {
            List<User> userList = userService.queryAllUsers();
            List<DictionaryValue> clueStateList = dictionaryValueService.queryDicValueByTypeCode("clueState");
            List<DictionaryValue> sourceList=dictionaryValueService.queryDicValueByTypeCode("source");
            List<DictionaryValue> appellationList=dictionaryValueService.queryDicValueByTypeCode("appellation");
            mav.addObject("ulist",userList);
            mav.addObject("csList",clueStateList);
            mav.addObject("sList",sourceList);
            mav.addObject("appeList",appellationList);
        }catch (Exception e){
            LOGGER.error("进入线索页面查询字典数据出错：{}",e.getMessage());
            mav.setViewName("/errors/exception");
        }
        //mav.addObject("");
        return mav;
    }
    @PostMapping("addClue")
    @ResponseBody
    public Object addClue(Clue clue) {
         ReturnObject<Clue> returnObject=new ReturnObject<>();
        HttpSession session= request.getSession();
        User user=(User) session.getAttribute(Constant.SESSION_USER);
         clue.setCreateBy(user.getId());
         clue.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
         clue.setId(UUIDUtils.getUUID());
         try{
             int result=clueService.addClue(clue);
             if(result<1){
                 returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                 returnObject.setMessage("添加失败...");
             }else{
                 returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setObject(clue);
             }
         }catch (Exception e){
             LOGGER.error("添加线索出现异常：{}",e.getMessage());
             returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
             returnObject.setMessage("服务器忙，请稍后重试...");
         }
         return returnObject;
    }
    @RequestMapping("queryClueSplitByCondition")
    @ResponseBody
    public Object queryClueSplitByCondition(@RequestBody Map<String,String> param){
        //ReturnObject<List<Clue>> returnObject=new ReturnObject<>();
        Map<String,Object> returnObject=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        LOGGER.info("【传入参数】{}",param);
        if(param==null){
            returnObject.put("code",Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.put("message","传入参数为空，请联系管理员...");
        }
        try{
            int pageNo=Integer.parseInt(param.get("pageNo"));
            if(pageNo<1){
                pageNo=1;//置为第一页
            }
            int pageSize=Integer.parseInt(param.get("pageSize"));
           int beginNo=(pageNo-1)*pageSize;
           map.put("beginNo",beginNo);
           map.put("pageSize",pageSize);
           map.put("fullname",param.get("fullname"));
           map.put("company",param.get("company"));
           map.put("phone",param.get("phone"));
           map.put("source",param.get("source"));
           map.put("owner",param.get("owner"));
           map.put("mphone",param.get("mphone"));
           map.put("state",param.get("state"));
            List<Clue> clueList=clueService.queryClueSplit(map);
            long totalCount=clueService.queryClueCountByCondition(map);
            LOGGER.info("【clue count】{}",totalCount);
            returnObject.put("clueList",clueList);
            returnObject.put("totalCount",totalCount);
            returnObject.put("code",Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            LOGGER.error("根据条件分页查询线索出现异常：{}",e.getMessage());
            returnObject.put("message","服务器忙，请稍后重试...");
            returnObject.put("code",Constant.RETURN_OBJECT_CODE_FAIL);
        }
        return returnObject;
    }
    @RequestMapping("clueDetails")
    public ModelAndView clueDetails(String id){
        ModelAndView mav=new ModelAndView("/workbench/clue/detail");
        LOGGER.info("【根据id查询线索】{}",id);
        try{
            Clue clue=clueService.queryClueById(id);
            List<ClueRemark> clueRemarks=clueRemarkService.queryClueRemarksByCid(id);
            List<Activity> activityList=activityService.queryActivityByCid(id);
            mav.addObject("clue",clue);
            mav.addObject("clueRemarks",clueRemarks);
            mav.addObject("activityList",activityList);
            LOGGER.info("【根据id查询线索】{}、{}",id,clue.toString());

        }catch (Exception e){
            LOGGER.error("根据id查询线索出现异常：{}",e.getMessage());
            mav.setViewName("/errors/exception");
        }
        return mav;
    }
    @RequestMapping("toConvert")
    public ModelAndView toConvert(String cid){
        ModelAndView mav=new ModelAndView("/workbench/clue/convert");
        try{
            Clue clue=clueService.queryClueById(cid);
            mav.addObject("clue",clue);
            List<DictionaryValue> dictionaryValueList=dictionaryValueService.queryDicValueByTypeCode("stage");
            mav.addObject("dvList",dictionaryValueList);

        }catch (Exception e){
            LOGGER.error("跳转线索转换页面出现异常：{}",e.getMessage());
            mav.setViewName("/errors/exception");
        }
        return mav;
    }
    @RequestMapping("clueConvertHandle")
    @ResponseBody
    public Object clueConvertHandle(@RequestBody Map<String,Object> param){
        ReturnObject returnObject=new ReturnObject();
        if(param==null){
            LOGGER.error("线索转换传入参数为空...");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        try{
            HttpSession session= request.getSession();
            User user=(User) session.getAttribute(Constant.SESSION_USER);
            param.put(Constant.SESSION_USER,user);
            clueService.saveClueConvert(param);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            LOGGER.error("线索转换出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return  returnObject;
    }
}
