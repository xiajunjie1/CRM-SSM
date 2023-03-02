package com.maker.crm.action.setting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/setting/*")
@Controller
public class SettingIndexAction {
    @RequestMapping("index")
    public String index(){
        return "/settings/index";
    }
}
