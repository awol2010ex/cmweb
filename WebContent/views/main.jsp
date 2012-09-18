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
<jsp:include  page="/css.jsp"  flush="true" />

<script type='text/javascript'>
$(function (){
            $("#layout1").ligerLayout({
                minLeftWidth:80,
                minRightWidth:80
            });
});

</script>
</head>
<body>

 <div id="layout1">
             <div  position="left"></div>
            <div position="center" title="报表列表">
            </div>
            <div position="top"><%=cognos8_str%></div>
        </div> 

</body>
</html>