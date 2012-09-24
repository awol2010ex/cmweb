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
<script type='text/javascript'
	src='<%=request.getContextPath() %>/dwr/interface/Cognos8Dwr.js'></script>
<script type='text/javascript'
	src='<%=request.getContextPath() %>/dwr/engine.js'></script>
	
<script type="text/javascript" src="<%=contextPath%>/static/scripts/draggable.js"></script> 	
	
<script type='text/javascript'>
var tree = null; ;//报表目录树

var grid=null;//报表列表

var grid_manager =null; //报表列表管理器

var selected_grid =null ;//已选报表列表

var selected_grid_manager =null ;//已选报表列表管理器

var searchPathMap ={}; //查询路径缓存
$(function (){
	
	        //划分布局
            $("#layout1").ligerLayout({
                minLeftWidth:100,
                leftWidth: 300,
                rightWidth:300,
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
							if (node.data && node.data.searchPath) {
								params.searchPath = node.data.searchPath+"/folder";//展开节点的searchPath,只显示目录
							}
							
							this
									.loadData(
											$("#"+node.data.id)[0],
											'<%=contextPath%>/restful/cognos8/path/list/',
											params);
							$("#"+node.data.id).data("isloaded",true)
						},
						onSelect :function (node){//点击刷新列表
							if(node.data && node.data.searchPath){
							 grid.set("parms",{searchPath:(node.data.searchPath+"/*")});//设置查询参数
							 grid_manager.loadData();
							}
						} 

					});
           
           
          //报表列表
           grid=$("#grid").ligerGrid({
        	    checkbox: true,

                columns: [
                      {
                    	  
                    	  display: '名称', name: 'name', isAllowHide: true ,align:"left" ,
                    	  render: function (row)
                          {
                              var html = "<img src='"+row.icon+"' style='width:12px;height:12px;' />&nbsp;"+row.name;
                              return html;
                          }

                    	  
                      },
                      {
                    	  
                    	  display: '类型', name: 'className', isAllowHide: true ,align:"left"
                    	  
                      }
                      
                      ,{
                    	  
                    	  display: '操作', isAllowHide: true ,align:"center" ,
                    	  render :function(row){
                    		  
                    		  var html="";
                    		  
                    		  if(row.className =='com.cognos.developer.schemas.bibus._3.ReportView' //报表视图
                    			 ||
                    			 row.className =='com.cognos.developer.schemas.bibus._3.Report'//报表
                    			 ||
                    			 row.className =='com.cognos.developer.schemas.bibus._3.Shortcut'//报表链接
                    		  ){
                    			  searchPathMap[row.id]=row.searchPath;//查询路径缓存
                    			  //显示发邮件按钮
                    			  html+="<a href='#' onclick=\"sendMail('"+row.id+"')\"> 发邮件 </a>";
                    		  }
                    		  return html;
                    	  }
                      }
                ],
                url: "<%=contextPath%>/restful/cognos8/report/list/",
                sortName: 'id',
                showTitle: false,
                dataAction:'server',
                pageSize: 10,
                height:"90%",
                enabledEdit: false,
                pageSizeOptions: [10,50,100],
                colDraggable:true,
                rowDraggable: true

            });

           grid_manager =$("#grid").ligerGetGridManager();
           
           
           
         //已选报表列表
           selected_grid=$("#selected_grid").ligerGrid({
        	    checkbox: true,
                columns: [
                      {
                    	  
                    	  display: '名称', name: 'name', isAllowHide: true ,align:"left" ,
                    	  render: function (row)
                          {
                              var html = "<img src='"+row.icon+"' style='width:12px;height:12px;' />&nbsp;"+row.name;
                              return html;
                          }

                    	  
                      },
                      {
                    	  
                    	  display: '类型', name: 'className', isAllowHide: true ,align:"left" ,
                    	  
                      }
                ],
                data:{Total:0 ,Rows:[]},
                sortName: 'id',
                showTitle: false,
                usePager:false,
                height:"90%",
                enabledEdit: false,
                colDraggable:true,
                rowDraggable: true

            });

           selected_grid_manager =$("#selected_grid").ligerGetGridManager();
           
           //拖动行为
           gridDraggable(grid ,selected_grid);

           
         //发送邮件
           $("#sendMailBtn").click(function(){
        	   Cognos8Dwr.emailReport(
        			   $("#Text_sendMail_addr").val(),
        			   $("#Text_searchPath").val(),
        			   parseInt($("#Text_sendMail_type").val()),
        			   $("#Text_sendMail_subject").val(),
        			   $("#Text_sendMail_body").val(),
        			   $("#Text_sendMail_org").ligerGetComboBoxManager().getValue(),
        	       function(result){
        		      alert(result);
        		   
        	       }
        	   );
           });
         
           //选择部门下拉树
           $("#Text_sendMail_org").ligerComboBox({
               width: 250,
               selectBoxWidth: 250,
               selectBoxHeight: 400, valueField: 'UID',textField: 'name', treeLeafOnly: false,
               split:",",
               absolute:false,
               tree: { 
            	   url: '<%=contextPath%>/restful/org/list/',
            	   checkbox:true,
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
                       if($("#"+node.data.ORG_CODE).data("isloaded")){
                          return; 
                       }
						var params = {};
						if (node.data && node.data.UID) {
							params.pid = node.data.UID;//展开节点的ID
						}
						
						this
								.loadData(
										$("#"+node.data.ORG_CODE)[0],
										'<%=contextPath%>/restful/org/list/',
										params);
						$("#"+node.data.ORG_CODE).data("isloaded",true)
				  }
               }
           });
           
           
           //发送邮件类型
           $("#Text_sendMail_type_show").ligerComboBox({  
        	   width:250,
        	   absolute:false,
               data: [
                   { text: 'HTML', id: '0' },
                   { text: 'XML', id: '1' },
                   { text: 'PDF', id: '2' },
                   { text: 'CSV', id: '3' },
                   { text: 'XLS', id: '4' }
               ], valueFieldID: 'Text_sendMail_type'
           });

});




