<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(function(){
    //查询字典值

        //给创建按钮添加点击事件
        $("#createTypeBtn").click(function(){
            window.location.href="/dictionary/type/save";
        });
    });
</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" id="createTypeBtn" class="btn btn-primary" ><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" id="editTypeBtn" class="btn btn-default" onclick="window.location.href='edit.html'"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" id="deleteTypeBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${dicTypeList}" var="dicType">
				<tr class="active">
					<td><input type="checkbox" value="${dicType.code}" /></td>
					<td>${dicType.code}</td>
					<td>${dicType.name}</td>
					<td>${dicType.description}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>