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
                $("#queryHint").html("?????????????????????...<img src='/image/loading.gif' width=20px height=20px />");
            }
        });
    }
    //??????????????????????????????
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
				<h3>???????????????</h3>
			</div>
		</div>
	</div>
		<div class="col-xs-3 col-xs-offset-4 " style="heigth:25px"><span id="queryHint" style="color:#f00"></span></div>
    			<hr />
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" id="createBtn"></span> ??????</button>
		  <button type="button" class="btn btn-default" onclick="window.location.href='edit.html'"><span class="glyphicon glyphicon-edit"></span> ??????</button>
		  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> ??????</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>?????????</td>
					<td>??????</td>
					<td>?????????</td>
					<td>??????????????????</td>
				</tr>
			</thead>
			<tbody id="dicTbody">
			<!--
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>m</td>
					<td>???</td>
					<td>1</td>
					<td>sex</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td>f</td>
					<td>???</td>
					<td>2</td>
					<td>sex</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>3</td>
					<td>1</td>
					<td>????????????</td>
					<td>1</td>
					<td>orgType</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>4</td>
					<td>2</td>
					<td>????????????</td>
					<td>2</td>
					<td>orgType</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>5</td>
					<td>3</td>
					<td>????????????</td>
					<td>3</td>
					<td>orgType</td>
				</tr>
				-->
			</tbody>
		</table>
	</div>
	
</body>
</html>