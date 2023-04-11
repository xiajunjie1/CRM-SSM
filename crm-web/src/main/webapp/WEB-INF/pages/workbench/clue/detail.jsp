<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<style type="text/css">
    b{
        height:30px;
    }
</style>
<script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});

		$("#saveRemarkBtn").click(function(){
            var noteContent=$("#remark").val();
            if(noteContent==null || noteContent==""){
                alert("请输入备注内容");
                return;
            }
            var cid=$("#cid").val();
            if(cid==null || cid==""){
                alert("未获取到当前线索id，请联系管理员...");
                return;
            }
            $.ajax({
                'url':"/clueRemark/addClueRemark",
                'data':{
                    'noteContent':noteContent,
                    'clueId':cid
                },
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                        alert("添加成功！");
                        window.location.href="/clue/clueDetails?id="+cid;
                    }else{
                        alert(data.message);
                    }
                }
            });
		});

		$("#remarkDivList").on("click","a[name='deleteC']",function(){
                var id=$(this).attr("remarkId");
                $.ajax({
                    'url':"/clueRemark/removeClueRemarkById",
                    'data':{'id':id},
                    'type':"post",
                    'dataType':"json",
                    'success':function(data){
                        if(data.code==1){
                            alert("删除成功！");
                            $("div[itemId='"+id+"']").remove();
                        }else{
                            alert(data.message);
                        }
                    }

                 });
		});

		var queryActivityByNameCid=function(name,cid,tbody){
		       $.ajax({
            	'url':"/activity/queryActivityByNameCid",
            	 'data':{
            		     'name':name,
            		     'cid':cid
            		     },
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
                               td1.html("<input type='checkbox' value='"+obj.id+"'/>")
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

		//点击关联市场活动
		$("#connectActivity").click(function(){
		    $("#bundModal").modal("show");
		   var cid=$("#cid").val();
		   $("#aName").val("");
		    queryActivityByNameCid("",cid,"aTbody");

		});

		//关联市场活动搜索框，添加按键弹起事件
		$("#aName").keyup(function(){
		    var name=$(this).val();
		    var cid=$("#cid").val();
		    queryActivityByNameCid(name,cid,"aTbody");

		});
        //给关联按钮绑定点击事件
        $("#saveRelation").click(function(){
              var items= $("#aTbody input[type='checkbox']:checked");
              if(items.size()==0){
                alert("请选择需要关联的市场活动");
                return;
              }
              var params="";
              var cid=$("#cid").val();
              $.each(items,function(index,obj){
                    params+="ids="+this.value+"&";
              });
            params+="cid="+cid;
            console.log("【关联】"+params);
            $.ajax({
              'url':"/clue/clueActivityRelation/addRelation",
              'data':params,
              'type':"post",
              'dataType':"json",
              'success':function(data){
                    if(data.code==1){
                        alert("关联成功！");
                        $.each(data.object,function(index,obj){
                             //console.log("【关联的市场活动】"+obj.name)
                             var table=$("#relateTbody");
                             var tr=$("<tr trId='"+obj.id+"'></tr>");
                              var td1=$("<td></td>");
                              var td2=$("<td></td>");
                              var td3=$("<td></td>");
                              var td4=$("<td></td>");
                              var td5=$("<td></td>");

                              td1.html(obj.name);
                              td2.html(obj.startDate);
                              td3.html(obj.endDate);
                              td4.html(obj.owner);
                              td5.html("<a aId='"+obj.id+"' href='#'  style='text-decoration: none;'><span class='glyphicon glyphicon-remove'></span>解除关联</a>")
                              tr.append(td1);
                              tr.append(td2);
                              tr.append(td3);
                              tr.append(td4);
                              tr.append(td5);
                              table.append(tr);
                        });
                        $("#bundModal").modal("hide");

                    }else{
                        alert(data.message);
                         $("#bundModal").modal("show");
                    }
              }
                    });
        });

        $("#relateTbody").on("click","a",function(){
            var activityId=$(this).attr("aId");
            var clueId=$("#cid").val();
            if(activityId==null || activityId==""){
                alert("未获取到市场活动id");
                return;
            }
            if(clueId==null || clueId==""){
                alert("未获取到线索id");
                return;
            }
            $.ajax({
                'url':"/clue/clueActivityRelation/removeRelationByAidCid",
                'data':{
                    'activityId':activityId,
                    'clueId':clueId
                },
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                        alert("关联解除成功");
                        $("#relateTbody tr[trId='"+activityId+"']").remove();
                    }else{
                        alert(data.message);
                    }
                }
            });
        });
        //点击转换
        $("#convertBtn").click(function(){
            var cid=$("#cid").val();
            window.location.href="/clue/toConvert?cid="+cid;
        });
	});
	
</script>

</head>
<body>

	<!-- 关联市场活动的模态窗口 -->
	<div class="modal fade" id="bundModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">关联市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="aName" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td><input type="checkbox"/></td>
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
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="saveRelation">关联</button>
				</div>
			</div>
		</div>
	</div>


	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${clue.fullname} ${clue.appellation} <small>${clue.company}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" id="convertBtn"><span class="glyphicon glyphicon-retweet"></span> 转换</button>
			
		</div>
	</div>
	<input type="hidden" value="${clue.id}" id="cid" />
	<br/>
	<br/>
	<br/>

	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.fullname} ${clue.appellation}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.owner}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">公司</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.company}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">职位</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.job}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px;">邮箱</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.email}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; ">公司座机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;height:20px"><b>${clue.phone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">公司网站</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;height:20px"><b>${clue.website}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.mphone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">线索状态</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.state}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">线索来源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.source}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.contactSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 100px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                    ${clue.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 40px; left: 40px;" id="remarkDivList">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
		<c:forEach items="${clueRemarks}" var="remark">
		<div class="remarkDiv" itemId="${remark.id}" style="height: 60px;">
			<img title="${remark.createBy}" src="/image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>${remark.noteContent}</h5>
				<font color="gray">线索</font> <font color="gray">-</font> <b>${clue.fullname} ${clue.appellation}</b> <small style="color: gray;">
				${remark.editFlag=="0"?remark.createTime:remark.editTime}由${remark.editFlag=="0"?remark.createBy:remark.editBy}${remark.editFlag=="0"?"创建":"编辑"}</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					 <a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" remarkId="${remark.id}" name="deleteC"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		</c:forEach>
		<!-- 备注2
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="/image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-动力节点</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		-->
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button id="saveRemarkBtn" type="button" class="btn btn-primary">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 市场活动 -->
	<div>
		<div style="position: relative; top: 60px; left: 40px;">
			<div class="page-header">
				<h4>市场活动</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>所有者</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="relateTbody">
					    <c:forEach items="${activityList}" var="activity">
					        <tr trId="${activity.id}">
                            	<td>${activity.name}</td>
                            	<td>${activity.startDate}</td>
                            	<td>${activity.endDate}</td>
                            	<td>${activity.owner}</td>
                            	<td><a aId="${activity.id}"  style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
                            </tr>
					    </c:forEach>

					</tbody>
				</table>
			</div>
			
			<div>
				<a style="text-decoration: none;" href="#" id="connectActivity"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
			</div>
		</div>
	</div>
	
	
	<div style="height: 200px;"></div>
</body>
</html>