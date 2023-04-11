package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.commons.utils.PropertiesUtil;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.dao.tran.TransactionMapper;
import com.maker.crm.model.*;
import com.maker.crm.service.dictionary.DictionaryValueService;
import com.maker.crm.service.tran.TransactionHistoryService;
import com.maker.crm.service.tran.TransactionRemarkService;
import com.maker.crm.service.tran.TransactionService;
import com.maker.crm.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tran/*")
public class TranAction extends AbstractAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(TranAction.class);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRemarkService transactionRemarkService;
    @Autowired
    private TransactionHistoryService transactionHistoryService;
    @RequestMapping("index")
    public ModelAndView toIndex(){
        ModelAndView mav=new ModelAndView("/workbench/transaction/index");
        try{
            List<DictionaryValue> stageList=dictionaryValueService.queryDicValueByTypeCode("stage");
            List<DictionaryValue> typeList=dictionaryValueService.queryDicValueByTypeCode("transactionType");
            List<DictionaryValue> sourceList=dictionaryValueService.queryDicValueByTypeCode("source");

            //List<Transaction> transactionList= transactionService.queryTransactionSplitByCondition()
            mav.addObject("stageList",stageList);
            mav.addObject("typeList",typeList);
            mav.addObject("sourceList",sourceList);
        }catch (Exception e){
            LOGGER.error("跳转到交易主页出现异常！");
            mav.setViewName("/errors/exception");
        }

        return  mav;
    }
    @RequestMapping("queryTranSplitByCondition")
    @ResponseBody
    public Object queryTranSplitByCondition(@RequestBody Map<String,Object> map){
        Map<String,Object> result=new HashMap<>();
        if(map==null || map.isEmpty()){
            LOGGER.error("分页查询交易信息，传入参数为空");
            return result;
        }
        if(map.get("pageNo")==null){
            map.put("pageNo",1);
            LOGGER.error("分页查询交易信息页码为空");
        }
        if(map.get("pageSize")==null){
            map.put("pageSize",2);
            LOGGER.error("分页查询交易信息每页条数为空");
        }
        try {
            int pageNo=Integer.parseInt(map.get("pageNo").toString());
            int pageSize=Integer.parseInt(map.get("pageSize").toString());
            int beginNo=(pageNo-1)*pageSize;
            map.put("beginNo",beginNo);
            List<Transaction> transactionList=transactionService.queryTransactionSplitByCondition(map);
            int transactionCount=transactionService.queryCountByCondition(map);
            result.put("code",Constant.RETURN_OBJECT_CODE_SUCCESS);
            result.put("transactionList",transactionList);
            result.put("transactionCount",transactionCount);
        }catch (Exception e){
            LOGGER.error("分页查询交易信息出现异常：{}",e.getMessage());
            result.put("code", Constant.RETURN_OBJECT_CODE_FAIL);
            result.put("message","服务器忙，请稍后重试...");
        }
        return result;
    }
    @RequestMapping("toTranSave")
    public ModelAndView toTranSave(){
        ModelAndView mav=new ModelAndView("/workbench/transaction/save");
        try{
            List<User> userList=userService.queryAllUsers();
            List<DictionaryValue> stageList=dictionaryValueService.queryDicValueByTypeCode("stage");
            List<DictionaryValue> typeList=dictionaryValueService.queryDicValueByTypeCode("transactionType");
            List<DictionaryValue> sourceList=dictionaryValueService.queryDicValueByTypeCode("source");
            mav.addObject("userList",userList);
            mav.addObject("stageList",stageList);
            mav.addObject("typeList",typeList);
            mav.addObject("sourceList",sourceList);
        }catch (Exception e){
            LOGGER.error("跳转创建交易页面出现异常：{}",e.getMessage());
            mav.setViewName("/errors/exception");
        }
        return mav;
    }
    @RequestMapping("getPossibility")
    @ResponseBody
    public Object getPossibility(String stageText){
        ReturnObject<String> returnObject=new ReturnObject<>();
       try{
           PropertiesUtil propertiesUtil=new PropertiesUtil();
           String value=propertiesUtil.getValue(stageText);
           returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
           returnObject.setObject(value);
       }catch (Exception e){
           LOGGER.error("获取可能性出现异常：{}",e.getMessage());
           returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
           returnObject.setMessage("服务器忙，请稍后重试...");
       }

        return returnObject;
    }
    @RequestMapping("saveTranHandle")
    @ResponseBody
    public Object saveTranHandle(Transaction transaction,String cusName){
        ReturnObject<Transaction> returnObject=new ReturnObject<>();
        Map<String,Object> map=new HashMap<>();
        map.put("tran",transaction);
        map.put("cusName",cusName);
        HttpSession session=request.getSession();
        map.put(Constant.SESSION_USER,session.getAttribute(Constant.SESSION_USER));
        try{
            transactionService.addTransaction(map);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            LOGGER.error("添加交易出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return  returnObject;
    }
    @RequestMapping("toDetails")
    public ModelAndView toDetails(String id){
        ModelAndView mav=new ModelAndView("/workbench/transaction/detail");
        //ReturnObject<Transaction> returnObject=new ReturnObject<>();
        try{
            Transaction transaction=transactionService.queryTransactionDetailById(id);
            mav.addObject("transaction",transaction);
            String possibility=new PropertiesUtil().getValue(transaction.getStage());
            mav.addObject("possibility",possibility);
            List<TransactionRemark> transactionRemarkList=transactionRemarkService.queryTransactionRemarkByTranId(id);
            mav.addObject("tranRemarkList",transactionRemarkList);
            List<TransactionHistory> transactionHistoryList=transactionHistoryService.queryTransactionHistoryByTranId(id);
            mav.addObject("tranHistoryList",transactionHistoryList);
           // returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            //returnObject.setObject(transaction);
            List<DictionaryValue> dictionaryValueList=dictionaryValueService.queryDicValueByTypeCode("stage");
            mav.addObject("dicValueList",dictionaryValueList);
        }catch (Exception e){
            LOGGER.error("跳转交易细节页面出现异常：{}",e.getMessage());
            //returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            //returnObject.setMessage("服务器忙，请稍后重试...");
            mav.setViewName("/errors/exception");
        }
        return mav;
    }
    @RequestMapping("editTransactionStageByTranId")
    @ResponseBody
    public Object editTransactionStageByTranId(String tranId,String stageId,String money,String expectedDate){
        ReturnObject returnObject=new ReturnObject();
        TransactionHistory transactionHistory=new TransactionHistory();
        transactionHistory.setId(UUIDUtils.getUUID());
        transactionHistory.setTranId(tranId);
        transactionHistory.setMoney(money);
        transactionHistory.setStage(stageId);
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(Constant.SESSION_USER);
        transactionHistory.setCreateBy(user.getId());
        transactionHistory.setCreateTime(DateUtil.dateToDateTimeStr(new Date()));
        transactionHistory.setExpectedDate(expectedDate);
        try{
            transactionService.editTransactionStageByTranId(transactionHistory);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            LOGGER.error("修改交易状态出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return  returnObject;
    }
}
