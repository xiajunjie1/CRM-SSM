<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
	    var dateParam={
                          		    'language':"zh-CN",//显示语言
                          		    'format':"yyyy-mm-dd",//显示格式
                          		    'minView':"month",//可选择的最小视图
                          		    'initialDate':new Date(), //初始化显示日期
                          		    'autoclose':true, //设置选择日期时间后是否自动关闭
                          		    'todayBtn':true, //设置“今天”按钮
                          		    'clearBtn':true //清空日期的功能
                          		    //将clear改为清空，修改该插件中的bootstrap-datetimepicker.js文件或者修改bootstrap-datetimepicker.zh-CN.js
                          		};
		$("#expectedClosingDate").datetimepicker(dateParam);
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
        var queryActivityByNameAndCid=function(name,cid,tbody){
            $.ajax({
                'url':"/activity/queryActivityByNameInCid",
                'data':{
                    'name':name,
                    'cid':cid
                },
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                        var table=$("#"+tbody);
                        $.each(data.object,function(index,obj){
                            var tr=$("<tr></tr>");
                              var td1=$("<td></td>");
                              var td2=$("<td></td>");
                              var td3=$("<td></td>");
                              var td4=$("<td></td>");
                              var td5=$("<td></td>");

                              td2.html(obj.name);
                              td3.html(obj.startDate);
                              td4.html(obj.endDate);
                              td5.html(obj.owner);
                              td1.html("<input type='radio' aName='"+obj.name+"' value='"+obj.id+"' waschecked=false />");
                              tr.append(td1);
                              tr.append(td2);
                              tr.append(td3);
                              tr.append(td4);
                              tr.append(td5);
                              table.append(tr);
                        });
                    }
                }
            });
        }
		$("#searchActivity").click(function(){
            $("#searchActivityModal").modal("show");
            var cid='${clue.id}';
            queryActivityByNameAndCid("",cid,"aTbody");
            $("#aTbody").on("click",":radio",function(){
                if($(this).attr("waschecked")=="false"){
                    $(this).prop("checked",true);
                    $(this).attr("waschecked","true");
                }
                else{
                    $(this).prop("checked",false);
                     $(this).attr("waschecked","false");
                     if($("#activity").val()==$(this).attr("aName")){
                        $("#activity").val("");
                        $("#activity").attr("aid","");
                     }
                }

                var name=$(this).attr("aName");
                var id=$(this).attr("aid");
               if($(this).prop("checked")==true){
                $("#activity").val(name);
                $("#activity").attr("aid",id);
                }

            });

		});
        $("#clueConvertBtn").click(function(){
            if(confirm("确认转换线索吗？")){
                var clueId='${clue.id}';
                var isTran="false";
                var money="";
                var name="";
                var expectedDate="";
                var stage="";
                var activityId="";
                if($("#isCreateTransaction").prop("checked")==true){
                    isTran="true";
                    money=$.trim($("#amountOfMoney").val());
                    name=$.trim($("#tradeName").val());
                    expectedDate=$("#expectedClosingDate").val()
                    stage=$("#stage").val();
                    activityId=$("#activity").attr("aid");
                }
                var data={
                'clueId':clueId,
                 'isTran':isTran,
                 'money':money,
                 'name':name,
                 'expectedDate':expectedDate,
                 'stage':stage,
                 'activityId':activityId
                }
                $.ajax({
                    'url':"/clue/clueConvertHandle",
                    'data':JSON.stringify(data),
                    'type':"post",
                    'contentType':"application/json", //告知后台传入数据为JSON序列化
                    'dataType':"json",
                    'success':function(data){
                        if(data.code==1){
                            alert("转换成功");
                            window.location.href="/clue/index";
                           }else{
                            alert(data.message);
                        }
                    }
                });
            }
        })
	});


</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="aTbody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${clue.fullname} ${clue.appellation}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${clue.fullname} ${clue.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="${clue.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control" id="expectedClosingDate" readonly="readonly">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
		    	<option></option>
		    	<c:forEach items="${dvList}" var="dv">
		    	    <option value="${dv.id}">${dv.value}</option>
		    	</c:forEach>

		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="searchActivity" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activity" aid="" placeholder="点击上面搜索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${clue.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" value="转换" id="clueConvertBtn">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>