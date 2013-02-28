<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/page/admin/jqueryeasy.jsp" %>

<script type="text/javascript">
	jQuery.ajaxSetup({cache:false});//ajax不缓存
	
	jQuery(function($) {
		$('#adminAccountTable').datagrid({
			title : '管理员列表', //标题
			method : 'post',
			iconCls : 'icon-list', //图标
			singleSelect : false, //多选
			height : 400, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/admin/queryList", //数据来源
			remoteSort : true, //服务器端排序
			idField : 'userId', //主键字段
			pagination : true, //显示分页
			rownumbers : true, //显示行号
			frozenColumns : [ [ {
				field : 'userId',
				checkbox : true
			} //显示复选框
			] ],
			columns : [[ 
				{field : 'userName',title : '用户名',width : 20,sortable : true},
				{field : 'opt',title:'操作',width:5,align:'center', rowspan:1,
					formatter:function(value,rec,index){
						return '<span style="color:red"><a href="###" onclick="updateRow('+index+');"><img src="${ctx}/static/images/admin/update.png"  border="0" title="编辑" /></a> | <a href="###" onclick="singleDeleterow('+rec.adminId+','+index+');"><img src="${ctx}/static/images/admin/delete.png"  border="0"  title="删除"/></a></span>';
					}
				}
				]],
			toolbar : [ {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					addrow();
				}
			}, '-', {
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					batchDeleterow();
				}
			}],
			onLoadSuccess : function() {
				$('#adminAccountTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	
	//新增
    function addrow() {
    	showWindow({
    		title : '增加管理员信息',
    		href : '${ctx}/admin/addAdmin',
			top:30,
			width : 300,
    		height : 200,
    		minimizable : false,
    		maximizable : false,
    		collapsible : false,
    		resizable : false,
    		draggable :false,
    		onLoad : function() {
    			$('#adminAccountTable').form('clear');
    			
    			$('#userId').val('0');
    		}
    	});
    }
    
    function updateRow(rowIndex){
    	$('#adminAccountTable').datagrid('clearSelections');
    	$('#adminAccountTable').datagrid('selectRow', rowIndex);
    	var row = $('#adminAccountTable').datagrid('getSelected');
    	if(row){
    		showWindow({
        		title : '更新管理员信息',
        		href : '${ctx}/admin/addAdmin',
        		width : 300,
        		height : 200,
        		minimizable : false,
        		maximizable : false,
        		collapsible : false,
        		resizable : false,
        		draggable :false,
        		onLoad : function() {
        			$('#adminAccountTable').datagrid('selectRow', rowIndex);
        			$("#adminForm").form('load', row);  
        			$('#userId').val(row.userId);
        		}
        	});
    	}
    }
    
	function singleDeleterow(adminId, rowIndex){
		$('#adminAccountTable').datagrid('clearSelections');
		$.messager.confirm('确认', '你确定删除此用户吗？', function(r){
			if(r){
				var param = 'idList=' + adminId;
				deleteAdmin(param);
			}
		});
	}
	
	function batchDeleterow(){
		var rows = $('#adminAccountTable').datagrid('getSelections');
		if (rows.length == 0) {
    		$.messager.alert('提示', "请选择你要删除的记录", 'info');
    		return;
    	}
		$.messager.confirm('确认', '确认删除选中的所有用户吗？', function(r){
			if(r){
				var params = "";
    			$.each(rows, function(i, n) {
    				if (i == 0){
    					params += "idList=" + n.adminId;
    				}else{
    					params += "&idList=" + n.adminId;
    				}
    			});
				deleteAdmin(params);
			}
		});
	}
	
	function deleteAdmin(params){
		$.post('${ctx}/admin/adminBatchDelete', params, function(ret){
			if(ret == 'true'){
				$.messager.alert('信息', '删除成功');
				$('#adminAccountTable').datagrid('reload');
				$('#adminAccountTable').datagrid('clearSelections');
			}else if(ret == 'false'){
				$.messager.alert('信息', '删除失败');
			}else{
				$.messager.alert('信息', '网络不通，请稍后再试');
			}
			clearSelect('adminAccountTable');
		});
	}
	
	function restorePWD(){
		var rows = $('#adminAccountTable').datagrid('getSelections');
    	if (rows.length == 0) {
    		$.messager.alert('提示', "请选择你要还原的记录", 'info');
    		return;
    	}
    	
    	$.messager.confirm('提示', '确定要把密码还原成"123456"吗？', 
    		function(result){
	    		if (result) {
	    			var parm = "";
	    			$.each(rows, function(i, n) {
	    				if (i == 0)
	    					parm += "idList=" + n.adminId;
	    				else
	    					parm += "&idList=" + n.adminId;
	    			});
	    			
	    			doRestorePwd(parm);
	    		}
	    	}
    	);
	}
	
	function doRestorePwd(parm){
    	$.ajax({
            type: "post",
            url: "${ctx}/admin/restorePwd",
            data: parm,
            success: function (returnData) {
            	$.messager.alert('提示', returnData.msg, "info", function () {
                    $('#adminAccountTable').datagrid("reload");
                    $('#adminAccountTable').datagrid('clearSelections');
                });
            },
            error: function () {
                $.messager.alert('错误', '还原失败！', "error");
            }
        });
    }
	
	//表格查询
    function searchForm() {
    	var params = {};
    	var fields = $('#adminSearchForm').serializeArray(); //自动序列化表单元素为JSON对象
    	$.each(fields, function(i, field) {
    		if (field.value != "") {
    			params[field.name] = field.value; //设置查询参数
    		}
    	});
    	 $('#adminAccountTable').datagrid({pageNumber:1,queryParams:params});
    }
	
	//清空查询条件
    function clearForm() {
    	$('#adminSearchForm').form('clear');
    	searchForm();
    }
	
	// ------------------------adminAddOrUpdatePage------------------------------------
	//新增
	function saveOrUpdateAdmin() {
		var r = $('#adminForm').form('validate');
		if (!r) {
			return false;
		}

		if(!checkUniqueValue('userName')){
			return false;
		}else{
			var params = {};
			params['userId'] = jQuery.trim($('#userId').val());
			params['userName'] = jQuery.trim($('#userName').val());
			if($('#userId').val() == 0){
				params['adminPassword'] = jQuery.trim($('#adminPassword').val());
			}else{
				params['adminPassword'] = '123456';
			}
			$.post('${ctx}/admin/adminAddOrUpdate', params, function(ret) {
				if (ret == 'new') {
					$.messager.alert('提示', '添加成功');
					closeWindow();
					$('#adminAccountTable').datagrid('reload');
				}else if (ret == 'update') {
					$.messager.alert('提示', '修改成功');
					closeWindow();
					$('#adminAccountTable').datagrid('reload');
				}
			});
		}
	}
	
	// 验证是否唯一
	function checkUniqueValue(paramName){
		var value = $.trim($('#'+paramName).val());
		if(value == ''){
			return false;
		}
		var flag = true;
		var params = {};
		params['userId'] = $('#userId').val();
		params['paramName'] = paramName;
		params['value'] = value;
		$.ajax({
			type : 'post',
			url : '${ctx}/admin/checkSame',
			data : params,
			async : false,
			success : function(ret){
				if(ret == 'false'){
					if(paramName == 'adminName'){
						$.messager.alert('信息', '用户名已存在');
					}
					$('#'+paramName).addClass('validatebox-invalid')
					flag = false;
				}
			}
		});
		return flag;
	}
	// -------------------------------------------------------------------------------------
</script>
<script type="text/javascript">
//<![CDATA[
$(document).ready(function(){
	$(".panel-header").css("width","auto");
	$(".panel-body").css("width","auto");
	})
//]]>
</script>
</head>
<body>
	<div style="background: #fafafa; padding:10px;" class="table-inner">
	<div class="easyui-panel" title="查询" iconCls="icon-search" collapsible="true">
	<form id="adminSearchForm">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input name="userName" style="width:auto"></td>
				<td><a href="#" onClick="searchForm();" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
    			<td><a href="#" onClick="clearForm();" class="easyui-linkbutton" iconCls="icon-reset">清空</a></td>
			</tr>
		</table>
	</form>
	</div>
	</div>
	<div class="table-inner">    	
        <table id="adminAccountTable" style="padding:0px 2px 0px 0px">
        </table>
    </div>
    <div id="MyPopWindow" modal="true" shadow="false" cache="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
</body>
</html>