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

		Cognos8Dwr.saveTimeTask(
        	 utils.getFormData($("#form1")[0]),
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

         columns: [
               {
             	  
             	  display: '名称', name: 'name', isAllowHide: true ,align:"left" ,
             	  render: function (row)
                   {
                       var html = "<img src='"+row.icon+"' style='width:12px;height:12px;' />&nbsp;"+row.name;
                       return html;
                   }

             	  
               }
         ],
         sortName: 'id',
         showTitle: false,
         dataAction:'local',
         pageSize: 10,
         height:"90%",
         enabledEdit: false,
         pageSizeOptions: [10,50,100],
         rownumbers:true,

         colDraggable:true

     });

	//报表列表管理器
    grid_manager =$("#grid").ligerGetGridManager();
    
    
    //已选报表数据
    var  selected_data= top.selected_grid_manager.getData();
    for ( var i = 0; i < selected_data.length; i++) {
		var data = selected_data[i];
		
		grid.addRow(getCleanRow(data));
	}

  

	
});
</script>
</head>
<body style="width: 95%; height: 95%;">
<div position="center">
<form id="form1">
<table cellpadding="0" cellspacing="0" class="l-table-edit">

	<tr>

		<td align="left" class="l-table-edit-td" width="20%">ID:</td>

		<td align="left" class="l-table-edit-td" width="70%" colspan="2"><input
			name="id" type="text" id="id" ltype="text" field_name="id"
			style="width: 400px" value="${bean.id}" readonly="true"/></td>

		<td align="left" width="100%"></td>

	</tr>
	<tr>

		<td align=""left"" class="l-table-edit-td">任务名:</td>

		<td align="left" class="l-table-edit-td" colspan="2"><input
			name="taskname" type="text" id="taskname" ltype="text" field_name="taskname"
			style="width: 400px" value="${bean.taskname}"/></td>

		<td align="left" width="100%"></td>

	</tr>
	<tr>

		<td align="left" class="l-table-edit-td">CRON表达式:</td>

		<td align="left" class="l-table-edit-td" ><input
			name="cron" type="text" id="cron" ltype="text" field_name="cron"
			style="width: 400px" value="${bean.cron}"  readOnly="true"/></td>

        <td align="left" >
            <input
			type="button" value="选择" id="Button3"
			class="l-button l-button-submit" />
        
        
        </td>
		<td align="left" width="100%"></td>

	</tr>
	
	<tr>
	
	    <td colspan="4" style="padding:10px">
	      <!-- 已选报表列表 -->
	       <div id="grid"></div>
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


</body>
</html>