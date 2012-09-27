<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<script type='text/javascript'
	src='<%=request.getContextPath() %>/dwr/interface/Cognos8Dwr.js'></script>
<script type='text/javascript'
	src='<%=request.getContextPath() %>/dwr/engine.js'></script>
<jsp:include  page="/css.jsp"  flush="true" />
<style type="text/css">
body {
	font-size: 12px;
	height:100%;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}
</style>
	
<script type='text/javascript'>
var g=null;
$(function (){
	var g=$("#grid").ligerGrid({
        columns: [ 
              { display: '任务名', name: 'TASKNAME', width: "20%",isAllowHide: true },
              { display: 'CRON', name: 'CRON', width: "20%",isAllowHide: true },
              { display: '创建时间', name: 'CREATEDDATETIME', width: "20%",isAllowHide: true,type:'date' },
              { display: '最后修改时间', name: 'LASTUPDATEDDATETIME', width: "20%",isAllowHide: true,type:'date' },
              { display: '操作', name: 'ID', width: "20%",isAllowHide: true,
              	render :function(row){
          		  
          		  var html="";
          	      html+="<a href='#' onclick=\"editTimeTask('"+row.ID+"')\">编辑</a>";
          	      html+="&nbsp;<a href='#' onclick=\"removeTimeTask('"+row.ID+"')\">删除</a>";
          		  
          		  return html;
          	    }
              
              }
        ],
        url: "<%=contextPath%>/restful/cognos8/timetask/list/",
        sortName: 'ID',
        showTitle: false,
        dataAction:'server',
        pageSize: 5,
        height:"100%",
        enabledEdit: true,
        dateFormat:'yyyy-MM-dd hh:mm:ss',
        pageSizeOptions: [5, 10, 15]

    });
	       

});
//编辑
function editTimeTask(id){
	var tabid = new Date().getTime();
	parent.navtab.addTabItem({text:"编辑定时任务",url:'<%=contextPath%>/restful/cognos8/timetask/edit/?tabid='+tabid+'&id='+id,height:"90%",tabid:tabid});
}
//删除
function removeTimeTask(id){
	var b =window.confirm("确定删除?");
	if(b){
		
		Cognos8Dwr.removeTimeTask(id, 
		    function(result){
			 if(result){
                alert("删除成功");
                $("#grid").ligerGetGridManager().loadData();
             }else{
                alert("删除失败");
             }
		    } 		
		
		);
	}
}


</script>
</head>
<body >

<table width="100%" height="100%">
	<tr>
		<td width="100%">
		<div id="grid" style="width: 100%; height: 100%;"></div>
		</td>

	</tr>
</table>
</body>
</html>