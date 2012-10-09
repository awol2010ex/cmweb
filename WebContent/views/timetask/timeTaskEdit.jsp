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
<script type='text/javascript'
	src='<%=request.getContextPath() %>/dwr/interface/Cognos8Dwr.js'></script>
<script type='text/javascript'
	src='<%=request.getContextPath() %>/dwr/engine.js'></script>
	

<script type='text/javascript'
	src='<%=request.getContextPath() %>/static/scripts/json2.js'></script>

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

var searchPathMap ={}; //查询路径缓存

var grid=null;//报表列表

var grid_manager =null; //报表列表管理器
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

$(function() {
	$("body").ligerLayout();
	
	//保存定时任务
	$("#Button1").click(function(){
		var params =utils.getFormData($("#form1")[0]);
		//发送邮件部门
		params.sendmailorg = $("#Text_sendMail_org").ligerGetComboBoxManager().getValue();
		params.sendmailorgname = $("#Text_sendMail_org").val();
		Cognos8Dwr.saveTimeTask(
			 params,//主表
			 grid_manager.getData(),//明细
             function(result){
                 if(result){
                    alert("保存成功");
                 }else{
                    alert("保存失败");
                 }
             }
        );
        
    });
	
	//关闭
	$("#Button2").click(function(){

		top.navtab.removeTabItem("<%=request.getParameter("tabid")%>");
        
    });
	
	//选择设置cron表达式
	
	$("#Button3").click(function() {
		
		    var url='<%=request.getContextPath() %>/views/timetask/cronDef.jsp';
		    
		    
		    //编辑cron表达式
		    if($("#cron").val() && $.trim($("#cron").val())!=''){
		    	url=url+"?taskConf="+$.trim($("#cron").val());
		    }
		    
			$.ligerDialog.open({
				title : '选择cron表达式',
				width : 700,
				height : 300,
				url :url ,
				buttons : [ {
					text : '确定',
					onclick : function(item, dialog){
						
						
						if (dialog.frame.DoSave()) {//转化为字符串
							var taskDef = dialog.frame.$("taskDef").value ;
							$("#cron").val(taskDef);
							dialog.close();
				        }
					}
				}, {
					text : '取消',
					onclick : function(item, dialog){dialog.close();}
				} ]
			});

		});
	
	
	//报表列表
    grid=$("#grid").ligerGrid({
         toolbar :{
            items:[{text :"添加已选报表",icon:'add', click :addSelectedRows} ]         
         },
         columns: [
               {
             	  
             	  display: '名称', name: 'reportName', isAllowHide: true ,align:"left" 
             	  
               },
               {
              	  
              	  display: '参数', name: 'params', isAllowHide: true ,align:"center" ,
              	  render :function(row,i){
              		  var html="";
              		  if(!row.params || row.params==''){
            		  
            	          html+="<a href=\"javascript:editParams('"+((row.reportid && row.reportid!='')?(row.reportid):(row.id))+"',"+i+")\">参数</a>";
              	      }
              		  else{
              			  html+="<a href=\"javascript:editParams('"+((row.reportid && row.reportid!='')?(row.reportid):(row.id))+"',"+i+")\">"+row.params+"</a>";
              		  }
            		  return html;
            	  }

              	  
               }
         ],
         data:{"Total":0 ,Rows:[]},
         sortName: 'id',
         showTitle: false,
         dataAction:'local',
         enabledEdit: false,
         rownumbers:true,
         height:"90%",
         width:"100%",
         colDraggable:true,
         usePager: false

     });

	//报表列表管理器
    grid_manager =$("#grid").ligerGetGridManager();
    
    

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
  
    $("#Text_sendMail_org").ligerGetComboBoxManager().setInputValue("${bean.sendmailorg}","${bean.sendmailorgname}");
 
    document.getElementById("Text_sendMail_org").value ="${bean.sendmailorgname}";
    
    
    //发送邮件类型
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

	
    
    <%if(request.getParameter("id")==null ){%>
       addSelectedRows();//添加已选中报表
    <%}else{%>
       //列出明细
       
       Cognos8Dwr.getAllTimeTaskDtlList(
    		   
    	"<%=request.getParameter("id")%>",
    		   
        function(result){
    		for(var i=0,s=result.length;i<s;i++){
    			
    			searchPathMap[result[i].reportid] =result[i].searchPath;
    			
    			grid.addRow(result[i]);
    		}
    	}  
       
       );
       
    
    <%}%>
    
    
    
  
});
//添加已选中报表
function addSelectedRows(){
	//已选报表数据
    var  selected_data= top.selected_grid_manager.getData();
    for ( var i = 0; i < selected_data.length; i++) {
		var row = selected_data[i];
		searchPathMap[row.id]=row.searchPath;//查询路径缓存
		grid.addRow(getCleanRow(row));
	}
}

