<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function(){
        function hasLength(str){
            if(str==null || str==""){
                return false;
            }else{
                return true;
            }
        }
          $("#saveValueBtn").click(function(){
                //点击保存
                var typeCode=$.trim($("#create-dicTypeCode").val());
                var value=$.trim($("#create-dicValue").val());
                var text=$.trim($("#create-text").val());
                var orderNo=$.trim($("#create-orderNo").val());
                if(!hasLength(typeCode)){
                    alert("字典类型不能为空！");
                    return;
                }
                if(!hasLength(value)){
                    alert("字典值不能为空！");
                    return;
                }

                var regExp=/^(([1-9]\d*)|0)$/
                if(!regExp.test(orderNo)){
                    //排序号不为正整数
                    alert("排序号必须为正整数！");
                    return;
                }
                //发送保存请求
                $.ajax({
                    'url':"/dictionary/value/saveHandler",
                    'data':{
                        'value':value,
                        'text':text,
                        'orderNo':orderNo,
                        'typeCode':typeCode
                    },
                    'type':"post",
                    'dataType':"json",
                    'success':function(data){
                        if(data.code==0){
                            alert(data.message);
                        }else{
                            alert("添加成功！");
                            $("#create-dicTypeCode").val("");
                            $("#create-dicValue").val("");
                            $("#create-text").val("");
                            $("#create-orderNo").val("");




                        }
                    }
                });
            });
    });

</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典值</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" id="saveValueBtn" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="create-dicTypeCode" class="col-sm-2 control-label">字典类型编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-dicTypeCode" style="width: 200%;">
				  <option value=""></option>
				  <c:forEach items="${dicTypeList}" var="dicType">

				  <option value="${dicType.code}">${dicType.name}</option>

				  </c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-dicValue" class="col-sm-2 control-label">字典值<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-dicValue" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-text" class="col-sm-2 control-label">文本</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-text" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-orderNo" class="col-sm-2 control-label">排序号</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-orderNo" style="width: 200%;">
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>