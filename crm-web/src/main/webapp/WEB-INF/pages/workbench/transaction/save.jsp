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
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="/jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
   <script type="text/javascript">
       $(document).ready(function(){
            $("#create-possibility").prop("readonly",true);
            $("#create-transactionStage").change(function(){
               var stageText=$("#create-transactionStage option:selected").text();
               $.ajax({
                    'url':"/tran/getPossibility",
                    'data':{
                        'stageText':stageText
                    },
                    'type':"post",
                    'dataType':"json",
                    'success':function(data){
                             if(data.code==1){
                                $("#create-possibility").val(data.object);
                             }else{
                                alert(data.message);
                             }
                    }
               });
            });

            //客户名称自动补全
            $("#create-accountName").typeahead({
                source:function(jquery,process){
                     //jquery为用户在文本框中输入的内容
                     //process为Controller层返回的值
                     $.ajax({
                        'url':"/customer/queryAllCustomerByName",
                        'data':{'customerName':jquery},
                        'type':"post",
                        'dataType':"json",
                        'success':function(data){
                              process(data);
                        }
                     });
                }
            });
            //查询市场活动来源
            var queryActivityByName=function(name,tbody){
                $.ajax({
                    'url':"/activity/queryActivityByName",
                    'data':{'name':name},
                    'type':"post",
                    'dataType':"json",
                    'success':function(data){
                        if(data.code==1){
                             var table=$("#"+tbody);
                              table.html("");
                             $.each(data.object,function(index,obj){
                                   var tr=$("<tr></tr>");
                                   var td1=$("<td></td>");
                                   var td2=$("<td></td>");
                                   var td3=$("<td></td>");
                                   var td4=$("<td></td>");
                                   var td5=$("<td></td>");
                                   td1.html("<input type='radio' aid='"+obj.id+"' aname='"+obj.name+"' waschecked=false />");
                                   td2.html(obj.name);
                                   td3.html(obj.startDate);
                                   td4.html(obj.endDate);
                                   td5.html(obj.owner);
                                   tr.append(td1);
                                   tr.append(td2);
                                   tr.append(td3);
                                   tr.append(td4);
                                   tr.append(td5);
                                   table.append(tr);

                             });
                        }else{
                            alert(data.message);
                        }
                    }
                });
            }
            //点击搜索市场活动来源
            $("#searchActivity").click(function(){
                $("#queryActivity").val("");
                queryActivityByName("","aTbody");
                $("#findMarketActivity").modal("show");

            });
            //根据关键字搜索市场活动来源
            $("#queryActivity").keyup(function(){
                var text=$(this).val();
                queryActivityByName(text,"aTbody");
            })
            //查询客户来源
            var queryContactsByName=function(name,tbody){
                 $.ajax({
                                    'url':"/contacts/queryContactsByName",
                                    'data':{'name':name},
                                    'type':"post",
                                    'dataType':"json",
                                    'success':function(data){
                                        if(data.code==1){
                                             var table=$("#"+tbody);
                                              table.html("");
                                             $.each(data.object,function(index,obj){
                                                   var tr=$("<tr></tr>");
                                                   var td1=$("<td></td>");
                                                   var td2=$("<td></td>");
                                                   var td3=$("<td></td>");
                                                   var td4=$("<td></td>");

                                                   td1.html("<input type='radio' cid='"+obj.id+"' cname='"+obj.fullname+"' waschecked=false/>");
                                                   td2.html(obj.fullname);
                                                   td3.html(obj.email);
                                                   td4.html(obj.mphone);

                                                   tr.append(td1);
                                                   tr.append(td2);
                                                   tr.append(td3);
                                                   tr.append(td4);

                                                   table.append(tr);

                                             });
                                        }else{
                                            alert(data.message);
                                        }
                                    }
                                });
            }

            queryContactsByName("","cTbody");
            //点击搜索客户来源
            $("#queryContacts").click(function(){
                $("#contactsName").val("")
               $("#findContacts").modal("show");
            });
            //根据关键字搜索客户来源
            $("#contactsName").keyup(function(){
                var text=$(this).val();
                queryContactsByName(text,"cTbody");

            });
            //选择来源文本框只读
            $("#create-activitySrc").prop("readonly",true);
            $("#create-contactsName").prop("readonly",true);
            //给市场活动单选按钮添加点击事件，实现单选框勾选和取消勾选以及市场活动名和id值填入市场活动文本框
        $("#aTbody").on("click",":radio",function(){
                        if($(this).attr("waschecked")=="false"){
                            $(this).prop("checked",true);
                            $(this).attr("waschecked","true");
                        }
                        else{
                            $(this).prop("checked",false);
                             $(this).attr("waschecked","false");
                             if($("#create-activitySrc").val()==$(this).attr("aName")){
                                $("#create-activitySrc").val("");
                                $("#create-activitySrc").attr("aid","");
                             }
                             }
                             var name=$(this).attr("aName");
                             var aid=$(this).attr("aid");
                             if($(this).prop("checked")==true){
                                $("#create-activitySrc").val(name);
                                $("#create-activitySrc").attr("aid",aid);
                             }});

            //给客户单选按钮添加点击事件，实现单选框勾选和取消勾选以及客户名和id值填入客户文本框
         $("#cTbody").on("click",":radio",function(){
                                if($(this).attr("waschecked")=="false"){
                                    $(this).prop("checked",true);
                                    $(this).attr("waschecked","true");
                                }
                                else{
                                    $(this).prop("checked",false);
                                     $(this).attr("waschecked","false");
                                     if($("#create-contactsName").val()==$(this).attr("cName")){
                                        $("#create-contactsName").val("");
                                        $("#create-contactsName").attr("cid","");
                                     }
                                     }
                                     var name=$(this).attr("cName");
                                     var cid=$(this).attr("cid");
                                     if($(this).prop("checked")==true){
                                        $("#create-contactsName").val(name);
                                        $("#create-contactsName").attr("cid",cid);
                                     }
                                });
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
        $("#create-expectedClosingDate").prop("readonly",true);
		$("#create-expectedClosingDate").datetimepicker(dateParam);
		$("#create-nextContactTime").prop("readonly",true);
		$("#create-nextContactTime").datetimepicker(dateParam);

            //点击添加按钮，进行交易的添加
            $("#saveTranBtn").click(function(){
                var owner=$("#create-transactionOwner").val();
                var money=$.trim($("#create-amountOfMoney").val());
                var name=$.trim($("#create-transactionName").val());
                var expectedDate=$.trim($("#create-expectedClosingDate").val());
                var cusName=$.trim($("#create-accountName").val());
                var stage=$("#create-transactionStage").val();
                var type=$("#create-transactionType").val();
                var source=$("#create-clueSource").val();
                var activityId=$("#create-activitySrc").attr("aid");
                var contactsId=$("#create-contactsName").attr("cid");
                var description=$("#create-describe").val();
                var contactSummary=$("#create-contactSummary").val();
                var nextContactTime=$("#create-nextContactTime").val();
                if(owner==null||owner==''){
                    alert("所有者不能为空！");
                    return;
                }
                var regx=/^(([1-9]\d*)|0|(\d+(\.\d{1,2})?))$/;
                if(!regx.test(money)){
                    alert("金额为非负整数或者1-2位小数");
                    return;
                }
                if(name==null || name==''){
                    alert("交易名称不能为空");
                    return;
                }
                if(expectedDate==null || expectedDate==""){
                    alert("预计成交时间不能为空");
                    return;
                }

                if(cusName==null || cusName==''){
                    alert("客户名称不能为空");
                    return;
                }

                if(stage==null || stage==''){
                    alert("阶段不能为空");
                    return;
                }
                $.ajax({
                    'url':"/tran/saveTranHandle",
                    'data':{
                        'owner':owner,
                        'money':money,
                        'name':name,
                        'expectedDate':expectedDate,
                        'stage':stage,
                        'type':type,
                        'source':source,
                        'activityId':activityId,
                        'contactsId':contactsId,
                        'description':description,
                        'contactSummary':contactSummary,
                        'nextContactTime':nextContactTime,
                        'cusName':cusName

                    },
                    'type':"post",
                    'dataType':"json",
                    'success':function(data){
                        if(data.code==1){
                            alert("添加成功");
                            window.location.href="/tran/index";
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

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="queryActivity" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="aTbody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="contactsName" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="cTbody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" id="saveTranBtn" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner">
				  <c:forEach items="${userList}" var="user">
                  	<option value="${user.id}">${user.name}</option>
                  </c:forEach>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-expectedClosingDate">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-accountName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage">
			  	<option></option>
			  	<c:forEach items="${stageList}" var="stage">
              	    <option value="${stage.id}">${stage.value}</option>
              	</c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType">
				  <option></option>
				<c:forEach items="${typeList}" var="type">
                    <option value="${type.id}">${type.value}</option>
                </c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource">
				  <option></option>
				  <c:forEach items="${sourceList}" var="source">
                  	<option value="${source.id}">${source.value}</option>
                  </c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="#" data-toggle="modal" id="searchActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activitySrc" aid="">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="#;" id="queryContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-contactsName" cid="">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-nextContactTime">
			</div>
		</div>
		
	</form>
</body>
</html>