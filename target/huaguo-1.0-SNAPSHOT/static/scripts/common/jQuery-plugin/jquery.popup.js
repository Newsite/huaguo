/**
 * @author 史中营
 */
(function($) {

	$.fn.popup = function(options) {
		// 有缓存问题
		var opts = $.extend($.fn.popup.defaults, options);
		if(opts.minWidth < 150){
			opts.minWidth=150;
		}
		var html = '<div class="ui-popup-background"><div class="ui-popup-content ui-draggable" style="top: '+$(window).height()/2/2+'px;">';
		html += '<div style="margin: 0; padding: 0; display: inline"><input type="hidden" value="✓" name="utf8"></div>';
		html += '<table cellspacing="0" cellpadding="0" border="0" align="center" class="ui-popup-table">';
		html += '<tbody><tr><td width="25" class="ui-popup-top-l"></td><td class="ui-popup-top">';
		html += '<span class="ui-popup-title" id="ui_popup_title">' + opts.title + '</span>';
		html += '</td><td width="25" class="ui-popup-top-r"><a class="ui-popup-close" id="ui_popup_close">关闭</a></tr>';
		html += '<tr><td class="ui-popup-mid-l"></td><td class="ui-popup-mid"><div style="min-width:'+opts.minWidth+'px;">' + opts.html;
		html += '</div><div class="ui-popup-textarea-b"><span id="message_content_error"></span>';
		html += '<br><div class="ui-button ui-button-green ui-button-ajax">';
		html += '<span><button type="submit" id="ui_popup_button">' + opts.buttonText + '</button></span></div></div>';
		html += '</td><td class="ui-popup-mid-r"></td></tr><tr><td class="ui-popup-bottom-l"></td>';
		html += '<td class="ui-popup-bottom"></td><td class="ui-popup-bottom-r"></td></tr></tbody></table></div></div>';
		opts.rhtml = html;

		return this.each(function() {
			$.fn.popup.init($(this), opts);
		});
	};

	$.fn.popup.init = function(obj, opts) {
		$(obj).addClass('ui-popup').addClass('ui-popup-textarea').hide();
		$(obj).html('');
		$(obj).html(opts.rhtml);
		$("#ui_popup_close").click(function() {
			$(obj).hide();
		});
		if(opts.buttonAction!=null){
			$('#ui_popup_button').click(function(){
				opts.buttonAction();
				$(obj).hide();
			});
		}else{
			$('#ui_popup_button').click(function() {
				$(obj).hide();
			});
		}
		$(obj).show();

		if(opts.afterLoad!=null){opts.afterLoad()};
	};

	$.fn.popup.defaults = {
		'title' : '提示',
		'html' : '您还没有登录哦！立即&nbsp;<a href="javascript:void(0)" onclick="signupOrLogin(\'login\')" title="登录" style="color:#FF6600;text-decoration:none;">登录</a>&nbsp;或&nbsp;'
			+'<a href="javascript:void(0)" onclick="signupOrLogin(\'signup\')" title="注册账号" style="color:#FF6600;text-decoration:none;">注册</a>'
			+'<br><div style="margin-top:10px;">您也可以使用下列帐号登录花果网： </div><p style="margin-top:2px;height:25px;line-height:25px;">'
			+'<a href="javascript:void(0);" title="使用新浪微博账号登录"><img src="'+ctx+'/static/images/WeiboLogoLogin.png" alt="使用新浪微博账号登录" onclick="$(\'#SinaWeiboLogin\').click();" style="display:block;float:left;"/></a>'
			+'<a href="javascript:void(0);" title="使用腾讯微博账号登录"><img src="'+ctx+'/static/images/weiboicon24.png" alt="使用腾讯微博账号登录" onclick="$(\'#QQWeiboLogin\').click();" style="display:block;float:left;margin-left:3px;"/></a></p>',
		'rhtml' : '',
		'minWidth' : 150,
		'buttonText' : '关闭',
		'buttonAction' : null,
		'afterLoad' : null
	};
})(jQuery);