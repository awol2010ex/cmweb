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
var tree = null; ;//报表目录树

$(function (){
	
	        //划分布局
            $("#layout1").ligerLayout({
                minLeftWidth:100,
                leftWidth: 300,
                minRightWidth:80
            });
	        
            var height = $(".l-layout-center").height();
	        
          //面板
            $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

	        
	        
           //报表目录树
            tree = $("#tree1")
			.ligerTree(
					{

						url : '<%=contextPath%>/restful/cognos8/path/list/',
						checkbox: false,
						nodeWidth : 200,
						textFieldName : 'name',
	                    slide: false,
						idFieldName:"id",
						isLeaf:function(data){
							//这是BUG吧,为什么isLeaf 是 true ,hasChildren 就是true呢,看源代码
							return true;
						},
						onBeforeExpand : function(node) {

							//防止重复加载,使用isloaded 标记
                            if($("#"+node.data.id).data("isloaded")){
                               return; 
                            }
							var params = {};
							if (node.data.searchPath) {
								params.searchPath = node.data.searchPath+"/folder";//展开节点的searchPath,只显示目录
							}
							
							tree
									.loadData(
											$("#"+node.data.id)[0],
											'<%=contextPath%>/restful/cognos8/path/list/',
											params);
							$("#"+node.data.id).data("isloaded",true)
						}

					});


});

</script>
</head>
<body >

	<div id="layout1" style="overflow:auto">
		<div position="left" title="主要菜单" id="accordion1">
		
		      <div title="报表目录" class="l-scroll">
                         <ul id="tree1" style="margin-top:3px;">
              </div>

		</div>
		<div position="center" title="报表列表">

			

		</div>
		<div position="top"><%=cognos8_str%></div>
	</div>

</body>
</html>