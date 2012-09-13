<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();

    String cognos8_str=(String)request.getAttribute("cognos8_str");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type='text/javascript'>
//初始页面
function init(){
	
	<%if(cognos8_str!=null&&!"".equals(cognos8_str)){%>
	   alert("<%=cognos8_str%>");
	<%}%>
}
</script>
</head>
<body  onload="init()">

</body>
</html>