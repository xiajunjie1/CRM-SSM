package com.maker.crm.action.setting.user;

import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.maker.crm.action.abs.AbstractAction;
import com.maker.crm.action.abs.constants.Constant;
import com.maker.crm.commons.model.ReturnObject;
import com.maker.crm.commons.utils.DateUtil;
import com.maker.crm.model.User;
import com.maker.crm.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 处理跳转到用户登录页面的请求
 * 处理用户登录页面表单提交的请求
 * */
@Controller
@RequestMapping("/user/*")
public class UserAction extends AbstractAction {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserAction.class);
    @RequestMapping("login")
    public String login(){

        User user=(User) request.getSession().getAttribute(Constant.SESSION_USER);
        if(user!=null){
            //该用户已经登录过了，进入主页
            return "redirect:/workbench/index";
        }
        return "/settings/qx/user/login";
    }

    @RequestMapping("login_handle")
    @ResponseBody
    public Object loginHandle(String loginAct,String loginPwd,String isRem){
        Map<String,Object> param=new HashMap<>();
        param.put("loginAct",loginAct);
        param.put("loginPwd",loginPwd);
        User user=userService.queryUserByLoginActAndPwd(param);

        ReturnObject<User> returnObject=new ReturnObject();
        if(user==null){
            //用户名密码验证不通过
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
            return returnObject;
        }
        if("0".equals(user.getLockStat())){
            //该用户已被锁定
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("该用户已被锁定");
            return returnObject;
        }
        //将当前时间转化为字符串和用户过期时间比较大小，也可以将字符串转换为Date类型，在利用getTime()比较大小
        if(user.getExpireTime()!=null && !"".equals(user.getExpireTime()) && DateUtil.dateToDateTimeStr(new Date()).compareTo(user.getExpireTime())>0){//当前时间大于过期时间，账号已过期
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前用户已过期");
            return returnObject;
        }
        //获取请求用户的IP
       //HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();//在抽象类中已经获取了当前线程的request和response对象了
        LOGGER.info("请求的ip为：{}",request.getRemoteAddr());
        if(user.getAllowIp()!=null && "".equals(user.getAllowIp()) && !user.getAllowIp().contains(request.getRemoteAddr())){
            //设置了允许登录的ip,且请求ip不在允许登录的ip之中
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前ip禁止登录该账号");
            return returnObject;
        }
        //后台记住密码完成后，需要在前台用el表达式进行cookie的获取
        if("true".equals(isRem)){
            //记住密码被勾选，往浏览器写入cookie
            Cookie c1=new Cookie(Constant.COOKIE_LOGIN_ACT,user.getLoginAct());
            c1.setMaxAge(Constant.COOKIE_LOGIN_MAX_AGE);
            response.addCookie(c1);
            Cookie c2=new Cookie(Constant.COOKIE_LOGIN_PWD,user.getLoginPwd());
            c2.setMaxAge(Constant.COOKIE_LOGIN_MAX_AGE);
            response.addCookie(c2);
        }else{
            //没有勾选记住密码选项，删除掉之前存储过的Cookie，如果之前为存储Cookie，由于此时准备覆写的Cookie设置的生命周期为0，写入后马上也会被删除
            Cookie c1=new Cookie(Constant.COOKIE_LOGIN_ACT,"");
            c1.setMaxAge(0);//立即失效
            response.addCookie(c1);
            Cookie c2=new Cookie(Constant.COOKIE_LOGIN_PWD,"");
            c2.setMaxAge(0);
            response.addCookie(c2);
        }
        returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        returnObject.setMessage("登录成功");
        returnObject.setObject(user);
        request.getSession().setAttribute(Constant.SESSION_USER,user);
        return returnObject;
    }

    /**
     *  安全退出功能实现
     * */
    @RequestMapping("logout_handle")
    public String logoutHandle(){
        //清除Cookie
        Cookie c1=new Cookie(Constant.COOKIE_LOGIN_ACT,"");
        c1.setMaxAge(0);//立即失效
        response.addCookie(c1);
        Cookie c2=new Cookie(Constant.COOKIE_LOGIN_PWD,"");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //清除session
        request.getSession().invalidate();
        return "redirect:/user/login";//重定向到登录页面，地址栏也变为登录界面，防止用户刷新再次执行安全退出方法
    }
}