//编辑参数
function editParams(id  ,index){
	var searchPath= searchPathMap[id];

	var  waiting =$.ligerDialog.waitting('正在打开中,请稍候...');
	//取得参数
	Cognos8Dwr.getReportParamters(
			
			searchPath ,
			
			function(result){
				
				waiting.close();
				
				
		        if(result && result.length>0){
		        	$.ligerDialog.open({ 
		        		title:"参数设置",  
		        		target: $("#param_edit_win") ,
		        		isResize:true ,
		        		width:400,
		        		height:300,
		        		buttons:[
		        		   {
		        			   
		        			text:"确定",onclick: function(item, dialog){
		        				
		        				var _params= "[]";//报表参数值
		        				var _fields =$("#param_edit_form").data("fields");
		        				if(_fields && _fields.length>0){
		        					var array=[];
		        					for(var i=0,s=_fields.length;i<s;i++){
		        						var obj ={};
		        	        	    	obj.name =_fields[i].name;
		        	        	    	obj.value =$("#"+_fields[i].name).val();
		        		        		array.push(obj);
		        					}
		        					_params = JSON.stringify(array);
		        				}
		        				
		        				
		        				//更新字段
		        				grid_manager.updateCell("params" ,_params , grid_manager.getRowObj(parseInt($("#param_edit_form").data("index"))));
		        				
		        				dialog.hide();
		        		    }
		        		   
		        		   }   ,
		        		   {
		        			   
			        		text:"取消",onclick: function(item, dialog){
			        			dialog.hide();
			        		}
			        		   
			        	   } 
		        		]
		        	});
		        	
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
		        	$("#param_edit_form").data("fields",fields);//字段列表寄存
		        	$("#param_edit_form").data("index",index);//行数寄存
		        	
		        	
		        	
		        	for(var i=0,s=result.length;i<s;i++){
		        		$("#"+result[i].name).val("");
		        	}

		        }
		        else{
		        	alert("没有参数");
		        }
				
		
	        }
    );
}
</script>
</head>
<body style="width: 95%; height: 95%;">
<div position="center">
<form id="form1" width="100%">
<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

	<tr>

		<td align="left" class="l-table-edit-td" width="20%">ID:</td>

		<td align="left" class="l-table-edit-td" width="70%" colspan="2"><input
			name="id" type="text" id="id" ltype="text" field_name="id"
			style="width: 250px" value="${bean.id}" readonly="true"/></td>

		<td align="left" width="100%"></td>

	</tr>
	<tr>

		<td align=""left"" class="l-table-edit-td">任务名:</td>

		<td align="left" class="l-table-edit-td" colspan="2"><input
			name="taskname" type="text" id="taskname" ltype="text" field_name="taskname"
			style="width: 250px" value="${bean.taskname}"/></td>

		<td align="left" width="100%"></td>

	</tr>
	<tr>

		<td align="left" class="l-table-edit-td">CRON表达式:</td>

		<td align="left" class="l-table-edit-td" ><input
			name="cron" type="text" id="cron" ltype="text" field_name="cron"
			style="width: 250px" value="${bean.cron}"  readOnly="true"/></td>

        <td align="left" class="l-table-edit-td">
            <input
			type="button" value="选择" id="Button3"
			class="l-button l-button-submit" />
        
        
        </td>
		<td align="left" width="100%"></td>

	</tr>
	<tr>
				<td  align="left" class="l-table-edit-td">发送类型:</td>
				<td  align="left" class="l-table-edit-td" colspan="2">
				<input
					name="Text_sendMail_type_show" type="text" id="Text_sendMail_type_show"
					ltype="text" style="width: 250px;" field_name="sendmailtypename"  value="${bean.sendmailtypename}"/>
				<input
					name="Text_sendMail_type" type="text" id="Text_sendMail_type"
					ltype="text" style="width: 250px;display:none" field_name="sendmailtype" value="${bean.sendmailtype}" />
				</td>
				<td align="left" width="100%"></td>
	</tr>
	<tr>
				<td  align="left" class="l-table-edit-td">发送邮件地址:</td>
				<td  align="left" class="l-table-edit-td" colspan="2"><input
					name="Text_sendMail_addr" type="text" id="Text_sendMail_addr"
					ltype="text" style="width: 250px;" field_name="sendmailaddr"  value="${bean.sendmailaddr}" />
				</td>
				<td align="left" width="100%"></td>
	</tr>
	<tr>
				<td align="left" class="l-table-edit-td">发送目标部门:</td>
				<td  align="left" class="l-table-edit-td" colspan="2"><input
					name="Text_sendMail_org" type="text" id="Text_sendMail_org"
					ltype="text" style="width: 250px;" />
				</td>
				<td align="left" width="100%"></td>
	</tr>
	<tr>
	
	    <td colspan="4" style="padding:10px">
	      <!-- 已选报表列表 -->
	       <div id="grid" style="width:100%"></div>
	    </td>
	</tr>
	
	
	<tr>
	
	    <td ><input
			type="button" value="提交" id="Button1"
			class="l-button l-button-submit" /></td>
	    <td colspan="2"><input
			type="button" value="关闭" id="Button2"
			class="l-button l-button-submit" /></td>
		<td align="left" width="100%"></td>
	</tr>
</table>
</form>
</div>

<!-- 参数编辑窗口 -->
<div id="param_edit_win"
		style="width: 400px; height: 200px; margin: 3px; display: none;">
		<form id="param_edit_form"></form>
</div>

</body>
</html>