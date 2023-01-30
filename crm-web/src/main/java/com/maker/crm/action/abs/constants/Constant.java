package com.maker.crm.action.abs.constants;
/**
 * 常量类，专门存放常量
 * */
public class Constant {
    //用户登录成功标识code
    public static final int RETURN_OBJECT_CODE_SUCCESS=1;
    //用户登录失败标识code
    public static final int RETURN_OBJECT_CODE_FAIL=0;
    //Session 用户属性key
    public static final String SESSION_USER="sessionUser";
    //存放在cookie中用户名的key
    public static final String COOKIE_LOGIN_ACT="loginAct";
    //存放在cookie中密码的key
    public static final String COOKIE_LOGIN_PWD="loginPwd";
    //登录相关的Cookie的最大生命周期
    public static final int COOKIE_LOGIN_MAX_AGE=3600*240;//10天
}
