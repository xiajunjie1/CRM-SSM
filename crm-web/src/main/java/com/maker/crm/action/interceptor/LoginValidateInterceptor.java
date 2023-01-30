package com.maker.crm.action.interceptor;

import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginValidateInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER= LoggerFactory.getLogger(LoginValidateInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user=(User) request.getSession().getAttribute(Constant.SESSION_USER);
        //用户是否登录验证
        if(user==null){//用户未登录
            LOGGER.info("用户未登录,当前Context路径：{}",request.getContextPath());
            response.sendRedirect(request.getContextPath()+"/errors/login/not_login");
            return false;
        }
        return true;
    }

}
