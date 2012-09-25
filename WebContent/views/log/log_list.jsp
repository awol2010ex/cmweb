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
              { display: '搜索路径', name: 'SEARCHPATH', width: "25%",isAllowHide: true },
              { display: '发送人', name: 'SENDER', width: "25%",isAllowHide: true },
              { display: '报表名', name: 'REPORTNAME', width: "25%",isAllowHide: true },
              { display: '发送时间', name: 'CREATEDDATETIME', width: "25%",isAllowHide: true,type:'date' }
        ],
        url: "<%=contextPath%>/restful/cognos8/log/list/?reportid=<%=request.getParameter("reportid") %>",
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