Date.prototype.format = function(style) {
	if (style == undefined || style == null)
		style = "yyyy-MM-dd";
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"w+" : "天一二三四五六".charAt(this.getDay()), // week
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(style)) {
		style = style.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(style)) {
			style = style.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return style;
};

String.prototype.createShortDisp = function(text, num) {
	if (num == undefined || num == null)
		num = 10;
	var result = text;
	if (text.length > num)
		result = text.substring(0, num) + "...";
	return "<span title = '" + text + "'>" + result + "</>";
}

function vaildataTextLength(selectId) {
	var description = jQuery.trim($('#' + selectId).val());
	if (description != '' || description != "") {
		if (description.length > 500 || description.length < 1) {
			$.messager.alert('提示', '描述长度必须介于1-500之间！', 'info');
			$('#' + selectId).select();
			return false;
		}
	}
}

/**
 * 常用函数<br>
 * 
 * @author 史中营
 */
var generalUtil = {
	/**
	 * 值为空
	 */
	isNull : function(d) {
		// isNaN未判断
		return (d == null || d == '' || d == 'null' || d == 'NaN');
	},
	isNotNull : function(d) {
		return !this.isNull(d);
	},
	/**
	 * 值为空或空串
	 */
	isBlank : function(d) {
		return ($.trim(d) == '' || $.trim(d) == 'null' || $.trim(d) == 'NaN');
	},
	isNotBlank : function(d) {
		return !this.isBlank(d);
	},
	strLengthCN : function(s) {
		var len = 0;
		for ( var i = 0; i < s.length; i++) {
			if (s.substr(i, 1).match(/[^\x00-\xff]/ig) != null)
				len += 2;
			else
				len += 1;
		}
		return len;
	},
	strForShort : function(s, l, dots) {
		if (dots == null) {
			dots = true;
		}
		if (this.strLengthCN(s) <= l) {
			return s;
		}
		var len = 0;
		var str = '';
		for ( var i = 0; i < s.length; i++) {
			if (s.substr(i, 1).match(/[^\x00-\xff]/ig) != null) {
				len += 2;
			} else {
				len += 1;
			}
			str += s.substr(i, 1);

			if (len >= (dots ? (l - 3) : l)) {
				break;
			}
			/*
			 * if(len > (dots?(l-3):l)){ if(dots) str+='...'; break; }else{
			 * str+=s.substr(i,1); }
			 */
		}
		if (dots)
			str += '...';
		return str;
	},
	getDate : function(dateStr) {
		return new Date(dateStr.replace('-', '/').replace('-', '/'));
	}
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	// unescape
	if (r != null)
		return decodeURI(r[2]);
	return null;
}

// 图片按比例缩放---素材图片页面控制
function newDrawImage2(ImgD, iwidth, iheight) {
	var image = new Image();
	// var iwidth = 200; //定义允许图片宽度
	// var iheight = 150; //定义允许图片高度
	image.src = ImgD.src;
	if (image.width > 0 && image.height > 0) {
		if (image.width / image.height >= iwidth / iheight) {
			if (image.width > iwidth) {
				$(ImgD).css('width', iwidth + 'px');
				$(ImgD).css('height', (image.height * iwidth) / image.width + 'px');
				ImgD.width = iwidth;
				ImgD.height = (image.height * iwidth) / image.width;
			} else {
				$(ImgD).css('width', image.width + 'px');
				$(ImgD).css('height', image.height + 'px');
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
			// ImgD.alt=image.width+"×"+image.height;
		} else {
			if (image.height > iheight) {
				$(ImgD).css('height', iheight + 'px');
				$(ImgD).css('width', (image.width * iheight) / image.height + 'px');
				ImgD.height = iheight;
				ImgD.width = (image.width * iheight) / image.height;
			} else {
				$(ImgD).css('width', image.width + 'px');
				$(ImgD).css('height', image.height + 'px');
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
			// ImgD.alt=image.width+"×"+image.height;
		}
	}
}

//图片按比例放大---素材图片页面控制
function newDrawImageWithWidth(ImgD, iwidth) {
	var image = new Image();
	image.src = ImgD.src;
	if (image.width > iwidth) {
		$(ImgD).css('width', iwidth + 'px');
		$(ImgD).css('height', (image.height * iwidth) / image.width + 'px');
		ImgD.width = iwidth;
		ImgD.height = (image.height * iwidth) / image.width;
	}
}

// 图片按比例放大---素材图片页面控制
function newDrawImage(ImgD, iwidth, iheight) {
	/*
	 * var image=new Image(); image.src=ImgD.src; if(image.width>0 &&
	 * image.height>0){ if(image.width/iwidth >= image.height/iheight){
	 * $(ImgD).css('width', iwidth+'px'); $(ImgD).css('height',
	 * (image.height*iwidth)/image.width+'px'); ImgD.width=iwidth;
	 * ImgD.height=(image.height*iwidth)/image.width; }else{
	 * $(ImgD).css('height', iheight+'px'); $(ImgD).css('width',
	 * (image.width*iheight)/image.height+'px'); ImgD.height=iheight;
	 * ImgD.width=(image.width*iheight)/image.height; } }
	 */
	$(ImgD).css('width', iwidth + 'px');
	ImgD.width = iwidth;
	$(ImgD).css('height', iheight + 'px');
	ImgD.height = iheight;
}

function changepage(url) {
	if (url.indexOf('/index') != -1) {
		var _from = location.pathname;
		if (_from.indexOf('/signup') != -1 || _from.indexOf('/login') != -1) {
			_from = getQueryString('from');
			if (generalUtil.isNotBlank(_from)) {
				_from = _from.replace('/huaguo/', '');
				if (_from.indexOf('/') == 0)
					_from = _from.substring(1);
				window.location.href = ctx + '/' + _from;
				return false;
			}
		}
	}
	window.location.href = url;
}

function windowOpen(url) {
	window.open(url);
}

function item(name, value) {
	var val = "";
	if (window.localStorage) {
		if (value) {
			localStorage.setItem(name, value);
		} else {
			val = localStorage.getItem(name);
		}
	}
	return val == null ? "" : val;
}

function clearSelect(dataTableId) {
	$('#' + dataTableId).datagrid('clearSelections');
	// 取消选择DataGrid中的全选
	$("input[type='checkbox']").eq(0).attr("checked", false);
}

function getUserImageUrl(_url, def) {
	if (generalUtil.isNotBlank(_url)) {
		if (_url.indexOf("sinaimg") == -1 
			&& _url.indexOf("qlogo") == -1
			&& _url.indexOf("gtimg") == -1 
			&& _url.indexOf('http://') == -1
			) {
			_url = ctx+'/'+_url;
		}
	} else {
		if(def==null || def==48){
			_url = ctx+'/static/images/defaultHeadMax.png';
		}else if(def==28){
			_url = ctx+'/static/images/defaultHeadMi.png';
		}else if(def==24){
			_url = ctx+'/static/images/defaultHeadMin.png';
		}
	}
	return _url;
}