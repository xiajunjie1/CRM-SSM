<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>


<meta charset="UTF-8">
<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
        <script type="text/javascript" >
           $(document).ready(function(){
        //实现点击回车，发送登录请求的功能
        $(window).keydown(function(e){//对整个浏览器窗口添加按键事件
            if(e.keyCode==13){
                //按下回车键，模拟发送按钮点击
                $("#loginBtn").click();
            }
        });

            $("#loginBtn").click(function(){
                //添加点击事件
                var loginAct=$.trim($("#loginAct").val());//获取用户名
                var loginPwd=$.trim($("#loginPwd").val());//获取密码
                var isRem=$("#isRem").prop("checked");//获取是否勾选记住密码，对于结果为true/false的属性可以用prop获取，attr()可以获取属性="xxxx"的值
                //返回结果为true/false的常见属性有：checked、selected、readonly、disabled
                if(loginAct==""){
                    $("#msg").html("用户名不能为空！");
                    return;
                }
                if(loginPwd==""){
                    $("#msg").html("密码不能为空！");
                    return;
                }
                //用户名和密码都不为空,发送ajax请求，返回值为JSON
                $.ajax(
                    {
                        url:'/user/login_handle',
                        data:{
                            'loginAct':loginAct,
                            'loginPwd':loginPwd,
                            'isRem':isRem
                        },
                        type:'post',
                        dataType:'json',
                        success:function(data){
                            //处理响应信息
                            if(data.code==1){
                                //登录成功
                                window.location.href="/workbench/index";
                            }else{
                                $("#msg").html(data.message);
                            }
                        },
                        beforeSend:function(){
                                //当ajax向后台发送请求之前，会自动执行本函数
                                //当该方法执行后返回true，则向后台发送请求
                                //可以将参数验证的代码放在此函数中
                                $("#msg").html("正在登录<img width=20px height=20px src='/image/loading.gif' />");//添加登录提示
                                return true;
                        }

                    }
                )
            });
    });
 </script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="/image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2022&nbsp;Jayjxia</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="#" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" name="loginAct" id="loginAct" value="${cookie.loginAct.value}">
					</div>
					<div style="width: 350px; position: relative;top: 20px;" >
						<input class="form-control" type="password" placeholder="密码" name="loginPwd" id="loginPwd" value="${cookie.loginPwd.value}">
					</div>
					<div class="checkbox" style="position: relative;top: 30px; left: 10px;">
						<label>
						    <c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
						        <!--两个cookie均不为空 -->
                                <input type="checkbox" name="isRem" id="isRem" checked>
						    </c:if>
						    <c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
						        <!--两个cookie中任意为空 -->
						        <input type="checkbox" name="isRem" id="isRem">
						    </c:if>
							 十天内免登录
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color:#f00"></span>
					</div>
					<button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>