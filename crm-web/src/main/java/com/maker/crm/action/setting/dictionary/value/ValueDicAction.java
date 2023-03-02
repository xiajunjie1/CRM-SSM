package com.maker.crm.action.setting.dictionary.value;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.UUIDUtils;
import com.maker.crm.model.DictionaryType;
import com.maker.crm.model.DictionaryValue;
import com.maker.crm.service.dictionary.DictionaryTypeService;
import com.maker.crm.service.dictionary.DictionaryValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/dictionary/value/*")
@Controller
public class ValueDicAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(ValueDicAction.class);
    @Autowired
    private DictionaryTypeService dictionaryTypeService;
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mav=new ModelAndView("/settings/dictionary/value/index");

        return mav;
    }
    @RequestMapping("save")
    public ModelAndView save(){
        ModelAndView mav=new ModelAndView("/settings/dictionary/value/save");
        List<DictionaryType> dictionaryTypeList=null;
        //List<DictionaryValue> dictionaryValueList=null;
        try {
            dictionaryTypeList = dictionaryTypeService.queryAllDicType();
            mav.addObject("dicTypeList",dictionaryTypeList);
        }catch (Exception e){
            LOGGER.error("查询字典类型出错！");
            mav.setViewName("/errors/exception");
        }
        return mav;
    }
    @RequestMapping("saveHandler")
    @ResponseBody
    public Object saveHandler(DictionaryValue dictionaryValue){
        ReturnObject<DictionaryValue> returnObject=new ReturnObject<>();
        if(dictionaryValue==null){
            LOGGER.error("传入字典值参数为空");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
            return returnObject;
        }
        dictionaryValue.setId(UUIDUtils.getUUID());
        if(!StringUtils.hasLength(dictionaryValue.getOrderNo())){
            dictionaryValue.setOrderNo("0");
        }
        try {
            dictionaryValueService.addDicValue(dictionaryValue);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setObject(dictionaryValue);
        }catch (Exception e){
           LOGGER.error("插入字典值是出错：{}",e.getMessage());
           returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
           returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
        }

    public String edit(){
        return "/settings/dictionary/value/edit";
    }
    @RequestMapping("queryAllDicValue")
    @ResponseBody
    public Object queryAllDicValue(){
        List<DictionaryValue> dictionaryValueList=null;
        ReturnObject<List<DictionaryValue>> returnObject=new ReturnObject<>();
        try{
        dictionaryValueList=dictionaryValueService.queryAllDicValue();
        returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        returnObject.setObject(dictionaryValueList);
    }catch (Exception e){
            LOGGER.error("查询全部字典值出错：{}",e.getMessage());
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;

    }
}
