package com.maker.crm.action.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonAction {
    private static final Logger LOGGER= LoggerFactory.getLogger(CommonAction.class);
    @RequestMapping("/")
    public String index(){

        return "/index";
    }

}
