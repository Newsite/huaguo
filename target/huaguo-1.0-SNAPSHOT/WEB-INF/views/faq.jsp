<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="eric hui" name="author" />
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐," name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开发结果！" name="description"/>
<meta content="all" name="robots"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/found.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/huaguo-faq.css" media="all" />

<script type="text/javascript" src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>
<script type="text/javascript" >
//<![CDATA[
	$(function(){
	    var $div_li =$("div.tabbable ul li");
	    $div_li.click(function(){
			$(this).addClass("selected")            //当前<li>元素高亮
				   .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
            var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
			$("div.tab-content > div")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
					.eq(index).show()   //显示 <li>元素对应的<div>元素
					.siblings().hide(); //隐藏其它几个同辈的<div>元素
		}).hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		});
		var $rui =$("div.navbar .nav li");
	    $rui.click(function(){
			$(this).addClass("active")            //当前<li>元素高亮
				   .siblings().removeClass("active");  //去掉其它同辈<li>元素的高亮
            var index = $rui.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
			
			/*$("div#home,div#help,div#AboutUs")   
					.eq(index).show()  
					.siblings().hide(); */
					
		}).hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		})
		
		var anchor=getQueryString('anchor');
		if(generalUtil.isNotBlank(anchor)){
			var _lis = $('#tab').children();
			for(var i=0; i<_lis.length; i++){
				if($(_lis[i]).html()==anchor){
					$(_lis[i]).click();
					break;
				}
			}
		}
	})
//]]>
</script>
</head>

