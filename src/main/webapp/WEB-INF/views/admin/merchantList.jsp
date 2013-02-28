<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/page/admin/jqueryeasy.jsp"%>
<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	jQuery(function($) {
		$('#verifyMerchantTable').datagrid({
				title : '餐厅信息审核列表', //标题
				method : 'post',
				iconCls : 'icon-list', //图标
				singleSelect : false, //多选
				height : 400, //高度
				fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
				striped : true, //奇偶行颜色不同
				collapsible : true,//可折叠
				url : "${ctx}/admin/merchant/verifyMmerchantQueryList", //数据来源
				remoteSort : true, //服务器端排序
				idField : 'merchantId', //主键字段
				pagination : true, //显示分页
				rownumbers : true, //显示行号
				frozenColumns : [ [ {
					field : 'merchantId',
					checkbox : true
				} //显示复选框
				] ],
				columns : [ [
					 {field : 'nickName',title : '用户昵称',width : 20,sortable : true },  
					 {field : 'email',title : '注册邮箱',width : 20,sortable : true}, 
		             {field : 'merchantName',title : '餐厅',width : 25,sortable :true,
						 formatter: function(value, rec, index){
							 return '<a style="color:blue" target="_blank" onclick="javascript:todoMerchantIndex('+ rec.merchantId + ',' + rec.verifyStatus + ');">'+rec.merchantName+'</a>';
						 }	 
		             },
		             {field : 'createDate',title : '提交时间',width : 20,sortable : true,
		            	 formatter:function(value,row){
		    					if(row.createDate !=null){
		    						return new Date(row.createDate).format("yyyy-MM-dd hh:mm");
		    					}else{
		    						return '';
		    					}
		    				}
		             },
		             {field : 'status',title : '状态',width : 10,sortable : true,
		            	 formatter:function(value,row){
		            		var display="";
	    					if(row.verifyStatus ==0){
	    						display="审核中"
	    					}else if(row.verifyStatus ==1){
	    						display="已通过"
	    					}else if(row.verifyStatus ==2){
	    						display="未通过"
	    					}
	    					return display;
		    			}	 
		             },
		             {field : 'verifyDate',title : '审核时间',width : 20,sortable : true,
		            	 formatter:function(value,row){
		    					if(row.verifyDate !=null){
		    						return new Date(row.verifyDate).format("yyyy-MM-dd hh:mm");
		    					}else{
		    						return '';
		    					}
		    				}
		             },
					 {field : 'opt',title : '操作',width : 10,align : 'center',rowspan : 1,
		             formatter : function(value, rec, index) {
		            	 if(rec.verifyStatus ==0){ //审核中 不能删除
		            		 d = '| <a href="###" ><img src="${ctx}/static/images/admin/delete_disable.png"  border="0"  title="删除"/></a>'
		            	 }else{
		            		 d = '| <a href="###" onclick="singleDelete('+ rec.verifyMerchantId + ',' + rec.verifyStatus + ');"><img src="${ctx}/static/images/admin/delete.png"  border="0"  title="删除"/></a>'
		            	 }
						 return  ' <a href="###" onclick="verifyMerchant('+ rec.verifyMerchantId  + ',' + rec.verifyStatus + ');"><img src="${ctx}/static/images/admin/exchange_oper.png"  border="0"  title="查看餐厅维护日志" /></a>'
								+ d;
					}
				} ] ],
			toolbar : [ {
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					batchDeleterow();
				}
			}],
			onLoadSuccess : function() {
				$('#verifyMerchantTable').datagrid('clearSelections'); 
			}
		});
	});
	
	
	function todoMerchantIndex(merchantId,verifyStatus){
		if(verifyStatus==1){
			windowOpen("${ctx}/MerchantView/MerchantIndex/"+merchantId);
		}else{
			windowOpen("${ctx}/merchant/merchantPreview/"+merchantId);
		}
	}
	
	function singleDelete(verifyMerchantId, status) {
		$('#verifyMerchantTable').datagrid('clearSelections');
		
		if(status=='0'){
			$.messager.alert('提示', "审核中的状态不能被删除!");
    		return;
		}
		
		var param = 'idList=' + verifyMerchantId;
		
		deleteMerchantInfo(param);
	}

	//批量删除
	function batchDeleterow() {
		var rows = $('#verifyMerchantTable').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要删除的记录", 'info');
			return;
		}
		
		var isStatus= false;
		var params="";
		$.each(rows, function(i, n) {
			if(n.status=='0'){
				isStatus= true;
			}
			if (i == 0) {
				params += "idList=" + n.verifyMerchantId;
			} else {
				params += "&idList=" + n.verifyMerchantId;
			}
		});
		
		if(isStatus){
			$.messager.alert('提示', "审核中的状态不能被删除!");
    		return;
		}
		
		deleteMerchantInfo(params);
	}

	function deleteMerchantInfo(params) {
		$.messager.confirm('确认', '确认删除吗？', function(r) {
			if (r) {
				$.post('${ctx}/admin/merchant/merchantBatchDelete', params, function(ret) {
					if (ret == 'true') {
						$.messager.alert('信息', '删除成功');
						$('#verifyMerchantTable').datagrid('reload');
						$('#verifyMerchantTable').datagrid('clearSelections');
					} else if (ret == 'false') {
						$.messager.alert('信息', '删除失败');
					} 
					clearSelect('verifyMerchantTable');
				});
			}
		});
	}
	
	
	//表格查询
	function searchForm() {
		var params = {};
		var fields = $('#merchantSearchForm').serializeArray();
		$.each(fields, function(i, field) {
			if (field.value != "") {
				params[field.name] = field.value; //设置查询参数
			}
		});
	    $('#verifyMerchantTable').datagrid({pageNumber:1,queryParams:params});
	}

	//清空查询条件
	function clearForm() {
		$('#merchantSearchForm').form('clear');
		
		$("#querySelect").combobox('setValue','');
		
		searchForm();
	}
	
	
	//============    verifyMerchantEvent 页面 事件   =======================
	function verifyMerchant(verifyMerchantId,status){
		$.ajax({
            type: "post",
            url: "${ctx}/admin/merchant/getVerifyMerchant",
            data: "verifyMerchantId="+verifyMerchantId,
            success: function (returnData) {
            	 showWindow({
       				title: '审核餐厅信息',
              		href : '${ctx}/admin/merchant/verifyMerchantPopWindow',
              		width : 580,
       	    		height : 400,
       	    		minimizable : false,
       	    		maximizable : false,
       	    		collapsible : false,
       	    		resizable : false,
       	    		draggable :false,
       	    		onLoad : function() {
       	    			
       	    			if(status==1 || status == 2){
      	    				$('#btn-verifyMerchant').linkbutton('disable');
      	    				$('#btn-unVerifyMerchant').linkbutton('disable');
      	    			}
       	    			$("#verifyMerchantId").val(returnData['verifyMerchantId']);
       	    			$("#merchantId").val(returnData['merchantId']);
       	    			$("#nickNameText").html(returnData['nickName']);
       	    			$("#email").html(returnData['email']);
       	    			$("#createDate").html(returnData['createDate']);
       	    			var statusText = "";
       	    			if(returnData['merchantStatus']=='0'){
       	    				statusText ="审核中" ;
       	    			}else if(returnData['merchantStatus']=='1'){
       	    				statusText ="已通过" ; 
       	    			}else if(returnData['merchantStatus']=='2'){
       	    				statusText ="未通过";
       	    			}
       	    			$('#merchantStatus').html(statusText);

       	    			var text = returnData['merchantName'];
       	    			if (text.length > 15){
       	    				result = text.substring(0,15) + "...";
       	    			}else{
       	    				result = text;
       	    			}
       	    			
       	    			var _merchant = '<td  width="65"  class="textr" height="5">餐厅：</td><td><a title = "' + text + '" onclick="javascript:togoMerchantIdenx('+returnData['merchantId']+','+returnData.merchantStatus+');"  style="color: blue;">'
	       	    			+result
	       	    			+'</a></td>';
       	    			
       	    			$("#_merchant").append(_merchant);
       	    			
       	    			
       	    			$('#merchantStyle').html(returnData['merchantStyle']);
      	    			$('#cityName').html(returnData['cityName']+'/'+returnData['area']);
      	    			$('#addressText').html(returnData['address']);
      	    			$('#descriptionText').html(returnData['description']);
       	    		}
       	    	});
            },
            error: function () {
                $.messager.alert('错误', '失败！', "error");
            }
        });
	}
	
	
	function verifyMerchantSubmmit(isVerify){
		// 1 提交 // 2 拒绝 
		var verifyMerchantId= $('#verifyMerchantId').val();
		var merchantId = $('#merchantId').val();
		var parm="verifyMerchantId="+verifyMerchantId+"&merchantId="+ merchantId+"&isVerify="+ isVerify+"&reason=";
		if(isVerify==2){
			 $('#dictdlg').dialog("open");
		     $('#dictdlg').dialog({
					title : '拒绝审核信息',
					width : 350,
					modal : true,
					shadow : true,
					closed : false,
					height : 250,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					resizable : false,
					draggable : false
				});
		    	$("#verifyMerchantId").val(verifyMerchantId);
	    		$("#merchantId").val(merchantId);
	    		
	    		$('#reason').focus();
				$('#reason').click();
	    		
		}else{
			$.ajax({
	            type: "post",
	            url: "${ctx}/admin/merchant/verifyMerchantIsPass",
	            data: parm,
	            success: function (returnData) {
	            	$.messager.alert('提示', returnData.msg, "info");
	           		$('#MyPopWindow').window('close'); 
	                $('#verifyMerchantTable').datagrid("reload");
	            },
	            error: function () {
	                $.messager.alert('错误', "失败", "error");
	            }
	        });
		}
	}
	
	function refuseSuggestionBtn(){
		var verifyMerchantId= $('#verifyMerchantId').val();
		var merchantId = $('#merchantId').val();

		var reason = jQuery.trim($('#reason').val());
		if(reason!=''||reason!=""){
			if(generalUtil.strLengthCN(reason)>=200){
				$.messager.alert('提示', '拒绝理由在100个字符以内！', 'info');
				$("#reason").select();
				return false;
			}
			
			var parm="verifyMerchantId="+verifyMerchantId+"&merchantId="+ merchantId+"&isVerify=2"+"&reason="+reason;
			
			$.ajax({
	            type: "post",
	            url: "${ctx}/admin/merchant/verifyMerchantIsPass",
	            data: parm,
	            success: function (returnData) {
	            	$.messager.alert('提示', returnData.msg, "info");
	           		$('#MyPopWindow').window('close'); 
	                $('#verifyMerchantTable').datagrid("reload");
	                closeDictDialog() ;
	                document.getElementById("reason").value='';
	            },
	            error: function () {
	                $.messager.alert('错误', "失败", "error");
	                document.getElementById("reason").value='';
	            }
	        });
		}else{
			$.messager.alert('提示', '拒绝理由不能为空！', 'info');
		}
	}
	
	//关闭
	function closeDictDialog() {
		$('#dictdlg').dialog('close');
	}
	// -------------------------------------------------------------------------------------
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".panel-header").css("width", "auto");
		$(".panel-body").css("width", "auto");
		
		$("#querySelect").combobox('setValue','');
	})
