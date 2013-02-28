<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/page/admin/jqueryeasy.jsp"%>
<script type="text/javascript" src="${ctx}/static/scripts/common/util/imagePreview.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/kindeditor-min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/area.js"></script>

<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	var editor;
	KindEditor.ready(function(K) {
		var item = [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print',
				'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|',
				'justifyleft', 'justifycenter', 'justifyright', 'justifyfull',
				'insertorderedlist', 'insertunorderedlist', 'indent',
				'outdent', 'subscript', 'superscript', 'clearhtml',
				'quickformat', 'selectall', '/', 'formatblock', 'fontname',
				'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic',
				'underline', 'strikethrough', 'lineheight', 'removeformat',
				'|', 'image', 'table', 'hr', 'emoticons', , 'pagebreak',
				'anchor', 'link', 'unlink', '|', 'about' ]
		editor = K.create('#editor_id', {
			width : '600px',
			height : '416px',
			items : item,
			resizeType : 0,
			uploadJson : '${ctx}/merchant/uploadEditorImg'
		});

	});

	function saveMerchant() {
		if ($("#name").val() == "") {
			$("#nameMsg").html("请填写餐厅名称");
			return;
		}
		if ($("#name").val().length > 24) {
			$("#nameMsg").html("餐厅名称须小于25个字符");
			return;
		}
		if ($("#merchantStyle").val() == "") {
			$("#styleMsg").html("请选择一个菜系");
			return;
		}
		if ($("#cityName").val() == "请选择" || $("#cityName").val() == "") {
			$("#areaMsg").html("请选择所在城市");
			return;
		}
		if ($("#area").val() == "" || $("#area").val() == "请选择") {
			$("#areaMsg").html("请选择所在区域");
			return;
		}
		if ($("#address").val() == "") {
			$("#addressMsg").html("请填写餐厅详细地址");
			return;
		}
		if ($("#address").val().length > 200) {
			$("#addressMsg").html("餐厅地址须小于200个字符");
			return;
		}
		if ($("#imageUrl").val() == "") {
			$("#imageMsg").html("请上传餐厅招牌图片");
			return;
		}
		if ($("#description").val() == "") {
			$("#descriptionMsg").html("请简单介绍下餐厅");
			return;
		}
		var descLength = namelen($("#description").val());
		if (descLength > 280) {
			$("#descriptionMsg").html("餐厅介绍须小于140个汉字或者280个字符");
			return;
		}
		if ($("#tel").val() == "") {
			$("#telMsg").html("请填写餐厅电话");
			return;
		}
		if (!checkTel($("#tel").val())) {
			$("#telMsg").html("请正确填写餐厅电话");
			return;
		}
		
		editor.sync();
		var content = $('#editor_id').val();
		$('#content').val(content);
		if (content == "") {
			alert("请填写餐厅详细信息");
			return;
		}
		
		var fields = $("#contentForm").serializeArray();
		
		$.post("${ctx}/merchant/saveOrUpdate", fields, function(data) {
			if (data.error != null) {
				if (data.error == "1") {
					alert(data.msg);
				}
				if (data.error == "2") {
				}
			} else {
			}
		});
	}
	
	
	function changeMerchantCity2(){
		var id=$("#cityName2").val();
		$("#area2").empty();
		$("#area2").append("<option value=''>请选择</option>"); 
		alert(1111);
		if(id=="无锡"){
			for(i=0;i<wuxiarea.length;i++){
				$("#area2").append("<option value='"+wuxiarea[i]+"'>"+wuxiarea[i]+"</option>"); 
			}
		}else if(id=="北京"){
			for(i=0;i<beijingarea.length;i++){
				$("#area2").append("<option value='"+beijingarea[i]+"'>"+beijingarea[i]+"</option>"); 
			}
		}else if(id==""){
			$("#area2").empty();
			$("#area2").append("<option value=''>请选择</option>"); 
		}
	}

</script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#address").val("详细的餐厅地址，如：中山路360号102室");
		$("#description").val("简单介绍下餐厅情况，字数控制在140个汉字内");
	})
