<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#relogin").click(function(){
            window.top.location.href="/";
        });
    });
</script>
</head>
<body>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2022&nbsp;Jayjxia</span></div>
	</div>
	<div class="container" style="position:relative;top:10%">
       <div class="jumbotron">
            <h1>访问出错！</h1>
            <p>遇到问题，请联系管理员。</p>
            <p><a id="relogin" class="btn btn-primary btn-lg" role="button" href="#">
            返回主页</a>
          </p>
       </div>
    </div>
</body>
</html>