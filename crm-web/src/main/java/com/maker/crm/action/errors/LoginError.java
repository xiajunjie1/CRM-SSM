package com.maker.crm.action.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors/login/*")
public class LoginError {
    @RequestMapping("not_login")
    public String notLogin(){
        return "/errors/notLogin";
    }
}
