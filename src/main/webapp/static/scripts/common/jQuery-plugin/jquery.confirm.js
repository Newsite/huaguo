/**
 * @author 史中营
 */
(function($) {

	$.fn.confirm = function(options) {
		// 有缓存问题
		var opts = $.extend($.fn.confirm.defaults, options);
		if(opts.minWidth < 150){
			opts.minWidth=150;
		}
		var html = '<div class="ui-popup-background"><div class="ui-popup-content ui-draggable" style="top: '+$(window).height()/2/2+'px;">';
		html += '<div style="margin: 0; padding: 0; display: inline"><input type="hidden" value="✓" name="utf8"></div>';
		html += '<table cellspacing="0" cellpadding="0" border="0" align="center" class="ui-popup-table">';
		html += '<tbody><tr><td width="25" class="ui-popup-top-l"></td><td class="ui-popup-top">';
		html += '<span class="ui-popup-title" id="ui_confirm_title">' + opts.title + '</span>';
		html += '</td><td width="25" class="ui-popup-top-r"><a class="ui-popup-close" id="ui_confirm_close">关闭</a></tr>';
		html += '<tr><td class="ui-popup-mid-l"></td><td class="ui-popup-mid"><div style="min-width:'+opts.minWidth+'px;">' + opts.html;
		html += '</div><div class="ui-popup-textarea-b"><span id="message_content_error"></span>';
		html += '<br><div class="ui-button ui-button-green ui-button-ajax">';
		html += '<span><button type="submit" id="ui_confirm_ok_button">' + opts.okButtonText + '</button></span>';
		html += '<span><button type="submit" id="ui_confirm_cancel_button">' + opts.cancelButtonText + '</button></span></div></div>';
		html += '</td><td class="ui-popup-mid-r"></td></tr><tr><td class="ui-popup-bottom-l"></td>';
		html += '<td class="ui-popup-bottom"></td><td class="ui-popup-bottom-r"></td></tr></tbody></table></div></div>';
		opts.rhtml = html;

		return this.each(function() {
			$.fn.confirm.init($(this), opts);
		});
	};

	$.fn.confirm.init = function(obj, opts) {
		$(obj).addClass('ui-popup').addClass('ui-popup-textarea').hide();
		$(obj).html('');
		$(obj).html(opts.rhtml);
		$("#ui_confirm_close").click(function() {
			$(obj).hide();
		});
		if(opts.cancelButtonAction!=null){
			$('#ui_confirm_cancel_button').click(function(){
				$(obj).hide();
				opts.cancelButtonAction();
			});
		}else{
			$('#ui_confirm_cancel_button').click(function() {
				$(obj).hide();
			});
		}
		if(opts.okButtonAction!=null){
			$('#ui_confirm_ok_button').click(function(){
				$(obj).hide();
				opts.okButtonAction();
			});
		}else{
			$('#ui_confirm_ok_button').click(function() {
				$(obj).hide();
			});
		}
		$(obj).show();

		if(opts.afterLoad!=null){opts.afterLoad()};
	};

	$.fn.confirm.defaults = {
		'title' : '提示',
		'html' : '您还没有登录哦！立即&nbsp;<a href="javascript:void(0)" onclick="signupOrLogin(\'login\')" title="登录" style="color:#FF6600;text-decoration:none;">登录</a>&nbsp;或&nbsp;'
			+'<a href="javascript:void(0)" onclick="signupOrLogin(\'signup\')" title="注册账号" style="color:#FF6600;text-decoration:none;">注册</a>'
			+'<br><div style="margin-top:5px;">您也可以使用下列帐号登录花果网： </div><p style="margin-top:5px;height:25px;line-height:25px;">'
			+'<img src="'+ctx+'/static/images/WeiboLogoLogin.png" alt="使用新浪微博账号登录" style="display:block;float:left;"/>'
			+'<a href="javascript:void(0);" onclick="$(\'#SinaWeiboLogin\').click();" title="使用新浪微博账号登录" style="color:#FF6600;text-decoration:none;display:block;float:left;">&nbsp;新浪微博账号&nbsp;</a>'
			+'<img src="'+ctx+'/static/images/weiboicon24.png" alt="使用腾讯微博账号登录" style="display:block;float:left;" />'
			+'<a href="javascript:void(0);" onclick="$(\'#QQWeiboLogin\').click();" title="使用腾讯微博账号登录" style="color:#FF6600;text-decoration:none;display:block;float:left;">&nbsp;腾讯微博账号</a></p>',
		'rhtml' : '',
		'minWidth' : 150,
		'okButtonText' : '确认',
		'cancelButtonText' : '取消',
		'okButtonAction' : null,
		'cancelButtonAction' : null,
		'afterLoad' : null
	};
})(jQuery);