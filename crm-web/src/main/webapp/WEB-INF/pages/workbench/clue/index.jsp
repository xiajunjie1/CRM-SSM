<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bootstrap-selector/css/bootstrap-select.min.css" type="text/css" rel="stylesheet" />
<link href="/jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-selector/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-selector/js/i18n/defaults-zh_CN.js"></script>
<script type="text/javascript" src="/jquery/bs_pagination-master/js/jquery.bs_pagination.min.js?id=2"></script>
<script type="text/javascript" src="/jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
	var dateParam={
    		    'language':"zh-CN",//显示语言
    		    'dateFormat':"yyyy-mm-dd",//显示格式
    		    'timeFormat':"HH:mm:ss",
    		    'minView':0,//可选择的最小视图
    		    'initialDate':new Date(), //初始化显示日期
    		    'autoclose':true, //设置选择日期时间后是否自动关闭
    		    'todayBtn':true, //设置“今天”按钮
    		    'clearBtn':true //清空日期的功能
    		    //将clear改为清空，修改该插件中的bootstrap-datetimepicker.js文件或者修改bootstrap-datetimepicker.zh-CN.js
    		};
		$("#create-nextContactTime").datetimepicker(dateParam);
		$("#create-nextContactTime").attr("readOnly",true);

        var queryClueSplitByCondition=function(pageNo,pageSize){
            var fullname=$.trim($("#queryFullname").val());
            var company=$.trim($("#queryCompany").val());
            var phone=$.trim($("#queryPhone").val());
            var source=$("#querySource").val();
            var owner=$("#queryOwner").val();
            var mphone=$.trim($("#queryMphone").val());
            var state =$("#queryState").val();
            var data={
                                         'pageNo':pageNo,
                                         'pageSize':pageSize,
                                         'fullname':fullname,
                                         'company':company,
                                         'phone':phone,
                                         'source':source,
                                         'owner':owner,
                                         'mphone':mphone,
                                         'state':state
                                     };
            $.ajax({
                'url':"/clue/queryClueSplitByCondition",
                'data':JSON.stringify(data), //将JSON进行序列化，便于SpringMVC将其转为Map
                'type':"post",
                'contentType':"application/json", //告知后台传入数据为JSON序列化
                'dataType':"json",
                'success':function(data){
                $("#queryHint").html("");
                    if(data.code==1){
                        var table=$("#clueTbody");
                        table.html("");
                        $.each(data.clueList,function(index,obj){
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
                        td2.html("<a href='/clue/clueDetails?id="+obj.id+"'>"+obj.fullname+obj.appellation+"</a>");

                        td3.html(obj.company);
                        td4.html(obj.phone);
                        td5.html(obj.mphone);
                        td6.html(obj.source);
                        td7.html(obj.owner);
                        td8.html(obj.state);
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
                         var totalPages=1;
                         var totalCount=data.totalCount;
                         if(totalCount!=0 && totalCount%pageSize==0){
                            totalPages=totalCount/pageSize;
                         }else{
                            totalPages=parseInt(totalCount/pageSize)+1;
                         }
                         console.log("【clue】"+totalPages+"、"+totalCount);

                        //构建分页组件
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

                                       queryClueSplitByCondition(pageObj.currentPage,pageObj.rowsPerPage);
                               } //修改页面时触发函数，包括点击页面卡片，首页尾页等


                               });
                    }else{
                        alert(data.message);
                    }
                },
                'beforeSend':function(){
                    $("#queryHint").html("查询中，请稍等...<img src='/image/loading.gif' width=20px height=20px />");
                }
            });
        }

        queryClueSplitByCondition(1,2);

		$("#createClueBtn").click(function(){
		$("#createClueForm").get(0).reset();//清空form
		    $("#createClueModal").modal("show");
		    $("#saveClueBtn").click(function(){//点击保存
		        	var clueOwner=$("#create-clueOwner").val();
                		var appellation=$("#create-call").val();
                		var name=$.trim($("#create-surname").val());
                		var company=$.trim($("#create-company").val());
                		var job=$.trim($("#create-job").val());
                		var email=$.trim($("#create-email").val());
                		var phone=$.trim($("#create-phone").val());
                		var website=$.trim($("#create-website").val());
                		var mphone=$.trim($("#create-mphone").val());
                		var state=$("#create-status").val();
                		var source=$("#create-source").val();
                		var nextContactTime=$("#create-nextContactTime").val();
                		var description=$.trim($("#create-describe").val());
                		var contactSummary=$.trim($("#create-contactSummary").val());
                		var address=$.trim($("#create-address").val());
                		if(company==null || company==""){
                		    alert("公司不能为空");
                		    return;
                		}
                		if(name==null || name==""){
                		    alert("姓名不能为空");
                		    return;
                		}
                		var regExp=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                		if(email!=null && email!=""){
                		    if(!regExp.test(email)){
                		        alert("邮箱不符合格式");
                		        return ;
                		    }
                		}
                		$.ajax({
                		    'url':"/clue/addClue",
                		    'data':{
                                'fullname':name,
                                'appellation':appellation,
                                'owner':clueOwner,
                                'company':company,
                                'job':job,
                                'email':email,
                                'phone':phone,
                                'website':website,
                                'mphone':mphone,
                                'state':state,
                                'source':source,
                                'nextContactTime':nextContactTime,
                                'description':description,
                                'contactSummary':contactSummary,
                                'address':address
                		    },
                		    'type':"post",
                		    'dataType':"json",
                		    'success':function(data){
                                if(data.code==1){
                                    alert("添加成功！");
                                    $("#createClueModal").modal("hide");
                                     $("#queryFullname").val("")
                                    $("#queryCompany").val("");
                                     $("#queryPhone").val("");
                                     $("#querySource").val("");
                                     $("#queryOwner").val("");
                                     $("#queryMphone").val("");
                                     $("#queryState").val("");
                                    queryClueSplitByCondition(1,$("#bs_pag").bs_pagination('getOption','rowsPerPage'));
                                }else{
                                    alert(data.message);
                                     $("#createClueModal").modal("show");

                                }
                		    }
                		});
		    });
		});

        //为查询按钮绑定查询事件
        $("#queryClueBtn").click(function(){
            //alert("test!");
            queryClueSplitByCondition(1,$("#bs_pag").bs_pagination('getOption','rowsPerPage'));
        });

		
	});
	
