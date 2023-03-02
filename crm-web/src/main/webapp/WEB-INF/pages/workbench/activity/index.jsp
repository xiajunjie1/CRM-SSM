<%@page pageEncoding="UTF-8" %>
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
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js?id=1"></script>
<script type="text/javascript" src="/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js?id=1"></script>
<script type="text/javascript" src="/jquery/bootstrap-selector/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="/jquery/bootstrap-selector/js/i18n/defaults-zh_CN.js"></script>
<script type="text/javascript" src="/jquery/bs_pagination-master/js/jquery.bs_pagination.min.js?id=1"></script>
<script type="text/javascript" src="/jquery/bs_pagination-master/localization/en.js"></script>
<script type="text/javascript">

	$(function(){
		//进行日历插件的引用
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
		$("#create-startTime").datetimepicker(dateParam);
		$("#create-endTime").datetimepicker(dateParam);

		$("#query-startDate").datetimepicker(dateParam);
		$("#query-endDate").datetimepicker(dateParam);

		$("#edit-startTime").datetimepicker(dateParam);
		$("#edit-endTime").datetimepicker(dateParam);

		$("#createActivity").click(function(){//打开模态窗口前，先要清空模态窗口的内容
		    $("#createActivityForm").get(0).reset();//清空form
		    $("#hint").hide();//隐藏提示栏
		    $("#createActivityModal").modal("show")
		});
		        function queryActivitySplitByCondition(pageNo,pageSize){
                    var queryName=$("#query-name").val();
                            		var queryOwner=$("#query-owner").val();
                            		var queryStartDate=$("#query-startDate").val();
                            		var queryEndDate=$("#query-endDate").val();
                    $.ajax({
                                                url:'/activity/listSplitByCondition',
                                                data:{
                                                    'name':queryName,
                                                    'owner':queryOwner,
                                                    'startDate':queryStartDate,
                                                    'endDate':queryEndDate,
                                                    'pageNo':pageNo,
                                                    'pageSize':pageSize
                                                },
                                                type:'post',
                                                dataType:'json',
                                                success:function(data){
                                                    //遍历获取到的市场活动列表
                                                     var table=$("#aTbody");
                                                     table.html("");
                                                   $.each(data.activityList,function(index,obj){

                                                        var tr=$("<tr></tr>");
                                                        tr.addClass("active");
                                                        var td1=$("<td></td>");
                                                        var td2=$("<td></td>");
                                                        var td3=$("<td></td>");
                                                        var td4=$("<td></td>");
                                                        var td5=$("<td></td>");
                                                        td1.html("<input type='checkbox' value='"+obj.id+"' />");
                                                        td2.html("<a href='/activity/activityDetails?id="+obj.id+"'>"+obj.name+"</a>");
                                                        td3.html(obj.owner);
                                                        td4.html(obj.startDate);
                                                        td5.html(obj.endDate);
                                                        tr.append(td1);
                                                        tr.append(td2);
                                                        tr.append(td3);
                                                        tr.append(td4);
                                                        tr.append(td5);
                                                        table.append(tr);
                                                   });
                                                    //$("#totalRecordsB").html(data.totalCount);
                                                    $("#queryHint").html("");
                                                    //清空全选按钮选中状态
                                                    $("#checkAll").prop("checked",false);
                                                    //根据查询到的数据构建分页插件
                                                    var totalPages=1;
                                                    if(data.totalCount!=0&&data.totalCount%pageSize==0){
                                                        //记录数不为0，且可以整除pageSize
                                                        totalPages=data.totalCount/pageSize;
                                                    }else{
                                                        totalPages=parseInt(data.totalCount/pageSize)+1;
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
                                                                console.log("【onChange】"+pageObj)
                                                                queryActivitySplitByCondition(pageObj.currentPage,pageObj.rowsPerPage);
                                                        } //修改页面时触发函数，包括点击页面卡片，首页尾页等


                                                    });
                                                },
                                                beforeSend:function(){
                                                    $("#queryHint").html("查询中，请稍等...<img src='/image/loading.gif' width=20px height=20px />")
                                                }
                                		});
                }

		//点击保存事件
		$("#saveActivityBtn").click(function(){
		    var owner=$("#create-marketActivityOwner").val();
		    var name=$.trim($("#create-marketActivityName").val());
		    var startDate=$("#create-startTime").val();
		    var endDate=$("#create-endTime").val();
		    var cost=$.trim($("#create-cost").val());
		    var description=$("#create-describe").val();
            if(owner==""){
                $("#hint").removeClass();
                $("#hint").show();
                $("#hint").addClass("alert alert-danger");
                $("#hint").html("所属者不能为空<button type='button' id='hideBtn' class='close'>&times;</button>");
                 $("#hideBtn").click(function(){
                                        $("#hint").hide();
                                    });
                return;
            }
            if(name==""){
                   $("#hint").removeClass();
                   $("#hint").show();
                   $("#hint").addClass("alert alert-danger");
                   $("#hint").html("市场活动名称不能为空<button type='button' id='hideBtn' class='close'>&times;</button>");
                    $("#hideBtn").click(function(){
                        $("#hint").hide();
                    });
                    return;
                        }

            if(startDate!=""&&endDate!=""){
                if(startDate>endDate){
                     $("#hint").removeClass();
                                       $("#hint").show();
                                       $("#hint").addClass("alert alert-danger");
                                       $("#hint").html("开始时间不能在结束时间之后<button type='button' id='hideBtn' class='close'>&times;</button>");
                                        $("#hideBtn").click(function(){
                                            $("#hint").hide();
                                        });
                                        return;
                }
            }

            var regExp=/^(([1-9]\d*)|0)$/;//非负整数
            if(!regExp.test(cost)){
                //成本不为非负整数
                 $("#hint").removeClass();
                  $("#hint").show();
                  $("#hint").addClass("alert alert-danger");
                   $("#hint").html("成本只能为非负整数<button type='button' id='hideBtn' class='close'>&times;</button>");
                   $("#hideBtn").click(function(){
                   $("#hint").hide();
                    });
                    return;
            }
            //新增市场活动请求
		    $.ajax({
		        url:'/activity/add',
		        data:{
		            'owner':owner,
		            'name':name,
		            'startDate':startDate,
		            'endDate':endDate,
		            'cost':cost,
		            'description':description
		        },
		        type:'post',
		        dataType:'json',
		        success:function(data){

                    if(data.code==1){
                        //新增成功，关闭模态窗口
                        $("#createActivityModal").modal("hide");
                        //刷新主页面
                         $("#query-name").val("");
                         $("#query-owner").val("");
                         $("#query-startDate").val("");
                         $("#query-endDate").val("");
                        queryActivitySplitByCondition(1,$("#bs_pag").bs_pagination('getOption','rowsPerPage'));

                    }else{
                        $("#hint").removeClass();
                                          $("#hint").show();
                                          $("#hint").addClass("alert alert-danger");
                                          if(code.message==null || code.message==""){
                                            code.message="服务器忙，请稍后重试";
                                          }
                                           $("#hint").html(code.message+"<button type='button' id='hideBtn' class='close'>&times;</button>");
                                           $("#hideBtn").click(function(){
                                           $("#hint").hide();
                                            });
                                            //模态窗口不关闭
                                            $("#createActivityModal").modal("show");
                    }
		        },
		        beforeSend:function(){
		        $("#hint").removeClass();
		            $("#hint").show();
		            $("#hint").addClass("alert alert-info");
		            $("#html").html("正在添加市场活动<img src='/image/loading.gif' width=20px height=20px />")
		        }
		    });
		});


		 //一打开该页面，向后台发送请求，分页查询第一页的数据

                queryActivitySplitByCondition(1,2);

          //为查询按钮添加事件
          $("#queryActivityByCondition").click(function(){
                //利用bs_pagination提供的函数获取分页参数，比如每页显示行数
                console.log($("#bs_pag").bs_pagination('getOption','rowsPerPage'));
                console.log("#######"+$("#rows_per_page_bs_pag").val());
                queryActivitySplitByCondition(1,$("#rows_per_page_bs_pag").val());
          })

          //点击checkbox实现全选功能，注意，其中有些checkbox是通过js查询到结果后动态生成的
          //对于jQuery添加事件，一般有两种方式，方式1为$("元素").函数名(...)，该方法只能对固有元素进行事件绑定，固有元素即一开始就写在页面上的元素，而不是动态生成的元素
          //使用jQuery的on函数给元素加事件，它可以给动态生成的元素添加事件，父选择器，父元素必须为固有元素，用法为 父选择器.on("事件类型",子选择器,函数);

          //点击全选按钮，选中所有checkbox，取消全选，所有checkbox按钮取消
          $("#checkAll").click(function(){
            //点击全选按钮后，将全选按钮本身的checked状态赋值给tbody下所有checkbox上，即全选按钮被选中了，其它checkbox则被选中，全选按钮未选中，其它checkbox也不会被选中
            $("#aTbody input[type='checkbox']").prop("checked",this.checked);
          });

          //使用on给动态生成的checkbox添加点击事件
          $("#aTbody").on("click","input[type='checkbox']",function(){
                //alert($("#aTbody input[type='checkbox']").size()+"  "+$("#aTbody input[type='checkbox']:checked").size())
                if($("#aTbody input[type='checkbox']").size()==$("#aTbody input[type='checkbox']:checked").size()){
                    //如果所有tbody中的checkbox和所有tbody中已选中的checkbox数量相等，那么设置所有的checkbox为选中
                    //alert("相等");
                    $("#checkAll").prop("checked",true);
                }else{
                    //有未选中的checkbox，取消全选
                    $("#checkAll").prop("checked",false);

                }
          });

          //点击删除按钮，绑定相应的事件
          $("#deleteActivityBtn").click(function(){
           var checklist= $("#aTbody input[type='checkbox']:checked");
           if(checklist.size()==0){
            //未勾选删除项
            alert("请勾选删除项");
            return;
           }
           if(window.confirm("确认删除吗？")){
            //点击确认，返回为true
            var ids="";
            //将参数拼成id=xxx&id=xxx...&的形式
            $.each(checklist,function(){
                ids+="id="+this.value+"&"
            });
            //去掉最后一个&
            ids=ids.substr(0,ids.length-1);//param1:开始下标，param2:截取长度
            console.log(ids);
            $.ajax({
                'url':"/activity/removeActivities",
                'data':ids,
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    //请求收到后台响应
                    if(data.code==0){
                    //删除失败
                    alert(data.message);
                    return;
                    }
                    alert(data.message);
                    //刷新主页面
                    queryActivitySplitByCondition(1,$("#bs_pag").bs_pagination('getOption','rowsPerPage'));
                }
            });
           }
          });

          //点击修改按钮
        $("#editActivityBtn").click(function(){
            var check=$("#aTbody input[type='checkbox']:checked");
            if(check.size()==0){
                alert("请选择需要修改的市场活动");
                return;
            }
            if(check.size()>1){
                alert("只能选择一条修改的市场活动");
                return;
            }
            //查询选中的市场活动数据
            console.log(check.get(0).value);
            $.ajax({
                'url':"/activity/queryActivityById",
                'data':"id="+check.get(0).value,
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){//进行数据回填
                    $("#edit-ActivityOwner").val(data.object.owner);//浏览器会自动匹配
                    $("#edit-ActivityName").val(data.object.name);
                    $("#edit-startTime").val(data.object.startDate);
                    $("#edit-endTime").val(data.object.endDate);
                    $("#edit-describe").val(data.object.description);
                    $("#edit-cost").val(data.object.cost);
                    $("#edit-id").val(data.object.id);
                    $("#editActivityModal").modal("show");
                    }else{
                        alert(data.message);
                    }

                }
            });

        });

        //点击更新按钮，对更新的数据进行保存
        $("#saveEditActivity").click(function(){
            var owner=$("#edit-ActivityOwner").val();
            var name=$("#edit-ActivityName").val();
            var startDate=$("#edit-startTime").val();
            var endDate=$("#edit-endTime").val();
            var description=$("#edit-describe").val();
            var cost=$("#edit-cost").val();
            var id=$("#edit-id").val();
            if(id==""){
                 $("#editHint").removeClass();
                                            $("#editHint").show();
                                            $("#editHint").addClass("alert alert-danger");
                                            $("#editHint").html("当前市场活动id缺失<button type='button' id='hideBtn' class='close'>&times;</button>");
                                             $("#hideBtn").click(function(){
                                                                    $("#editHint").hide();
                                                                });
                                            return;
            }
            if(owner==""){
                            $("#editHint").removeClass();
                            $("#editHint").show();
                            $("#editHint").addClass("alert alert-danger");
                            $("#editHint").html("所属者不能为空<button type='button' id='hideBtn' class='close'>&times;</button>");
                             $("#hideBtn").click(function(){
                                                    $("#editHint").hide();
                                                });
                            return;
                        }
            if(name==""){
             $("#editHint").removeClass();
                                        $("#editHint").show();
                                        $("#editHint").addClass("alert alert-danger");
                                        $("#editHint").html("市场活动名称不能为空<button type='button' id='hideBtn' class='close'>&times;</button>");
                                         $("#hideBtn").click(function(){
                                                                $("#editHint").hide();
                                                            });
                                        return;
            }

            if(startDate!=null && endDate!=null){
                if(startDate>endDate){
                     $("#editHint").removeClass();
                                                $("#editHint").show();
                                                $("#editHint").addClass("alert alert-danger");
                                                $("#editHint").html("开始日期不可以大于结束日期<button type='button' id='hideBtn' class='close'>&times;</button>");
                                                 $("#hideBtn").click(function(){
                                                                        $("#editHint").hide();
                                                                    });
                                                return;
                }
            }
            var regExp=/^(([1-9]\d*)|0)$/;
            if(!regExp.test(cost)){
                 $("#editHint").removeClass();
                                            $("#editHint").show();
                                            $("#editHint").addClass("alert alert-danger");
                                            $("#editHint").html("成本必须为非负整数<button type='button' id='hideBtn' class='close'>&times;</button>");
                                             $("#hideBtn").click(function(){
                                                                    $("#editHint").hide();
                                                                });
                                            return;
            }
            $.ajax({
                'url':"/activity/editActivityById",
                'data':{
                    'id':id,
                    'owner':owner,
                    'name':name,
                    'startDate':startDate,
                    'endDate':endDate,
                    'cost':cost,
                    'description':description
                },
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                        alert(data.message);
                        $("#editActivityModal").modal("hide");
                        queryActivitySplitByCondition(1,$("#bs_pag").bs_pagination('getOption','rowsPerPage'));
                    }else{
                         $("#editHint").removeClass();
                         $("#editHint").show();
                         $("#editHint").addClass("alert alert-danger");
                         if(data.message==null||data.message==""){
                            data.message="服务器忙，请稍后重试...";
                         }
                         $("#editHint").html(data.message+"<button type='button' id='hideBtn' class='close'>&times;</button>");
                         $("#hideBtn").click(function(){
                         $("#editHint").hide();
                         });
                          $("#editActivityModal").modal("show");

                    }
                }
            });
        });

        //批量导出全部市场活动数据
        $("#exportActivityAllBtn").click(function(){
            $.ajax({
                'url':"/activity/exportAllActivity",
                'data':{},
                'type':"post",
                'dataType':"json",
                "success":function(data){
                    if(data==null){
                        alert("服务器忙，请稍后重试...");
                        return;
                    }
                    if(data.flag==true){
                    //导出数据已准备好
                   alert("数据已导出");
                   window.location.href="/activity/downloadActivityFile?fileName="+data.fileName;
                   console.log("/activity/downloadActivityFile?fileName="+data.fileName);
                    }else{
                        alert("服务器忙，请稍后重试...");
                    }
                }
            });
        });

        $("#exportActivityXzBtn").click(function(){
            var checklist=$("#aTbody input[type='checkbox']:checked");
            if(checklist==null || checklist.size()==0){
                alert("请选择需要导出的数据项");
                return;
            }
            var ids="";
            $.each(checklist,function(){
                ids+="id="+this.value+"&";
            });
            ids=ids.substr(0,ids.length-1);
            $.ajax({
                'url':"/activity/exportSelectedActivity",
                'data':ids,
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data==null){
                    alert("服务器忙，请稍后重试...");
                    return;
                    }
                     if(data.flag==true){
                      //导出数据已准备好
                      alert("数据已导出");
                      window.location.href="/activity/downloadActivityFile?fileName="+data.fileName;
                      console.log("/activity/downloadActivityFile?fileName="+data.fileName);
                      }
                      else{
                            alert("服务器忙，请稍后重试...");
                            }
                }
            });
        });
        //导入市场活动
        $("#importActivityBtn").click(function(){
               $("#activityFile").get(0).value='';
               $("#importHint").html("");
               var fname= $("#activityFile").val();
               var suffix=fname.substr(fname.lastIndexOf(".")+1).toLocaleLowerCase();
               if(suffix!="xlsx"&&suffix!="xls"){
                    $("#importHint").html("只允许上传Excel文件");
                    return;
               }
               //获取实际上传的文件本体
               var activityFile=$("#activityFile")[0].files[0];
               if(activityFile.size>5*1024*1024){
                    $("#importHint").html("上传文件大小不能超过5MB");
                    return;
               }
               var formData=new FormData();//ajax提供的接口，可以以键值对的形式向服务器端传送文本参数或者二进制参数等
               formData.append("activityFile",activityFile);

            $.ajax({
                'url':"/activity/importActivity",
                'data':formData,
                'processData':false,//用来设置是否将所有的参数转为字符串，默认为true
                'contentType':false,//设置ajax把所有的参数统一按urlencoded编码，默认是true
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                     $("#importHint").html("");
                        alert("成功导入"+data.object.success+"条数据");
                        $("#importActivityModal").modal("hide");
                        $("#query-name").val("");
                        $("#query-owner").val("");
                        $("#query-startDate").val("");
                        $("#query-endDate").val("");
                        queryActivitySplitByCondition(1,$("#bs_pag").bs_pagination('getOption','rowsPerPage'))
                    }else{
                        $("#importActivityModal").modal("show");
                         $("#importHint").html(data.message);
                    }
                },
                beforeSend:function(){
                    $("#importHint").html("导入中，请稍等...<img src='/image/loading.gif' width=10px height=10px />");
                }

            });
        });

	});
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal"  role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>

				<div id="hint" style="padding:8px;width:350px;position:relative;left:30%;margin:5px">

				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="createActivityForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control selectpicker" data-live-search="true" id="create-marketActivityOwner">
					            <c:forEach items="${ulist}" var="u" >
					                <option value="${u.id}">${u.name}</option>
					            </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label" >开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-startTime" readonly="readonly">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label" >结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-endTime" readonly="readonly">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div id="editHint" style="padding:8px;width:350px;position:relative;left:30%;margin:5px">

                				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-ActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control selectpicker" id="edit-ActivityOwner">
							    <c:forEach items="${ulist}" var="u" >
                                  <option value="${u.id}">${u.name}</option>
                                 </c:forEach>
								</select>
							</div>
                            <label for="edit-ActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-ActivityName" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						<input type="hidden" id="edit-id" />
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveEditActivity">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls/xlsx]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                        <br/>
                        <span id="importHint" style="color:#F00"></span>
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS和XLSX的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
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
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" readonly />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate" readonly>
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="queryActivityByCondition">查询</button>

				</form>
			</div>
			<div class="col-xs-3 col-xs-offset-4 " style="heigth:25px"><span id="queryHint" style="color:#f00"></span></div>
			<hr />
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary"  id="createActivity"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="aTbody">
					<!--
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>
                        -->

					</tbody>
				</table>

				<div id="bs_pag"></div>
				<input type="hidden" id="selectUrl" value=${url} />
			</div>
			<%--
			<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRecordsB"></b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							2
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">10</a></li>
							<li><a href="#">20</a></li>
							<li><a href="#">50</a></li>
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
			</div>
			--%>
		</div>
		
	</div>
</body>
</html>