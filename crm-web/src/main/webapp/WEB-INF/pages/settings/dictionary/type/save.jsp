<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" >
    $(function(){
        var code=$.trim($("#code").val());
        if(code==null || code==""){
            $("#saveBtn").attr("disabled",true);
        }
        $("#code").blur(function(){
            var code=$.trim($("#code").val());
            if(code==null || code==""){
                $("#codeHint").text("输入的编码不能为空");
                $("#saveBtn").attr("disabled",true);
                return ;
            }
            $.ajax({
                'url':"/dictionary/type/queryDicTypeByCode",
                'data':{
                    'code':code
                },
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                         $("#codeHint").text("编码已存在");
                                        $("#saveBtn").attr("disabled",true);
                                        return ;
                    }else{
                        $("#codeHint").text("");
                        $("#saveBtn").attr("disabled",false);
                    }
                }
            });
        });

        $("#saveBtn").click(function(){
            var save_code=$("#code").val();
            var save_name=$("#name").val();
            var save_description=$("#describe").val();
            $.ajax({
                'url':"/dictionary/type/addDicType",
                'data':{
                    'code':save_code,
                    'name':save_name,
                    'description':save_description
                },
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                        alert("添加成功！");
                       $("#code").val("");
                       $("#name").val("");
                       $("#describe").val("");
                       $("#saveBtn").attr("disabled",true);
                    }else{
                        alert(data.message);
                    }
                }
            });
        });
    });
</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典类型</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" id="saveBtn" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="code" class="col-sm-2 control-label">编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="code" style="width: 200%;"><span id="codeHint" style="color:red"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="name" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="describe" style="width: 200%;"></textarea>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>