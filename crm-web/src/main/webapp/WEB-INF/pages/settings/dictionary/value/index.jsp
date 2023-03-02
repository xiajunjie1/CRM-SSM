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
    function queryAllDicValue(){
        $.ajax({
            'url':"/dictionary/value/queryAllDicValue",
            'data':{},
            'type':"post",
            'dataType':"json",
            'success':function(data){
                if(data.code==1){
                $("#queryHint").html("");
                   var table= $("#dicTbody");
                   table.html("");
                   $.each(data.object,function(index,obj){
                              var tr=$("<tr></tr>");
                                      tr.addClass("active");
                                      var td1=$("<td></td>");
                                      var td2=$("<td></td>");
                                      var td3=$("<td></td>");
                                      var td4=$("<td></td>");
                                      var td5=$("<td></td>");
                                      td1.html("<input type='checkbox' value='"+obj.id+"'/>");
                                      td2.html(obj.value);
                                      td3.html(obj.text);
                                      td4.html(obj.orderNo);
                                      td5.html(obj.typeCode);
                                      tr.append(td1);
                                      tr.append(td2);
                                      tr.append(td3);
                                      tr.append(td4);
                                      tr.append(td5);
                                      table.append(tr);
                   });


                }else{
                    $("#queryHint").html(data.message);
                }
            },
            'beforeSend':function(){
                $("#queryHint").html("查询中，请稍等...<img src='/image/loading.gif' width=20px height=20px />");
            }
        });
    }
    //打开页面调用查询方法
    queryAllDicValue();

    $("#createBtn").click(function(){
        window.location.href="/dictionary/value/save";
    });
});
</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
		<div class="col-xs-3 col-xs-offset-4 " style="heigth:25px"><span id="queryHint" style="color:#f00"></span></div>
    			<hr />
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" id="createBtn"></span> 创建</button>
		  <button type="button" class="btn btn-default" onclick="window.location.href='edit.html'"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody id="dicTbody">
			<!--
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>m</td>
					<td>男</td>
					<td>1</td>
					<td>sex</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td>f</td>
					<td>女</td>
					<td>2</td>
					<td>sex</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>3</td>
					<td>1</td>
					<td>一级部门</td>
					<td>1</td>
					<td>orgType</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>4</td>
					<td>2</td>
					<td>二级部门</td>
					<td>2</td>
					<td>orgType</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>5</td>
					<td>3</td>
					<td>三级部门</td>
					<td>3</td>
					<td>orgType</td>
				</tr>
				-->
			</tbody>
		</table>
	</div>
	
</body>
</html>