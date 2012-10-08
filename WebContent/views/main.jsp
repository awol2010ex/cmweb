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
<script type='text/javascript'
	src='<%=request.getContextPath() %>/static/scripts/json2.js'></script>
<script type='text/javascript'>
var tree = null; ;//报表目录树

var grid=null;//报表列表

var grid_manager =null; //报表列表管理器

var selected_grid =null ;//已选报表列表

var selected_grid_manager =null ;//已选报表列表管理器

var searchPathMap ={}; //查询路径缓存

var navtab =null ;//标签页管理器
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
           
           
           //标签页
            $("#navtab1").ligerTab({changeHeightOnResize:true,dragToMove:true,dblClickToClose:true});
            navtab = $("#navtab1").ligerGetTabManager();
           
            
            //工具条
            $("#toptoolbar1").ligerToolBar({ 
       		 items: [
                   { 
                     text: '添加到已选择', 
                     icon:'add',
                     click:addReportsToSelected
                   },
                   {
                	 text :"查看定时任务",
                	 icon:"search",
                	 click:viewTimeTask
                   }
               ]
            });
            
            
          //报表列表
           grid=$("#grid").ligerGrid({
        	    checkbox: true,

                columns: [
                      {
                    	  
                    	  display: '名称', name: 'reportName', isAllowHide: true ,align:"left" ,
                    	  render: function (row)
                          {
                              var html = "<img src='"+row.icon+"' style='width:12px;height:12px;' />&nbsp;"+row.reportName;
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
                    			  searchPathMap[row.id]={searchPath:row.searchPath,name:row.name };//查询路径缓存
                    			  //显示发邮件按钮
                    			  html+="<a href='#' onclick=\"sendMail('"+row.id+"')\"> 发邮件 </a>&nbsp;"
                    			  +"<a href='#' onclick=\"viewLog('"+row.id+"')\"> 查看日志 </a>&nbsp;";
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
                rownumbers:true,

                colDraggable:true

            });

           grid_manager =$("#grid").ligerGetGridManager();
           
           
           
           
           
         //工具条
           $("#toptoolbar2").ligerToolBar({ 
      		 items: [
                  { 
                    text: '删除已选择', 
                    icon:'delete',
                    click:deleteReportsFromSelected
                  },
                  { 
                      text: '新建定时任务', 
                      icon:'add',
                      click:createTimeTask
                   }
              ]
           });
           
           
           
           
         //已选报表列表
           selected_grid=$("#selected_grid").ligerGrid({
        	    rownumbers:true,

        	    checkbox: true,
                columns: [
                      {
                    	  
                    	  display: '名称', name: 'reportName', isAllowHide: true ,align:"left" ,width:"100%",
                    	  render: function (row)
                          {
                              var html = "<img src='"+row.icon+"' style='width:12px;height:12px;' />&nbsp;"+row.reportName;
                              return html;
                          }

                    	  
                      }
                ],
                data:{Total:0 ,Rows:[]},
                sortName: 'id',
                showTitle: false,
                usePager:true,
                height:"90%",
                enabledEdit: false,
                colDraggable:true
            });

           selected_grid_manager =$("#selected_grid").ligerGetGridManager();
          

           
         //发送邮件
           $("#sendMailBtn").click(function(){
        	   var  waiting =$.ligerDialog.waitting('正在发送邮件中,请稍候...');
        	   
        	   var  fields=$("#param_edit_form").data("fields");//字段列表
        	   
        	   var params= "[]";//报表参数值
        	   
        	   if(fields && fields.length>0){
        		  var array=[];
        	      for(var i=0,s=fields.length;i<s;i++){
        	    	var obj ={};
        	    	obj.name =fields[i].name;
        	    	obj.value =$("#"+fields[i].name).val();
	        		array.push(obj);
	              }
        	      params = JSON.stringify(array);
        	   }
        	   
        	   
        	   //发送邮件操作
        	   Cognos8Dwr.emailReport(
        			   $("#Text_sendMail_addr").val(),
        			   $("#Text_searchPath").val(),
        			   parseInt($("#Text_sendMail_type").val()),

        			   $("#Text_sendMail_body").val(),
        			   $("#Text_sendMail_subject").val(),
        			   $("#Text_sendMail_org").ligerGetComboBoxManager().getValue(),
        			   params,
        	       function(result){
        			   waiting.close();
        		       alert(result);
        		       send_mail_dialog.hide();
        		   
        	       }
        	   );
           });
         
           //选择部门下拉树
           $("#Text_sendMail_org").ligerComboBox({
               width: 250,
               selectBoxWidth: 250,
               selectBoxHeight: 400, valueField: 'UID',textField: 'name', treeLeafOnly: false,
               split:",",
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
           
           
           //发送邮件类型下拉框
           $("#Text_sendMail_type_show").ligerComboBox({  
        	   width:250,
               data: [
                   { text: 'HTML', id: '0' },
                   { text: 'XML', id: '1' },
                   { text: 'PDF', id: '2' },
                   { text: 'CSV', id: '3' },
                   { text: 'XLS', id: '4' }
               ], valueFieldID: 'Text_sendMail_type'
           });

});



var send_mail_dialog =null ;//发送邮件窗口
//打开发邮件窗口
function sendMail(id){
	 send_mail_dialog=$.ligerDialog.open({ title:"发送邮件设置",  target: $("#send_email_form") , isResize:true ,width:400,height:300});
	 
	 $("#Text_searchPath").val(searchPathMap[id].searchPath);//显示搜索路径
	 
	 $("#Text_sendMail_subject").val(searchPathMap[id].name);//邮件标题
	 
	 $("#Text_sendMail_body").val("");//邮件标题
	 
	 

	   var  waiting =$.ligerDialog.waitting('正在取得报表参数中,请稍候...');
		//取得参数
		Cognos8Dwr.getReportParamters(
				
				searchPathMap[id].searchPath ,
				
				function(result){
					
					waiting.close();
					
					
			        if(result && result.length>0){
			        	
			        	var fields=[];
			        	for(var i=0,s=result.length;i<s;i++){
			        		fields.push({
			        			
			        			name:result[i].name,
			        			display:result[i].name,
			        			type: "text"
			        		});
			        	}
			        	//生成参数编辑表单
			        	$("#param_edit_form").ligerForm({
			                inputWidth: 170, labelWidth: 90, space: 40,
			                fields: fields
			            }); 
			        	
			        	$("#param_edit_form").data("fields",fields);
			        	
			        	for(var i=0,s=result.length;i<s;i++){
			        		$("#"+result[i].name).val("");
			        	}

			        }
					
			
		        }
	    );
}
//查看日志
function viewLog(id){
	 navtab.addTabItem({text:"查看日志",url:'<%=contextPath%>/views/log/log_list.jsp?reportid='+id,height:"90%"});
}
//添加选择的报表

	
	function addReportsToSelected() {
		var rows = grid.getCheckedRows();//已选行
		//加到已选择表

		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if (row.className == 'com.cognos.developer.schemas.bibus._3.ReportView' //报表视图
					|| row.className == 'com.cognos.developer.schemas.bibus._3.Report'//报表
					|| row.className == 'com.cognos.developer.schemas.bibus._3.Shortcut'//报表链接
			) {
				if (!isSelected(row)) {
					selected_grid.addRow(getCleanRow(row));
				}
			}
		}

	}
	//该报表是否已选择
	function isSelected(row) {
		var selected_data = selected_grid_manager.getData();//已选报表
		for ( var i = 0, s = selected_data.length; i < s; i++) {
			if (selected_data[i].id == row.id) {
				return true;
			}
		}
		return false;
	}
	//删除已选择报表
	function deleteReportsFromSelected() {
		selected_grid.deleteSelectedRow();

	}

	//字符串startsWith方法
	String.prototype.startWith = function(s) {
		if (s == null || s == "" || this.length == 0 || s.length > this.length)
			return false;
		if (this.substr(0, s.length) == s)
			return true;
		else
			return false;
		return true;
	}

	//取得清洁的ROW,去掉__开头的key
	function getCleanRow(row) {
		var newRow = {};
		for ( var key in row) {
			if (!key.startWith('__')) {
				newRow[key] = row[key];
			}
		}
		return newRow;
	}
	//新建定时任务
	function createTimeTask() {
		var tabid = new Date().getTime();
		navtab.addTabItem({
			text : "新建定时任务",
			url : "<%=contextPath%>/restful/cognos8/timetask/edit/?tabid="+tabid,height:"90%",tabid:tabid
		});
	}
	
	//查看定时任务
	function viewTimeTask(){
		navtab.addTabItem({text:"定时任务列表",url:"<%=contextPath%>/views/timetask/timetask_list.jsp",height:"90%"});
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
		<div position="center" >
		   <div id="navtab1" style=" border:1px solid #A3C0E8; height:100%;">
		     <div  title="报表列表">
		     
		       <table width="100%">
		        <tr>
		           <td>
		              <div id="toptoolbar1"></div>
		           </td>
		        </tr>
		        <tr><td>
                   <div id="grid"></div>
                </td></tr>
               </table>
			 </div>
           </div>
		</div>
		<div position="top"><%=cognos8_str%></div>
		
		<div position="right" title="已选报表列表">
		     
		     <table width="100%">
		        <tr>
		           <td>
		              <div id="toptoolbar2"></div>
		           </td>
		        </tr>
		        <tr><td>
                   <div id="selected_grid"  ></div>
                </td></tr>
               </table>
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
			    <td colspan="4">
			    <form id="param_edit_form"></form><!-- 参数修改窗口 -->
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