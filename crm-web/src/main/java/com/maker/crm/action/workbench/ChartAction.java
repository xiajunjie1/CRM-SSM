package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.model.FunnelVo;
import com.maker.crm.service.tran.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/chart/*")
public class ChartAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ChartAction.class);
    @Autowired
    private TransactionService transactionService;
    @RequestMapping("tran/toChartIndex")
    public String toTranChartIndex(){
        return "/workbench/chart/transaction/index";
    }
    @RequestMapping("tran/getData")
    @ResponseBody
    public Object getData(){
        ReturnObject<List<FunnelVo>> returnObject=new ReturnObject<>();
        try{
            List<FunnelVo> funnelVoList=transactionService.queryTransactionFunnelVo();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setObject(funnelVoList);

        }catch (Exception e){
            LOGGER.error("获取交易漏斗图信息出现异常：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
}
