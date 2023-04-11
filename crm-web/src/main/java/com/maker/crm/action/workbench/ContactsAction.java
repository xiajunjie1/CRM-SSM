package com.maker.crm.action.workbench;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.model.Contacts;
import com.maker.crm.service.contacts.ContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/contacts/*")
public class ContactsAction {
    @Autowired
    private ContactsService contactsService;
    private static final Logger LOGGER= LoggerFactory.getLogger(ContactsAction.class);
    @RequestMapping("queryContactsByName")
    @ResponseBody
    public Object queryContactsByName(String name){
        ReturnObject<List<Contacts>> returnObject=new ReturnObject<>();
        try{
            List<Contacts> contactsList=contactsService.queryContactsByName(name);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setObject(contactsList);
        }catch (Exception e){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("服务器忙，请稍后重试...");
        }
        return returnObject;
    }
}