<body>
<div id="found">
	<%@ include file="/static/page/header.jsp"%>
	<%@ include file="/static/page/navigation.jsp"%>

  <div id="help" class="faq">
    <div class="container">
      <h3>花果网<strong>FAQ</strong></h3>
      <div class="tabbable  faq-inner">
        <div class="nav-tabs">
          <ul id="tab">
            <li class="selected">关于花果</li>
            <li class="">用户协议</li>
            <li class="">联系我们</li>
            <li class="">友情链接</li>
            <li class="">意见反馈</li>
          </ul>
        </div>
        <div class="tab-content faq-contents">
          <div class="faq-content" style="display: none; ">
            <h4>关于我们</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p>"生活就是一道菜接着另一道菜！" 我们一直尝试着提供一个可以分享生活、感悟生活的知性平台给所有热爱生活的人，我们曾经选择了无数的方向和思路，但最终我们选择了美食，相信热爱生活的你也一定有同样的生活感悟。以美食来感受生活之美，以美食来品味人生百态...... </p>
            </div>
          </div>
          <div class="faq-content hide" style="display: none; ">
            <p>花果网在此特别提醒用户认真阅读本《用户协议》(下称《协议》)中各个条款， 包括免除或限制花果网责任的免责条款及对用户的权利限制。用户需认真审阅并选择是否接受本《协议》（未成年人应在法定监护人陪同下审阅）。用户的使用行为将视为对本协议的接受，并同意接受本协议各项条款的约束。</p>
            <h4>一、服务内容</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p>1、花果网运用自己的系统，通过互联网络等方式为用户提供商户信息、评价信息、消费信息、优惠信息、团购等网络服务。<br />
                2、用户必须自行准备如下设备和承担如下开支：<br />
                （1）上网设备，包括并不限于电脑或者其他上网终端、调制解调器及其他上网装置；<br />
                （2）上网开支，包括并不限于网络接入费、上网设备租用费、手机流量费等。<br />
                3、用户提供的注册资料，用户同意：<br />
                （1）提供合法、真实、准确、详尽的个人资料； <br />
                （2）如有变动，及时更新用户资料。如果用户提供的注册资料不合法、不真实、不准确、不详尽的，用户需承担因此引起的相应责任及后果，并且途拓保留终止用户使用花果网各项服务的权利。 </p>
            </div>
            <h4>二、服务的提供、修改及终止</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p> 本站运用自己的操作系统通过国际互联网络为用户提供网络服务。同时，用户必须: <br />
                1.	自行配备上网的所需设备，包括个人电脑、调制解调器或其他必备上网装置。<br />
                2.	自行负担个人上网所支付的与此服务有关的电话费用、网络费用。<br />
                基于本站所提供的网络服务的重要性，用户应同意：<br />
                1.	保证向本站提供的任何资料、注册信息的真实性、正确性及完整性，包括但不限于邮箱、联系电话等。保证本网站可以通过上述联系方式与用户进行联系；<br />
                2.	当上述资料发生变更时，及时更新用户资料，符合及时、详尽、准确的要求。<br />
                本站对用户的电子邮件、手机号等隐私资料进行保护，承诺不会在未获得用户许可的情况下擅自将用户的个人资料信息出租或出售给任何第三方，但以下情况除外：<br />
                1.	用户同意让第三方共享资料；<br />
                2.	用户同意公开其个人资料，享受为其提供的产品和服务；<br />
                3.	根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；<br />
                4.	本站发现用户违反了本站服务条款或本站其它使用规定。<br />
                关于用户隐私的具体协议以本站的隐私声明为准。
                若本站通过技术检测、人工抽检等手段有合理理由怀疑用户资料信息为错误、不实、失效或不完整，本站有权暂停或终止用户的帐号，并拒绝现在或将来使用本站网络服务的全部或部分，同时保留追索用户不当得利返还的权利。 </p>
            </div>
            <h4>三、隐私说明</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p> 1、花果网承诺：<br />
                非经法定原因或用户事先许可，花果网不会向任何第三方透露用户的密码、姓名、手机号码等非公开信息<br />
                2、在下述法定情况下，用户的个人信息将会被部分或全部披露：<br />
                （1）经用户同意向用户本人或其他第三方披露；<br />
                （2）根据法律、法规等相关规定，或行政机构要求，向行政、司法机构或其他法律规定的第三方披露；<br />
                （3）其它花果网根据法律、法规等相关规定进行的披露。 </p>
            </div>
            <h4>四、用户权利</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p> 1、用户的用户名、密码和安全性<br />
                （1）用户有权选择是否成为花果网会员，用户选择成为花果网注册用户的，可自行创建、修改昵称。用户名和昵称的命名及使用应遵守相关法律法规并符合网络道德。用户名和昵称中不能含有任何侮辱、威胁、淫秽、谩骂等侵害他人合法权益的文字。<br />
                （2）用户一旦注册成功，成为花果网的会员，将得到用户名（用户邮箱）和密码，并对以此组用户名和密码登入系统后所发生的所有活动和事件负责，自行承担一切使用该用户名的言语、行为等而直接或者间接导致的法律责任。<br />
                （3）用户有义务妥善保管花果网账号、用户名和密码，用户将对用户名和密码安全负全部责任。因用户原因导致用户名或密码泄露而造成的任何法律后果由用户本人负责。<br />
                （4）用户密码遗失的，可以通过注册电子邮箱发送的链接重置密码，以手机号码注册的用户可以凭借手机号码找回原密码。用户若发现任何非法使用用户名或存在其他安全漏洞的情况，应立即告知花果网。<br />
                2、用户有权在注册并登陆后对站内商户发布客观、真实、亲身体验的点评信息；<br />
                3、 用户有权在注册并登陆后自行添加商户、完善站内商户的营业时间、交通等信息；<br />
                4、用户有权修改其个人账户中各项可修改信息，自行选择昵称和录入介绍性文字，自行决定是否提供非必填项的内容；<br />
                5用户有权根据花果网网站规定，享受花果网提供的其它各类服务。 </p>
            </div>
            <h4>五、用户义务</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p> 1、不得利用本站危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益，不得利用本站制作、复制和传播下列信息：<br />
                （1）煽动抗拒、破坏宪法和法律、行政法规实施的；<br />
                （2）煽动颠覆国家政权，推翻社会主义制度的；<br />
                （3）煽动分裂国家、破坏国家统一的；<br />
                （4）煽动民族仇恨、民族歧视，破坏民族团结的；<br />
                （5）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；<br />
                （6）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；<br />
                （7）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；<br />
                （8）损害国家机关信誉的；<br />
                （9）其他违反宪法和法律行政法规的；<br />
                （10）进行商业广告行为的。<br />
                2、用户不得通过任何手段恶意注册花果网站帐号，包括但不限于以牟利、炒作、套现、获奖等为目的多个账号注册。用户亦不得盗用其他用户帐号。
                如用户违反上述规定，则花果网有权直接采取一切必要的措施，包括但不限于删除用户发布的内容、取消用户在网站获得的星级、荣誉以及虚拟财富，暂停或查封用户帐号，取消因违规所获利益，乃至通过诉讼形式追究用户法律责任等。<br />
                3、用户需维护点评的客观、真实性，不得利用花果网用户身份进行违反诚信的任何行为，包括但不限于：炒作商户，并向商户收取费用或获取利益；为获得利益或好处，参与或组织撰写及发布虚假点评；以差评威胁，要求商户提供额外的利益或好处；进行其他其它影响点评真实性、客观性、干扰扰乱网站正常秩序的违规行为等。
                如用户违反上述规定，则花果网有权采取一切必要的措施，包括但不限于：删除用户发布的内容，暂停或查封用户帐号，取消因违规所获利益，乃至通过诉讼形式追究用户法律责任等。<br />
                4、禁止用户将花果网以任何形式作为从事各种非法活动的场所、平台或媒介。未经花果网的授权或许可，用户不得借用本站的名义从事任何商业活动，也不得以任何形式将花果网作为从事商业活动的场所、平台或媒介。
                如用户违反上述规定，则花果网有权直接采取一切必要的措施，包括但不限于删除用户发布的内容，暂停或查封用户帐号，取消因违规所获利益，乃至通过诉讼形式追究用户法律责任等。<br />
                5、用户在花果网以各种形式发布的一切信息，均应符合国家法律法规等相关规定及网站相关规定，符合社会公序良俗，并不侵犯任何第三方主体的合法权益，否则用户自行承担因此产生的一切法律后果，且汉涛因此受到的损失，有权向用户追偿。 </p>
            </div>
            <h4>六、拒绝担保与免责</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p> 1、花果网作为“网络服务提供者”的第三方平台，不担保网站平台上的信息及服务能充分满足用户的需求。对于用户在接受花果网的服务过程中可能遇到的错误、侮辱、诽谤、不作为、淫秽、色情或亵渎事件，花果网不承担法律责任。<br />
                2、基于互联网的特殊性，花果网也不担保服务不会受中断，对服务的及时性、安全性都不作担保，不承担非因花果网导致的责任。
                花果网力图使用户能对本网站进行安全访问和使用，但花果网不声明也不保证本网站或其服务器是不含病毒或其它潜在有害因素的；因此用户应使用业界公认的软件查杀任何自花果网下载文件中的病毒。<br />
                3、花果网不对用户所发布信息的保存、修改、删除或储存失败负责。对网站上的非因花果网故意所导致的排字错误、疏忽等不承担责任。
                花果网有权但无义务，改善或更正本网站任何部分之疏漏、错误。<br />
                4、除非花果网以书面形式明确约定，花果网对于用户以任何方式（包括但不限于包含、经由、连接或下载）从本网站所获得的任何内容信息，包括但不限于广告、商户信息、点评内容等，不保证其准确性、完整性、可靠性；对于用户因本网站上的内容信息而购买、获取的任何产品、服务、信息或资料，花果网不承担责任。用户自行承担使用本网站信息内容所导致的风险。<br />
                5、花果网内所有用户所发表的用户点评，仅代表用户个人观点，并不表示本网站赞同其观点或证实其描述，本网站不承担用户点评引发的任何法律责任。<br />
                6、花果有权删除网站内各类不符合法律或协议规定的点评，而保留不通知用户的权利。<br />
                7、所有发给用户的通告，花果网都将通过正式的页面公告、站内信、电子邮件、客服电话、手机短信或常规的信件送达。任何非经花果网正规渠道获得的中奖、优惠等活动或信息，花果网不承担法律责任。 </p>
            </div>
          </div>
          <div class="faq-content hide" style="display: none; ">
            <h4>商家合作</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p>如果你有商家，有合作意向，请将信息发送至：marketing@huaguo.cn <br />
                或者可以电话联系我们，热线电话：400-689-2225 </p>
            </div>
            <h4>其他业务</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p> 其他业务，请将信息发送至：service@touco.cn </p>
            </div>
          </div>
          <div class="faq-content hide" style="display: block; ">
            <h4>友情链接</h4>
            <div class="faq-answer"> <!--<span class="icon-answer"></span>-->
              <p>哇宝(网址：<a href="http://m.waboo.cn">m.waboo.cn</a> )</p>
              <p>vizo(网址：<a href="http://www.vizo.cn">www.vizo.cn</a>)</p>
              <p>途拓(网址：<a href="http://www.touco.cn">www.touco.cn</a>)</p>
            </div>
          </div>
          <div class="faq-content hide" style="display: none; ">
            <h4>意见反馈</h4>
            <div class="faq-answer"><!-- <span class="icon-answer"></span>-->
              <p>欢迎访问我们的网站，你对花果有任何的意见和建议都可以在这里告诉我们，我们会认真听取！</p>
              <p>请将要表达的信息发送至：service@touco.cn</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
<%@ include file="/static/page/footer.jsp"%>
  
  <div id="topBottomBar" class="hidden"> <a href="javascript:void(0)" id="goToTop" class="goToTop">回到顶部</a></div>
</div>
</body>
</html>
