package com.maker.crm.action.workbench;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workbench/*")
public class WorkbenchIndexAction {
    @RequestMapping("index")
    public String index(){
        return "/workbench/index";
    }
    @RequestMapping("main/index")
    public String main(){
        return "/workbench/main/index";
    }
}
