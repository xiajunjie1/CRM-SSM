package com.maker.crm.action.setting.dictionary.type;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.model.DictionaryType;
import com.maker.crm.service.dictionary.DictionaryTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/dictionary/type/*")
@Controller
public class TypeDicAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(TypeDicAction.class);
    @Autowired
    private DictionaryTypeService dictionaryTypeService;
    @RequestMapping("index")
    public ModelAndView index(){

        ModelAndView mav=new ModelAndView("/settings/dictionary/type/index");
        List<DictionaryType> dictionaryTypeList=null;
        try{
            dictionaryTypeList=dictionaryTypeService.queryAllDicType();

        }catch (Exception e){
            LOGGER.error("查询所有类型出错：{}",e.getMessage());
        }

        mav.addObject("dicTypeList",dictionaryTypeList);
        return mav;
    }

    @RequestMapping("save")
    public String save(){

        return "/settings/dictionary/type/save";
    }
    @RequestMapping("edit")
    public String edit(){
        return "/settings/dictionary/type/edit";
    }
    @RequestMapping("queryDicTypeByCode")
    @ResponseBody
    public Object queryDicTypeByCode(String code){
        ReturnObject<DictionaryType> returnObject=new ReturnObject<>();
        try{
            DictionaryType dictionaryType= dictionaryTypeService.queryDicTypeByCode(code);
            if(dictionaryType==null){
                //未查询到
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("未查询到"+code+"的类型项");
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setObject(dictionaryType);
            }
        }catch (Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
    @RequestMapping("addDicType")
    @ResponseBody
    public Object addDicType(DictionaryType dictionaryType){
        ReturnObject<DictionaryType> returnObject=new ReturnObject<>();
       try{
           int result=dictionaryTypeService.addDicType(dictionaryType);
            if(result>0){
                //插入成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);

            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("服务器忙，请稍后重试");
            }
            }catch (Exception e){
                LOGGER.error("添加DictionaryType出现异常：{}",e.getMessage());
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("服务器忙，请稍后重试...");
            }
       return returnObject;
    }



}
