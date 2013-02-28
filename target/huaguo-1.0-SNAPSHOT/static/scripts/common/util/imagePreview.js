if (window.navigator.userAgent.toLowerCase().indexOf("firefox")>=1) {
		HTMLElement.prototype.__defineSetter__("outerHTML", function(sHTML) {
			var r = this.ownerDocument.createRange();
			r.setStartBefore(this);
			var df = r.createContextualFragment(sHTML);
			this.parentNode.replaceChild(df, this);
			return sHTML;
		});

		HTMLElement.prototype.__defineGetter__("outerHTML", function() {
			var attr;
			var attrs = this.attributes;
			var str = "<" + this.tagName.toLowerCase();
			for ( var i = 0; i < attrs.length; i++) {
				attr = attrs[i];
				if (attr.specified)
					str += " " + attr.name + '="' + attr.value + '"';
			}
			if (!this.canHaveChildren)
				return str + ">";
			return str + ">" + this.innerHTML + "</"
					+ this.tagName.toLowerCase() + ">";
		});

		HTMLElement.prototype.__defineGetter__("canHaveChildren", function() {
			switch (this.tagName.toLowerCase()) {
			case "area":
			case "base":
			case "basefont":
			case "col":
			case "frame":
			case "hr":
			case "img":
			case "br":
			case "input":
			case "isindex":
			case "link":
			case "meta":
			case "param":
				return false;
			}
			return true;

		});
}

function showImg(imgObj,imgPreviewId,imgDivId){		
	if(imgObj.value){
		if(checkImgType(imgObj.value)){
			$('#'+imgDivId).show();
			var target = $('#' + imgDivId);			
			var src = null;
        	if(imgObj.files){
	    		if (window.URL.createObjectURL) {
				　　//FF4+
				　　src = window.URL.createObjectURL(imgObj.files[0]);
				} else if (window.webkitURL.createObjectURL) {
				　　//Chrome8+
				　　src = window.webkitURL.createObjectURL(imgObj.files[0]);
				}
        		
        		if($('#' + imgPreviewId).length){
        			$('#' + imgPreviewId).attr('src', src);
        		} else {
        			var width = $(target).css('width');
        			var height = $(target).css('height'); 
        			target.html("<img src='" + src + "' width='"+width+"' height='"+height+"'/>");
        		}
        	} else {
        		var width = $(target).css('width');
    			var height = $(target).css('height');        		
        		var imgDiv = document.createElement("div");
        		target.html(imgDiv);
            	imgDiv.style.width = width;
            	imgDiv.style.height = height;
            	imgDiv.className = "preview_image";
            	imgDiv.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = scale)";
            	imgDiv.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgObj.value;
        	}
		} else {
			$.messager.alert('提示', "您上传的好像不是图片文件！", 'info');
			imgObj.outerHTML += "";
			imgObj.value="";
		}
	}
}

function checkImgType(imgValue){
	if(imgValue.match(/^.*(\.jpg|\.JPG|\.gif|\.GIF|\.bmp|\.BMP|\.png|\.PNG|\.JPEG|\.jpeg)$/i)){
		return true;
	}
	return false;
}