//发邮件
function sendMail(id){
	 $.ligerDialog.open({ title:"发送邮件设置",  target: $("#send_email_form") , isResize:true ,width:400,height:300});
	 
	 $("#Text_searchPath").val(searchPathMap[id]);//显示搜索路径
}

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
              <div id="grid"></div>
			

		</div>
		<div position="top"><%=cognos8_str%></div>
		
		<div position="right" title="已选报表列表">
		     <div id="selected_grid"  ></div>
		</div>
	</div>


	<div id="send_email_form"
		style="width: 400px; height: 200px; margin: 3px; display: none;">
		<table width="100%">
			<tr>
				<td style="padding: 5px" align="left">查询路径:</td>
				<td style="padding: 5px" align="left"><input
					name="Text_searchPath" type="text" id="Text_searchPath"
					ltype="text" style="width: 250px;" readonly="true" />
				</td>
			</tr>
			<tr>
				<td style="padding: 5px" align="left">发送类型:</td>
				<td style="padding: 5px" align="left">
				<input
					name="Text_sendMail_type_show" type="text" id="Text_sendMail_type_show"
					ltype="text" style="width: 250px;" />
				<input
					name="Text_sendMail_type" type="text" id="Text_sendMail_type"
					ltype="text" style="width: 250px;display:none" />
				</td>
			</tr>
			<tr>
				<td style="padding: 5px" align="left">发送邮件地址:</td>
				<td style="padding: 5px" align="left"><input
					name="Text_sendMail_addr" type="text" id="Text_sendMail_addr"
					ltype="text" style="width: 250px;" />
				</td>
			</tr>
			
			<tr>
				<td style="padding: 5px" align="left">发送邮件标题:</td>
				<td style="padding: 5px" align="left"><input
					name="Text_sendMail_subject" type="text" id="Text_sendMail_subject"
					ltype="text" style="width: 250px;" />
				</td>
			</tr>
			<tr>
				<td style="padding: 5px" align="left">发送邮件内容:</td>
				<td style="padding: 5px" align="left"><input
					name="Text_sendMail_body" type="text" id="Text_sendMail_body"
					ltype="text" style="width: 250px;" />
				</td>
			</tr>
			<tr>
				<td style="padding: 5px" align="left">发送目标部门:</td>
				<td style="padding: 5px" align="left"><input
					name="Text_sendMail_org" type="text" id="Text_sendMail_org"
					ltype="text" style="width: 250px;" />
				</td>
			</tr>
			<tr>
          <td colspan="2" style="padding:5px" align="center">
              <input
			type="button" value="发送" id="sendMailBtn"
			class="l-button l-button-submit" />
          </td>
       </tr>
	</div>
</body>
</html>