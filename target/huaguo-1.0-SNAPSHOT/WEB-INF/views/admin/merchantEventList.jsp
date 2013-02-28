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
		$('#merchantEventTable').datagrid({
				title : '餐厅动态列表', //标题
				method : 'post',
				iconCls : 'icon-list', //图标
				singleSelect : false, //多选
				height : 400, //高度
				fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
				striped : true, //奇偶行颜色不同
				collapsible : true,//可折叠
				url : "${ctx}/admin/merchantEvent/merchantEventQueryList", //数据来源
				remoteSort : true, //服务器端排序
				idField : 'recordId', //主键字段
				pagination : true, //显示分页
				rownumbers : true, //显示行号
				frozenColumns : [ [ {
					field : 'recordId',
					checkbox : true
				} //显示复选框
				] ],
				columns : [ [
					 {field : 'nickName',title : '用户昵称',width : 20,sortable : true },  
					 {field : 'email',title : '注册邮箱',width : 20,sortable : true}, 
		             {field : 'merchantName',title : '餐厅',width : 25,sortable :true,
						 formatter: function(value, rec, index){
							 return '<a target="_blank" style="color:blue" onclick="javascript:togoMerchantIdenx('+rec.merchantId+','+rec.status+');">'+rec.merchantName+'</a>'
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
	    					if(row.status ==0){
	    						display="审核中"
	    					}else if(row.status ==1){
	    						display="已通过"
	    					}else if(row.status ==2){
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
		            	 if(rec.status ==0){ //审核中 不能删除
		            		 d = '| <a href="###" ><img src="${ctx}/static/images/admin/delete_disable.png"  border="0"  title="删除"/></a>'
		            	 }else{
		            		 d = '| <a href="###" onclick="singleDelete('+ rec.recordId + ',' + rec.status + ');"><img src="${ctx}/static/images/admin/delete.png"  border="0"  title="删除"/></a>'
		            	 }
						 return  ' <a href="###" onclick="verifyMerchantEvent('+ rec.recordId  + ',' + rec.status + ');"><img src="${ctx}/static/images/admin/exchange_oper.png"  border="0"  title="查看餐厅维护日志" /></a>'
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
				$('#merchantEventTable').datagrid('clearSelections'); 
			}
		});
	});
	
	
	function togoMerchantIdenx(merchantId,status){
		if(status==1){
			windowOpen("${ctx}/MerchantView/MerchantIndex/"+merchantId);
		}else if(status==0 || status==2){
			windowOpen("${ctx}/merchant/merchantPreview/"+merchantId);
		}
	}
	
	
	
	function singleDeleterow(recordId, status) {
		$('#merchantEventTable').datagrid('clearSelections');
		
		if(status=='0'){
			$.messager.alert('提示', "审核中的状态不能被删除!");
    		return;
		}
		
		var param = 'idList=' + recordId;
		
		deleteMerchantEvent(param);
	}

	//批量删除
	function batchDeleterow() {
		var rows = $('#merchantEventTable').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要删除的记录", 'info');
			return;
		}
		
		var isStatus= false;
		$.each(rows, function(i, n) {
			if(n.status=='0'){
				isStatus= true;
			}
			if (i == 0) {
				params += "idList=" + n.recordId;//+'&isRecommend='+n.recommendStatus;
			} else {
				params += "&idList=" + n.recordId;//+'&isRecommend='+n.recommendStatus;
			}
		});
		
		if(isStatus){
			$.messager.alert('提示', "审核中的状态不能被删除!");
    		return;
		}
		
		deleteMerchantEvent(params);
	}

	function deleteMerchantEvent(params) {
		$.messager.confirm('确认', '确认删除吗？', function(r) {
			if (r) {
				$.post('${ctx}/admin/merchantEvent/merchantEventBatchDelete', params, function(ret) {
					if (ret == 'true') {
						$.messager.alert('信息', '删除成功');
						$('#merchantEventTable').datagrid('reload');
						$('#merchantEventTable').datagrid('clearSelections');
					} else if (ret == 'false') {
						$.messager.alert('信息', '删除失败');
					} 
					clearSelect('merchantEventTable');
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
		$('#merchantEventTable').datagrid({pageNumber:1,queryParams:params});
	}

	//清空查询条件
	function clearForm() {
		$('#merchantSearchForm').form('clear');
		
		$("#querySelect").combobox('setValue','');
		
		searchForm();
	}
	
	
	//============    verifyMerchantEvent 页面 事件   =======================
	function verifyMerchantEvent(recordId,status){
		$.ajax({
            type: "post",
            url: "${ctx}/admin/merchantEvent/getVerifyMerchantEvent",
            data: "recordId="+recordId,
            success: function (returnData) {
            	 showWindow({
       				title: '审核餐厅动态信息',
              		href : '${ctx}/admin/merchantEvent/verifyMerchantEventPopWindow',
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
       	    			$("#recordId").val(returnData['recordId']);
       	    			var nickNameText = returnData['nickName'];
       	    			if (nickNameText.length > 20){
       	    				nickNameResult = nickNameText.substring(0,20) + "...";
       	    			}else{
       	    				nickNameResult = nickNameText;
       	    			}
       	    			$("#nickNameText").html(nickNameResult);
       	    			
       	    			$("#email").html(returnData['email']);
       	    			$("#createDate").html(returnData['createDate']);
       	    			var statusText = "";
       	    			if(returnData['status']=='0'){
       	    				statusText ="审核中" ;
       	    			}else if(returnData['status']=='1'){
       	    				statusText ="已通过" ; 
       	    			}else if(returnData['status']=='2'){
       	    				statusText ="未通过";
       	    			}
       	    			$('#status').html(statusText);
       	    			
       	    			var text = returnData['merchantName'];
       	    			if (text.length > 15){
       	    				result = text.substring(0,15) + "...";
       	    			}else{
       	    				result = text;
       	    			}
       	    			var _merchant = '<td  width="65"  class="textr" height="5">餐厅：</td><td><a title = "' + text + '" onclick="javascript:togoMerchantIdenx('+returnData['merchantId']+','+returnData.status+');"  style="color: blue;">'
	       	    			+result
	       	    			+'</a></td>';
       	    			$("#_merchant").append(_merchant);
       	    			
       	    			
       	    			$('#merchantStyle').html(returnData['merchantStyle']);
      	    			$('#cityName').html(returnData['cityName']+'/'+returnData['area']);
      	    			$('#addressText').html(returnData['address']);
      	    			$('#descriptionText').html(returnData['description']);
      	    			
      	    			
      	    			var _merchantEvent ='';
      	    			// 动态类型:0-店长说(餐厅动态),1-团购优惠,2-促销活动
      	    			var eventType =  returnData['eventType'];
      	    			if(eventType==0){
      	    				_merchantEvent='<td class="textr" height="5" ><font style="font-weight:bold" color="green">店长说：</font></td> <td><font style="font-weight:bold" color="green">'+returnData['content']  +  '</font></td>';
      	    			}else if(eventType==1){
      	    				_merchantEvent='<td class="textr" height="5" ><font style="font-weight:bold" color="green">团购优惠：</font></td> <td> <a target="_blank" href="'+returnData['eventlink']+'" ><font style="font-weight:bold" color="green">'+returnData['eventlink'] +'</font></a></td>';
      	    			}else if(eventType==2){
      	    				_merchantEvent='<td class="textr" height="5" ><font style="font-weight:bold" color="green">促销活动：</font></td> <td><font style="font-weight:bold" color="green">'+ returnData['content']  +  '</font></td>';
      	    			}
      	    			
      	    			$("#_merchantEvent").append(_merchantEvent);
       	    		}
       	    	});
            },
            error: function () {
                $.messager.alert('错误', '失败！', "error");
            }
        });
	}
	
	
	function verifyMerchantEventSubmmit(isVerify){
		// 1 提交
		var recordId = $('#recordId').val();
		var parm="recordId="+recordId+"&isVerify="+ isVerify+"&reason";
		// 2 拒绝 
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
	    		$("#recordId").val(recordId);
	    		$('#reason').focus();
				$('#reason').click();
		}else{
			$.ajax({
	            type: "post",
	            url: "${ctx}/admin/merchantEvent/verifyMerchantEventIsPass",
	            data: parm,
	            success: function (returnData) {
	            	$.messager.alert('提示', returnData.msg, "info");
	           		$('#MyPopWindow').window('close'); 
	                $('#merchantEventTable').datagrid("reload");
	            },
	            error: function () {
	                $.messager.alert('错误', "", "error");
	            }
	        });
		}
	}
	
	
	function refuseSuggestionBtn(){
		var recordId= $('#recordId').val();
		var reason = jQuery.trim($('#reason').val());
		if(reason!=''||reason!=""){
			if(generalUtil.strLengthCN(reason)>=200){
				$.messager.alert('提示', '拒绝理由在100个字符以内！', 'info');
				$("#reason").select();
				return false;
			}
			
			var parm="recordId="+recordId+"&isVerify=2&reason="+reason;
			$.ajax({
	            type: "post",
	            url: "${ctx}/admin/merchantEvent/verifyMerchantEventIsPass",
	            data: parm,
	            success: function (returnData) {
	            	$.messager.alert('提示', returnData.msg, "info");
	           		$('#MyPopWindow').window('close'); 
	                $('#merchantEventTable').datagrid("reload");
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
				<table width="902" height="60">
					<tr>
						<td align="right">用户昵称：</td>
						<td><input name="nickName" id="nickName"></td>
						
						<td align="right">餐厅名称： <input name="merchantName" style="width: auto"></td>
						
						<td align="right">审核状态：</td>
						<td>
							<select id="querySelect" class="easyui-combobox" name="status"  panelHeight="auto" style="width:150px;">
								<option value="" selected="selected">请选择</option>
								<option value="0">审核中</option>
								<option value="1">已通过</option>
								<option value="2">未通过</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">提交时间：(从)</td>
				    	<td >
							<input class="easyui-datebox" id="startDate" name="startDate">
						&nbsp;
					  </td>
						<td align="right">到： 
					  		<input class="easyui-datebox" id="endDate" name="endDate">
					    </td>
						<td></td>
						<td><a href="#" onClick="searchForm();" class="easyui-linkbutton" iconCls="icon-search">查询</a>    
							<a href="#" onClick="clearForm();"
							class="easyui-linkbutton" iconCls="icon-reset">清空</a></td>
					</tr>
</table>
			</form>
		</div>
	</div>
	<div class="table-inner">
		<table id="merchantEventTable" style="padding: 0px 2px 0px 0px">
		</table>
	</div>
	
	<div id="MyPopWindow" modal="true" shadow="false" cache="false" style="margin: 0px; padding: 0px; overflow: auto;"></div>
</body>

<div id="dictdlg" class="easyui-dialog" style="padding: 10px 20px; overflow: auto;margin:2px;" closed="true" buttons="#dictdlg-buttons">
		<form  style="margin: 10px; text-align: center; display: inline;">
			<input type="hidden" name="recordId" id="recordId">
			
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