</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="createClueForm" role="form">
					
						<div class="form-group">
							<label for="create-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control selectpicker" data-live-search="true" id="create-clueOwner">
			                        <c:forEach items="${ulist}" var="user">
			                            <option value="${user.id}">${user.name}</option>
			                        </c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
								  <option></option>
								  <c:forEach items="${appeList}" var="appe">
								    <option value="${appe.id}">${appe.value}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-status">
								  <option></option>
								    <c:forEach items="${csList}" var="clueState">
								        <option value="${clueState.id}">${clueState.value}</option>
								    </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
								<c:forEach items="${sList}" var="source">
                                	<option value="${source.id}">${source.value}</option>
                                </c:forEach>
								</select>
							</div>
							<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                            <div class="col-sm-10" style="width: 300px;">
                              <input type="text" class="form-control" id="create-nextContactTime">
                            </div>
							</div>







						

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>

						</div>
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveClueBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueOwner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="动力节点">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call">
								  <option></option>
								  <option selected>先生</option>
								  <option>夫人</option>
								  <option>女士</option>
								  <option>博士</option>
								  <option>教授</option>
								</select>
							</div>
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-surname" value="李四">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status">
								  <option></option>
								  <option>试图联系</option>
								  <option>将来联系</option>
								  <option selected>已联系</option>
								  <option>虚假线索</option>
								  <option>丢失线索</option>
								  <option>未联系</option>
								  <option>需要条件</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								  <option></option>
								  <option selected>广告</option>
								  <option>推销电话</option>
								  <option>员工介绍</option>
								  <option>外部介绍</option>
								  <option>在线商场</option>
								  <option>合作伙伴</option>
								  <option>公开媒介</option>
								  <option>销售邮件</option>
								  <option>合作伙伴研讨会</option>
								  <option>内部研讨会</option>
								  <option>交易会</option>
								  <option>web下载</option>
								  <option>web调研</option>
								  <option>聊天</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary">这个线索即将被转换</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="queryFullname">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="queryCompany">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="queryPhone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="querySource">
					  	  <option></option>
					  	  <c:forEach items="${sList}" var="source">
					  	  <option value="${source.id}">${source.value}</option>

						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <select id="queryOwner" class="form-control">
				      <option></option>
				       <c:forEach items="${ulist}" var="user">
                      	<option value="${user.id}">${user.name}</option>
                      	</c:forEach>
				      </select>
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="queryMphone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="queryState">
					  	<option></option>
					  	<c:forEach items="${csList}" var="clueState">
					  	    <option value="${clueState.id}">${clueState.value}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button"  class="btn btn-default" id="queryClueBtn">查询</button>
				  
				</form>
			</div>
			<div class="col-xs-3 col-xs-offset-4 " style="heigth:25px"><span id="queryHint" style="color:#f00"></span></div>
            <hr />
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createClueBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editClueModal"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" /></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="clueTbody">
						<!--
						<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">李四先生</a></td>
							<td>动力节点</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">李四先生</a></td>
                            <td>动力节点</td>
                            <td>010-84846003</td>
                            <td>12345678901</td>
                            <td>广告</td>
                            <td>zhangsan</td>
                            <td>已联系</td>
                        </tr>
                        -->
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 60px;">
				<!--
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
				-->
				<div id="bs_pag"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>