</script>
</head>
<body>
	<div class="easyui-tabs" fit="false" plain="false" border="true" style="height: 450px;">
		<div title="餐厅介绍" style="padding: 10px; width: auto; overflow: auto;">
			<form id="contentForm">
				<input type="hidden" name="merchantId" id="merchantId">
				<table>
					<tr>
						<td class="textr">餐厅名称：</td>
						<td colspan="3"><input name="name" id="name"
							validtype="lengthCN[2,50]" class="easyui-validatebox"
							required="true" style="width: 150px"></td>
					</tr>
					<tr>
						<td class="textr">所属菜系：</td>
						<td colspan="3">
							<select style="width: 140px" id="merchantStyle" name="merchantStyle">
								<option id="">请选择菜系</option>
								<option id="江浙菜" value="江浙菜">江浙菜</option>
								<option id="川菜" value="川菜">川菜</option>
								<option id="湘菜" value="湘菜">湘菜</option>
								<option id="粤港菜" value="粤港菜">粤港菜</option>
								<option id="东南亚菜" value="东南亚菜">东南亚菜</option>
								<option id="日韩料理" value="日韩料理">日韩料理</option>
								<option id="自助餐" value="自助餐">自助餐</option>
								<option id="海鲜" value="海鲜">海鲜</option>
								<option id="小吃快餐" value="小吃快餐">小吃快餐</option>
								<option id="火锅" value="火锅">火锅</option>
								<option id="西餐" value="西餐">西餐</option>
								<option id="烧烤烤肉" value="烧烤烤肉">烧烤烤肉</option>
								<option id="面包甜点" value="面包甜点">面包甜点</option>
								<option id="其他" value="其他">其他</option>
						</select>
						</td>
					</tr>
					<tr>
						<td class="textr">所在地区:</td>
						<td colspan="3">
							<select  style="width: 140px" id="cityName2" name="cityName" onchange="changeMerchantCity2();">
								<option id="">请选择</option>
								<option id="beijing">北京</option>
								<option id="wuxi">无锡</option>
							</select> 
							<select id="area2" name="area" style="width: 140px"></select>
						</td>
					</tr>
					<tr>
						<td class="textr">详细地址：</td>
						<td colspan="3" width="450px"><textarea class="neutral"
								id="address" name="address"
								style="resize: none; height: auto !important; width: 404px; height: 70px; min-height: 60px"
								onfocus="if(value=='详细的餐厅地址，如：中山路360号102室') {value=''}"
								onblur="if (value=='') {value='详细的餐厅地址，如：中山路360号102室'}"></textarea>
						</td>
					</tr>
					<tr>
						<td class="textr">餐厅招牌:：</td>
						<td colspan="3" width="450px"><input
							style="width: 200px; display: inline-block; float: left;"
							type="file" id="uploadImg" name="uploadImg"
							onchange="uploadMerchantImg(this);" /> <input type="hidden"
							id="imageUrl" name="imageUrl" /></td>
					</tr>

					<tr>
						<td></td>
						<td colspan="3" width="450px">
							<div class="dns_up_slide">
								<table width="100%">
									<tr>
										<td style="padding: 5px 0;">
											<div id="img_preview_div_1" class="dns_up_slidePic2 preview_fake"
												style="width: 180px; height:90px;">
												<img id="img_preview1" name="img_preview1" style="width: 180px; height: 90px;" />
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>

					<tr>
						<td class="textr">餐厅简介:</td>
						<td colspan="3" width="450px"><textarea class="neutral"
								id="description" name="description"
								style="resize: none; height: auto !important; width: 404px; height: 70px; min-height: 60px"
								onfocus="if(value=='简单介绍下餐厅情况，字数控制在140个汉字内') {value=''}"
								onblur="if (value=='') {value='简单介绍下餐厅情况，字数控制在140个汉字内'}"></textarea>
						</td>
					</tr>
					<tr>
						<td class="textr">餐厅电话:</td>
						<td colspan="3" width="450px"><input class="neutral"
							type="text" name="tel" id="tel" /></td>
					</tr>
					<tr>
						<td class="textr">人均消费:</td>
						<td colspan="3" width="450px"><select id="priceId"
							name="priceRegion.priceId">
								<option id="">请选择</option>
								<option value="1">30以内</option>
								<option value="2" selected>30-50</option>
								<option value="3">50-80</option>
								<option value="4">80-120</option>
								<option value="5">120-180</option>
								<option value="6">180-250</option>
								<option value="7">250以上</option>
						</select></td>
					</tr>

				</table>
			</form>
		</div>
		<div title="详细信息" id="detailInfo"
			style="padding: 10px; width: auto; overflow: auto;">
			<div class="editor-inside">
				<textarea id="editor_id" style="width: 404px; height: 70px">
                 	&lt;strong&gt;关于餐厅&lt;/strong&gt;
                 	&lt;br&gt;
                 	在这边你可以编写餐厅的故事，比如说创建的心路历程、餐厅名称的来由、还有对于餐厅的整体规划。
                 	&lt;br&gt;&lt;br&gt;                 	
                 	&lt;strong&gt;关于味道&lt;/strong&gt;
                 	&lt;br&gt;在这边你可以来说说餐厅的菜式，特色招牌菜。可以图文并茂的介绍店内独创的特色佳肴
                 	</textarea>
				<input type="hidden" id="content" name="content" value="" />
			</div>
		</div>
	</div>

	<div class="Control">
		<a href="#" id="btn-add" onClick="saveCreditInfo();"
			class="easyui-linkbutton" iconcls="icon-save">保存</a>&nbsp;&nbsp;&nbsp;
		<a href="#" id="btn-back" onclick="returnList();"
			class="easyui-linkbutton" iconcls="icon-back">返回列表</a>
	</div>
</body>
</html>