</script>
</head>
<body>
	<div style="background: #fafafa; padding: 10px;" class="table-inner">
		<div class="easyui-panel" title="查询" iconCls="icon-search" collapsible="true">
			<form id="merchantSearchForm">
				<table height="80">
					<tr>
						<td >用户昵称：</td>
						<td><input name="nickName" id="nickName"></td>
						
						<td>餐厅名称：<input name="merchantName" ></td>
						
						<td >审核状态：</td>
						<td>
							<select id="querySelect" class="easyui-combobox" name="verifyStatus"  panelHeight="auto" style="width:150px;">
								<option value="" selected="selected">请选择</option>
								<option value="0">审核中</option>
								<option value="1">已通过</option>
								<option value="2">未通过</option>
							</select>
					  </td>
					</tr>
					<tr>
						<td>提交时间： (从)</td>
				    	<td > 
				    		<input class="easyui-datebox" id="startDate" name="startDate">
						</td>
						<td>到 &nbsp; 
							<input class="easyui-datebox" id="endDate" name="endDate">
						</td>
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
		<table id="verifyMerchantTable" style="padding: 0px 2px 0px 0px">
		</table>
	</div>
	
	<div id="MyPopWindow" modal="true" shadow="false" cache="false" style="margin: 0px; padding: 0px; overflow: auto;"></div>
</body>



<div id="dictdlg" class="easyui-dialog" style="padding: 10px 20px; overflow: auto;margin:2px;" closed="true" buttons="#dictdlg-buttons">
		<form  style="margin: 10px; text-align: center; display: inline;">
			<input type="hidden" name="verifyMerchantId" id="verifyMerchantId">
			<input type="hidden" name="merchantId" id="merchantId">
			
				<table  align="center" class="text03">
				<tr>
					<td>
						请填写审核拒绝意见理由（字数在100汉字以内） ：
										</td>
				</tr>
				<tr>
					<td>
						<textarea name="reason" rows="5" cols="45" id="reason" style="resize: none;width: 250px;"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center">
						<a href="#" id="btn-add" onClick="refuseSuggestionBtn();" class="easyui-linkbutton" iconcls="icon-save">提交</a>&nbsp;&nbsp;&nbsp;
						<a href="#" id="btn-back" onClick="javascript:closeDictDialog();" class="easyui-linkbutton" iconcls="icon-cancel">关闭</a>					</td>
				</tr>
</table>
	</form>
</div>



</html>