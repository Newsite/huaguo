<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/page/admin/jqueryeasy.jsp"%>
<script type="text/javascript" src="${ctx}/static/scripts/common/area.js"></script>
<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	jQuery(function($) {
		$('#merchantIntoTable').datagrid({
				title : '餐厅信息列表', //标题
				method : 'post',
				iconCls : 'icon-list', //图标
				singleSelect : false, //多选
				height : 400, //高度
				fitColumns : true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
				striped : true, //奇偶行颜色不同
				collapsible : true,//可折叠
				url : "${ctx}/admin/merchant/merchantInfoQueryList", //数据来源
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
				             {field : 'merchantName',title : '餐厅',width : 25,sortable : true,
				            	 formatter:function(value,row){
				    					return '<a style="color:blue" onclick="todoMerchantIndex('+ row.merchantId + ');">'+row.merchantName+'</a>';
				            	 }
				             },
				             {field : 'merchantStyle',title : '所属菜系',width : 20,sortable : true},
				             {field : 'cityName',title : '所在城市',width : 20,sortable : true},
				             {field : 'area',title : ' 所在区域',width : 25,sortable : true},
				             {field : 'viewNum',title : '人气',width : 8,sortable : true},
				             {field : 'supportNum',title : '喜欢',width : 8,sortable : true},
				             {field : 'createUser',title : '创建者',width : 20,sortable : true ,
				            	 formatter:function(value,row){
				    					if(row.createUser !=null){
				    						return row.createUser;
				    					}else{
				    						return '';
				    					}
				    				}	 
				             },
				             {field : 'manager',title : '掌柜',width : 20,sortable : true,
				            	 formatter:function(value,row){
				    					if(row.manager !=null){
				    						return row.manager;
				    					}else{
				    						return '';
				    					}
				    				}	 
				             },
				             {field : 'updateDate',title : '上线时间',width : 15,sortable : true,
				            	 formatter:function(value,row){
				    					if(row.onLineDate !=null){
				    						return new Date(row.onLineDate).format("yyyy-MM-dd hh:mm");
				    					}else{
				    						return '';
				    					}
				    				}
				             },
				             {field : 'recommendStatus',title : '推荐状态',width : 10,sortable : true,
				            	 formatter:function(value,row){
				            		var display="";
			    					if(row.recommendStatus ==1){
			    						display="首页推荐"
			    					}else if(row.recommendStatus ==0){
			    						display="不推荐"
			    					}
			    					return display;
				    			}	 
				             },
							 {field : 'opt',title : '操作',width : 15,align : 'center',rowspan : 1,
				             formatter : function(value, rec, index) {
				            	 var isManager = "";
				            	 var isDelete = "";
				            	 if(rec.manager!=null){
				            		 isManager = ' <a href="###" onclick="viewMerchantManagerRow('+ rec.managerId + ',' + rec.merchantId + ');"><img src="${ctx}/static/images/admin/isAdmin.png"  border="0" title="餐厅掌柜" /></a> |'
				            	 }
				            	 if(rec.recommendStatus ==0){
				            		 isDelete = ' <a href="###" onclick="singleDeleterow('+ rec.merchantId + ',' + rec.recommendStatus + ');"><img src="${ctx}/static/images/admin/delete.png"  border="0"  title="删除"/></a>'
				            	 }else if(rec.recommendStatus ==1){
				            		 isDelete = ' <a href="###" ><img src="${ctx}/static/images/admin/delete_disable.png"  border="0"  title="删除"/></a>'
				            	 }
										//+ ' <a href="###" onclick="viewMerchantLogRow('+ rec.merchantId + ');"><img src="${ctx}/static/images/admin/exchange_oper.png"  border="0"  title="查看餐厅维护日志" /></a> |'
								return  isManager  + isDelete;
										
							}
						} ] ],
				toolbar : [ {
					text : '置顶推荐',
					iconCls : 'icon-recommend',
					handler : function() {
						recommend('1','推荐');//首页推荐
					}
				}, '-', {
					text : '取消推荐',
					handler : function() {
						recommend('0','取消推荐');
					}
				},/*   '-', {
					text : '新建餐厅',
					iconCls:'icon-newMerchant',
					handler : function() {
						newMerchant();
					}
				},  */ '-', {
					text : '批量删除',
					iconCls : 'icon-remove',
					handler : function() {
						batchDeleterow();
					}
				}],
				onLoadSuccess : function() {
					$('#merchantIntoTable').datagrid('clearSelections'); 
				}
			});
	});


		//新增
	function newMerchant(){
	    //window.location.href="${ctx}/admin/merchant/newMerchant";
	    showWindow({
			title: '新建餐厅',
      		href : '${ctx}/admin/merchant/newMerchant',
    		width : 500,
    		height : 400,
    		minimizable : false,
    		maximizable : false,
    		collapsible : false,
    		resizable : false,
    		draggable :false,
    		onLoad : function() {
    			$("#address").val("详细的餐厅地址，如：中山路360号102室");
    			$("#description").val("简单介绍下餐厅情况，字数控制在140个汉字内");
    		}
	    });
	}
	
	function todoMerchantIndex(merchantId){
		windowOpen("${ctx}/MerchantView/MerchantIndex/"+merchantId);
	}
	
	function recommend(type,isRecommendText) {
		var rows = $('#merchantIntoTable').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要"+isRecommendText+"的记录", 'info');
			return;
		}
		
		var isRecommend= false;
		var params = '';
		$.each(rows, function(i, n) {
			if(n.recommendStatus==type){
				isRecommend= true;
			}
			if (i == 0) {
				params += "idList=" + n.merchantId ;//+"&isRecommend="+type;
			} else {
				params += "&idList=" + n.merchantId ;//+"&isRecommend="+type;
			}
		});
		
		params +="&isRecommend="+type ;
		
		if(isRecommend){
			$.messager.alert('提示', "餐厅已经被"+isRecommendText+"，请选择未被"+isRecommendText+"的餐厅!");
    		return;
		}
		setRecommendRow(params,isRecommendText);
	}

	
	function setRecommendRow(params,isRecommendText){
		$.messager.confirm('提示', '确定要'+isRecommendText+'吗?', function(result) {
    		if (result) {
    			$.messager.progress({
    				title: '请等待',
    				msg: '',
    				text: '数据处理中，请等待....',
    				interval: 500
    			});
    			
    			$.post("${ctx}/admin/merchant/setRecommendRow",params,
    				function(data){
	    				$.messager.progress('close');
	    				$.messager.alert('提示',isRecommendText+"成功!",'info');
						$('#merchantIntoTable').datagrid("reload");
						$('#merchantIntoTable').datagrid('clearSelections');
    			});
    		}
    	});
	}
	
	
	function singleDeleterow(merchantId, recommendStatus) {
		$('#merchantIntoTable').datagrid('clearSelections');
		
		/* if(recommendStatus=='1'){
			$.messager.alert('提示', "该餐厅被推荐中，请先取消推荐再删除!");
    		return;
		} */
		
		var param = 'merchantIdList=' + merchantId;
		
		deleteMerchantInfo(param);
	}

	//批量删除
	function batchDeleterow() {
		var rows = $('#merchantIntoTable').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择你要删除的记录", 'info');
			return;
		}
		
		var isRecommend= false;
		var params="";
		$.each(rows, function(i, n) {
			/* if(n.recommendStatus=='1'){
				isRecommend= true;
			} */
			if (i == 0) {
				params += "merchantIdList=" + n.merchantId;
			} else {
				params += "&merchantIdList=" + n.merchantId;
			}
		});
		
		/**if(isRecommend){
			$.messager.alert('提示', "该餐厅被推荐中，请先取消推荐再删除!");
    		return;
		} */
		
		deleteMerchantInfo(params);
	}

	function deleteMerchantInfo(params) {
		$.messager.confirm('确认', '确认删除餐厅吗？', function(r) {
			if (r) {
				$.post('${ctx}/admin/merchant/batchDeleteMerchant', params, function(ret) {
					if (ret == 'true') {
						$.messager.alert('信息', '删除成功');
						$('#merchantIntoTable').datagrid('reload');
						$('#merchantIntoTable').datagrid('clearSelections');
					} else if (ret == 'false') {
						$.messager.alert('信息', '删除失败');
					} 
					clearSelect('merchantIntoTable');
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
     	 
		$('#merchantIntoTable').datagrid({pageNumber:1,queryParams:params});
	}

	//清空查询条件
	function clearForm() {
		$('#merchantSearchForm').form('clear');
		searchForm();
	}

	
	function changeMerchantCity(){
		var id=$("#cityName").val();
		$("#area").empty();
		$("#area").append("<option value=''>请选择</option>"); 
		if(id=="无锡"){
			for(i=0;i<wuxiarea.length;i++){
				$("#area").append("<option value='"+wuxiarea[i]+"'>"+wuxiarea[i]+"</option>"); 
			}
		}else if(id=="北京"){
			for(i=0;i<beijingarea.length;i++){
				$("#area").append("<option value='"+beijingarea[i]+"'>"+beijingarea[i]+"</option>"); 
			}
		}else if(id==""){
			$("#area").empty();
			$("#area").append("<option value=''>请选择</option>"); 
		}
	}
	
	
	
	function viewMerchantManagerRow(userId,merchantId){
		$.ajax({
            type: "post",
            url: "${ctx}/admin/merchant/getVerifyMerchantManager2Merchant",
            data: "userId="+userId+"&merchantId="+merchantId,
            success: function (returnData) {
            	 showWindow({
       				title: '餐厅掌柜信息',
              		href : '${ctx}/admin/merchantOwner/viewMerchantOwnerPopWindow',
       	    		width : 500,
       	    		height : 450,
       	    		minimizable : false,
       	    		maximizable : false,
       	    		collapsible : false,
       	    		resizable : false,
       	    		draggable :false,
       	    		onLoad : function() {
       	    			$("#ownerId").val(returnData['userId']);
       	    			$("#nickNameText").html(returnData['nickName']);
       	    			$("#email").html(returnData['email']);
       	    			$("#trueName").html(returnData['trueName']);
       	    			var genderText = "";
       	    			if(returnData['gender']=='m'){ // m--男，f--女,n--未知
       	    				genderText ="男" ;
       	    			}else if(returnData['gender']=='f'){
       	    				genderText ="女" ; 
       	    			}else if(returnData['gender']=='n'){
       	    				genderText ="未知";
       	    			}
       	    			$('#gender').html(genderText);
       	    			//+returnData['address']
       	    			$('#district').html(returnData['cityName']+""+returnData['area']);
       	    			
       	    			$('#linkMan').html(returnData['linkMan']);
       	    			$('#telephoneText').html(returnData['telephone']);
       	    			
       	    			var fileSys='<%=fileSys%>';
       	    			$("#img_preview").attr("src", ctx +returnData['picUrl']);
       	    		    $("#img_preview").attr({width:'160', height:'90'});  
       	    		
       	    			var picUrl = "'"+returnData['picUrl']+"'" ;
       	    		 	var detailStr = '<a href="${ctx}/admin/merchant/download2MerchantInfo/'+returnData['userId']+'/'+returnData['merchantId']+'" style="color: blue;">下载</a>   <a onclick="showOriginalPicUrl('+picUrl+',event);" onmouseout="hideOriginalList()" style="color: blue;">查看原图</a>';
       	    			$("#downOrView").append(detailStr);
       	    		}
       	    	});
            },
            error: function () {
                $.messager.alert('错误', '失败！', "error");
            }
        });
	}
	
	
	function showOriginalPicUrl(picUrl,e){
		var fileSys='<%=fileSys%>';
    	var customerHtml="<img id='showOriginalImage' style='width: 270px; height: 160px;'>";

    	$('#showOriginalImageHtml').css('left', '100px');
    	$('#showOriginalImageHtml').css('top', '100px');
    	$('#showOriginalImageHtml').css('width', '270px');
    	$('#showOriginalImageHtml').css('height', '160px');
    	$('#showOriginalImageHtml').html(customerHtml);
    	$('#showOriginalImageHtml').css('display', 'block');
    	
    	$('#showOriginalImage').attr('src', ctx + picUrl);
	}
	
	function hideOriginalList(){
		$('#showOriginalImageHtml').html('');
    	$('#showOriginalImageHtml').css('display', 'none');
	}
	
	// -------------------------------------------------------------------------------------
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".panel-header").css("width", "auto");
		$(".panel-body").css("width", "auto");
	})
</script>
</head>
<body>
	<div style="background: #fafafa; padding: 10px;" class="table-inner">
		<div class="easyui-panel" title="查询" iconCls="icon-search" collapsible="true">
			<form id="merchantSearchForm">
				<table  height="67">
					<tr>
						<td align="right">所在区域:</td>
						<td><select id="cityName" name="cityName" onchange="changeMerchantCity();">
								<option id="">请选择</option>
								<option id="beijing">北京</option>
								<option id="wuxi">无锡</option>
							</select> 
							<select id="area" name="area">
								<option value=''>请选择</option>
							</select>						</td>
						
						
						<td align="right">餐厅名称:<input name="merchantName" id="merchantName"></td>
						
						<td align="right">掌柜:<input name="manager" id="manager"></td>
					</tr>

					<tr>
						<td align="right">上线时间:(从 )</td>
				    	<td>
							<input class="easyui-datebox" id="startDate" name="startDate">
						&nbsp;</td>
						<td align="right">到 &nbsp; 
							<input class="easyui-datebox" id="endDate" name="endDate">
						</td>
						<td align="center"><a href="#" onClick="searchForm();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a href="#" onClick="clearForm();" class="easyui-linkbutton" iconCls="icon-reset">清空</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div class="table-inner">
		<table id="merchantIntoTable" style="padding: 0px 2px 0px 0px">
		</table>
	</div>
	
	<div id="MyPopWindow" modal="true" shadow="false" cache="false" style="margin: 0px; padding: 0px; overflow: auto;"></div>
</body>
</html>