<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/page/admin/jqueryeasy.jsp"%>

<script type="text/javascript">
	jQuery.ajaxSetup({cache:false});//ajax不缓存
	
	jQuery(function($) {
		$('#userTable').datagrid({
			title : '用户列表', //标题
			method : 'post',
			iconCls : 'icon-list', //图标
			singleSelect : false, //多选
			height : 400, //高度
			fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
			striped : true, //奇偶行颜色不同
			collapsible : true,//可折叠
			url : "${ctx}/admin/user/queryAllUserList", //数据来源
			remoteSort : true, //服务器端排序
			sortName : 'createDate', //排序的列
			sortOrder : 'desc', //倒序
			idField : 'userId', //主键字段
			pagination : true, //显示分页
			rownumbers : true, //显示行号
			frozenColumns : [ [ {
				field : 'userId',
				checkbox : true
			} //显示复选框
			] ],
			columns : [[ 
				{field : 'nickName',title : '用户昵称',width : 20,sortable : true},
				{field : 'email',title : '注册邮箱',width : 20,sortable : true},
				{field : 'gender',title : '性别',width : 20,sortable : false,
	            	 formatter:function(value,row){
	            		 	var displayText="";
	    					if(row.gender =='m'){
	    						displayText ="男";
	    					}else if(row.gender =='f'){
	    						displayText ="女";
	    					}else if(row.gender =='n'){
	    						displayText ="未知";
	    					}	
	    					return displayText;
	    				}
	            },
				{field : 'createDate',title : '注册时间',width : 20,sortable : false,
	            	 formatter:function(value,row){
	    					if(row.createDate !=null){
	    						return new Date(row.createDate).format("yyyy-MM-dd hh:mm");
	    					}else{
	    						return '';
	    					}
	    				}
	             },
	            /*  {field : 'delTag',title : '状态',width : 20,sortable : false,
	            	 formatter:function(value,row){
	    					var id_delTag=row.userId+",'"+row.delTag+"'";
							if(row.delTag=='0'){//不显示
								delTagText='<a href="###" style="color:blue;"  onclick="updateDelTag('+id_delTag+');"> 启用 </a> '
							}else if(row.delTag=='1'){
								delTagText = '<a href="###" style="color:red;"  onclick="updateDelTag('+id_delTag+');"> 禁用 </a> ';
							}
	    					return delTagText;
	    				}
	             }, */
				{field : 'opt',title:'操作',width:5,align:'center', rowspan:1,
					formatter:function(value,rec,index){
						var deleteText = '<a href="###" onclick="singleDeleterow('+rec.userId+');"><img src="${ctx}/static/images/admin/delete.png"  border="0"  title="删除"/></a>';
						return deleteText
					}
				}
				]],
			toolbar : [ {
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					addrow();
				}
			} , '-' , {
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					batchDeleterow();
				}  
			}],
			onLoadSuccess : function() {
				$('#userTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
			}
		});
	});
	
	//新增
    function addrow() {
    	showWindow({
    		title : '新建用户信息',
    		href : '${ctx}/admin/user/addUser',
			top:30,
    		width : 500,
    		height : 300,
    		minimizable : false,
    		maximizable : false,
    		collapsible : false,
    		resizable : false,
    		draggable :false,
    		onLoad : function() {
    			$('#userTable').form('clear');
    			$('input:radio[name=gender][value="m"]').attr('checked', true);
    			loadAllArea();
    		}
    	});
    }
	
    
    function isDisableBtn(){
    	var password = jQuery.trim($('#password').val());
    	if(!password){
			$.messager.alert('提示','密码不能为空!','info');
			return false; 
		}
        if ($("#isDisable").is(":checked")) { 
        	document.getElementById("password").type="text";
        }else{
        	document.getElementById("password").type="password";
        }
    }
	
   /*  function updateDelTag(userId,delTag){
		var parm = "userId=" + userId +"&delTag="+delTag;
    	$.ajax({
			type : "post",
			url : "${ctx}/admin/user/updateDelTag",
			data : parm,
			success : function(returnData) {
				$.messager.alert('提示', returnData.msg, "info",
						function() {
							$('#userTable').datagrid('reload');
						});
			},
			error : function() {
				$.messager.alert('错误', '', "error");
			}
		});
	} */
    
	//新增
    function addOrUpdate(){
		var r = $('#userForm').form('validate');
		if(!r) {
			return false;
		}
		
		var tempnickname = $("#nickName").val();
		if(checkTxtVal(tempnickname)!=""){
			$.messager.alert('提示',"只支持汉字、数字、字母和下划线",'info');
	    	return;
	    }
	    var nickNameLength = namelen(tempnickname);
	    if(nickNameLength>32) {
	    	 $.messager.alert('提示',"只能输入16个汉字或32个字符",'info');
	    	 return;
	    }
		
		var fields = $("#userForm").serializeArray();
		
		$.post("${ctx}/admin/user/saveOrUpdate",fields,function(data){	
			if(data.error){
			}else{
				$('#MyPopWindow').window('close'); 
				$('#userTable').datagrid('reload');
			}
			$.messager.alert('提示',data.msg,'info');
		});
	}
    
    function checkTxtVal(txtValue) {
		  var forbidChar = new Array("@", "#", "$", "%", "^", "&", "*",
      		  "……", "“", "'", "￥", "×", "\"", "<", ">", "’",
      		  "”","~","·","（）","？","/",";",".",":","{","}","[","]","|","\\","+","=","`","(",")","?","、","；","：","【","】");

        for (var i = 0; i < forbidChar.length; i++) {
            if (txtValue.indexOf(forbidChar[i]) >= 0) {
                return "昵称只支持汉字、数字、字母和下划线";
            }
        }
        return "";
  }
	
	
	//判断字符长度，汉字算2个字节,7个汉字或14个字符
	function namelen(s) {
		var l = 0;
		var a = s.split("");
		for (var i=0;i<a.length;i++) {
			if (a[i].charCodeAt(0)<299) {
				l++;
			} else {
				l+=2;
			}
		}
		return l;
	}
    
	
	function singleDeleterow(userId){
		$('#userTable').datagrid('clearSelections');
		/* if(delTag=='0'){
			$.messager.alert('提示', "启用中的状态不能被删除!");
    		return;
		} */
		var param = 'idList=' + userId;
		deleteAdmin(param);
	}
	
	function batchDeleterow(){
		var rows = $('#userTable').datagrid('getSelections');
		if (rows.length == 0) {
    		$.messager.alert('提示', "请选择你要删除的记录", 'info');
    		return;
    	}
		var isDelTag= false;
		var params = "";
		$.each(rows, function(i, n) {
			/* if(n.delTag=='0'){
				isDelTag= true;
			} */
			if (i == 0){
				params += "idList=" + n.userId;
			}else{
				params += "&idList=" + n.userId;
			}
		});
		

		/* if(isDelTag){
			$.messager.alert('提示', "启用中的状态不能被删除!");
    		return;
		} */
		
		deleteAdmin(params);
	}
	
	function deleteAdmin(params){
		$.messager.confirm('确认', '确认删除选中的所有用户吗？', function(r){
			if(r){
				$.post('${ctx}/admin/user/userBatchDelete', params, function(ret){
					if(ret == 'true'){
						$.messager.alert('信息', '删除成功');
						$('#userTable').datagrid('reload');
						clearSelect('userTable');
					}else if(ret == 'false'){
						$.messager.alert('信息', '删除失败');
						clearSelect('userTable');
					}else{
						$.messager.alert('信息', '网络不通，请稍后再试');
					}
				});
			}
		});
	}
	
	
	//表格查询
    function searchForm() {
    	var params = {};
    	var fields = $('#adminUserSearchForm').serializeArray(); //自动序列化表单元素为JSON对象
    	$.each(fields, function(i, field) {
    		if (field.value != "") {
    			params[field.name] = field.value; //设置查询参数
    		}
    	});
    	$('#userTable').datagrid({pageNumber:1,queryParams:params});
    }
	
	
	//清空查询条件
    function clearForm() {
    	$('#adminUserSearchForm').form('clear');
    	searchForm();
    }
	
	
    function loadAllArea(){
   	 $('#province').combobox({
				url:'${ctx}/city/getAllList',
				valueField:'cityId',
				textField:'areaName',   
		    	onChange : function(cityId,o){
		    		$('#city').combobox({
						url:'${ctx}/city/getAllListById?id='+cityId,
						valueField:'cityId',
						textField:'areaName',   
		    		    onChange : function(n,o){
		    		    }   
					});
		    }   
		});
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
	<div style="background: #fafafa; padding: 10px;" class="table-inner">
		<div class="easyui-panel" title="查询" iconCls="icon-search"
			collapsible="true">
			<form id="adminUserSearchForm">
				<table height="35">
					<tr>
						<td>用户昵称：</td>
						<td><input name="nickName" style="width: auto"></td>
						<td>注册邮箱：</td>
						<td><input name="email" style="width: auto"></td>
						<td>注册时间：</td>
						<td>从 &nbsp; <input class="easyui-datebox" id="startDate" name="startDate"> &nbsp; 到
						</td>
						<td>&nbsp; <input class="easyui-datebox" id="endDate" name="endDate"></td>
						<td><a href="#" onClick="searchForm();"
							class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
						<td><a href="#" onClick="clearForm();"
							class="easyui-linkbutton" iconCls="icon-reset">清空</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="table-inner">
		<table id="userTable" style="padding: 0px 2px 0px 0px">
		</table>
	</div>
	<div id="MyPopWindow" modal="true" shadow="false" cache="false"
		style="margin: 0px; padding: 0px; overflow: auto;"></div>
</body>
</html>