<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="/jquery/bs_pagination-master/js/jquery.bs_pagination.min.js?id=10001"></script>
<script type="text/javascript" src="/jquery/bs_pagination-master/localization/en.js"></script>
<script type="text/javascript">

	$(function(){
		var queryTranSplitByCondition=function(pageNo,pageSize,tbody){
		    var owner=$.trim($("#query-owner").val());
		    var name=$.trim($("#query-name").val());
		    var customer=$.trim($("#query-customer").val());
		    var type=$("#query-type").val();
		    var stage=$("#query-stage").val();
		    var source=$("#query-source").val();
		    var contact=$.trim($("#query-contact").val());
		    var data={
		        'pageNo':pageNo,
		        'pageSize':pageSize,
		        'owner':owner,
		        'name':name,
		        'customer':customer,
		        'type':type,
		        'stage':stage,
		        'contact':contact
		    }
		    $.ajax({
                'url':"/tran/queryTranSplitByCondition",
                'data':JSON.stringify(data),
                'type':"post",
                'contentType':"application/json",
                'dataType':"json",
                'success':function(data){
                     $("#queryHint").html("");
                        if(data.code==1){
                           var table=$("#"+tbody);
                           table.html("");
                           $.each(data.transactionList,function(index,obj){
                              var tr=$("<tr></tr>");
                              tr.addClass("active");
                              var td1=$("<td></td>");
                              var td2=$("<td></td>");
                              var td3=$("<td></td>");
                              var td4=$("<td></td>");
                              var td5=$("<td></td>");
                              var td6=$("<td></td>");
                              var td7=$("<td></td>");
                              var td8=$("<td></td>");
                              td1.html("<input type='checkbox' value='"+obj.id+"' />");
                              td2.html("<a href='/tran/toDetails?id="+obj.id+"'>"+obj.name+"</a>");
                              td3.html(obj.customerId);
                              td4.html(obj.stage);
                              td5.html(obj.type);
                              td6.html(obj.owner);
                              td7.html(obj.source);
                              td8.html(obj.contactsId);
                              tr.append(td1);
                              tr.append(td2);
                              tr.append(td3);
                              tr.append(td4);
                              tr.append(td5);
                              tr.append(td6);
                              tr.append(td7);
                              tr.append(td8);
                              table.append(tr);
                              });
                              //构造分页页码
                              var totalCount=data.transactionCount;
                              var totalPages=1;
                              //alert(totalCount);
                              if(totalCount!=0&&totalCount%pageSize==0){
                                  totalPages=totalCount/pageSize;
                              }else{
                                totalPages=parseInt(totalCount/pageSize)+1;
                              }

                              $("#bs_pag").bs_pagination({
                                           'currentPage':pageNo,//当前页
                                           'rowsPerPage':pageSize,//每页显示条数
                                           'totalPages':totalPages, //总页数，必填
                                           'visiblePageLinks':5,//最多显示5个页码链接
                                           'showGoToPage':true,//显示跳转页面
                                           //'showRowsPerPage':true,//显示每页的记录数
                                           'showRowsInfo':true,
                                           'onChangePage':function(event,pageObj){
                                               //pageObj可以返回当前切换到的页面和当前页面显示条数
                                                   //根据切换的最新页码进行分页查询
                                                  // console.log("【onChange】"+pageObj)
                                                   queryTranSplitByCondition(pageObj.currentPage,pageObj.rowsPerPage,"tTbody");
                                           } //修改页面时触发函数，包括点击页面卡片，首页尾页等


                                       });
                             }
                             else{
                                $("#queryHint").html(data.message);
                             }

                },
                'beforeSend':function(){
                    $("#queryHint").html("查询中，请稍等...<img src='/image/loading.gif' width=20px height=20px />");
                }

		    });
		}


		queryTranSplitByCondition(1,2,"tTbody");



	});
	
</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	<div class="col-xs-3 col-xs-offset-4 " style="heigth:25px"><span id="queryHint" style="color:#f00"></span></div>
	<hr/>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="query-customer">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="query-stage">
					  	<option></option>
					  	<c:forEach items="${stageList}" var="stage">
					  	    <option value="${stage.id}">${stage.value}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="query-type">
					  	<option></option>
                        <c:forEach items="${typeList}" var="type">
					  	    <option value="${type.id}">${type.value}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="query-source">
						  <option></option>
						 <c:forEach items="${sourceList}" var="source">
                         	<option value="${source.id}">${source.value}</option>
                         </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="query-contact">
				    </div>
				  </div>
				  
				  <button type="button" id="queryTranBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='/tran/toTranSave';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='edit.html';"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tTbody">

					</tbody>
				</table>
			</div>
			
			<div id="bs_pag" style="height: 50px; position: relative;top: 20px;">

			
		</div>
		
	</div>
</body>
</html>