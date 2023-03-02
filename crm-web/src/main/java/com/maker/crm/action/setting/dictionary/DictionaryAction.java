package com.maker.crm.action.setting.dictionary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dictionary/*")
public class DictionaryAction {
    @RequestMapping("index")
    public String index(){
        return "/settings/dictionary/index";
    }
}
