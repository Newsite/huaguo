/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.controller.UserController.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-21上午9:31:44
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.common.FileUploadUtil;
import com.touco.huaguo.common.FileUtil;
import com.touco.huaguo.common.Md5Util;
import com.touco.huaguo.common.PropertiesUtil;
import com.touco.huaguo.common.RandomUtil;
import com.touco.huaguo.domain.CityEntity;
import com.touco.huaguo.domain.TokenEntity;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.domain.UserRef;
import com.touco.huaguo.services.ICityService;
import com.touco.huaguo.services.IMessageService;
import com.touco.huaguo.services.ITokenService;
import com.touco.huaguo.services.IUserService;
import com.touco.huaguo.web.util.SendMailHelper;

/**
 * @author shizhongying
 * 
 * 
 */
@Controller
public class UserController {

	private Md5Util md5 = null;
	
	private FileUploadUtil fileUploadUtil = new FileUploadUtil();

	private static Logger logger = Logger.getLogger(UserController.class);
	
	private IUserService userService;
	
	private IMessageService messageService;
	
	@Autowired
	private ICityService cityService;
	
	
	@Autowired
	public void setIUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
	private ITokenService tokenService ;
	
	@Autowired
	public void setITokenrService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }
	@Autowired
	public void setIMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}



	// ****************   shizhongying   Start       ********************
	/**
	 * 显示注册页面
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView showRegisterView(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signup");
		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLoginView(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	

	/**
	 * 用户站内注册
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(UserEntity user,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			String email = user.getEmail();
			String regType = request.getParameter("regType").toUpperCase();
			if(StringUtils.isBlank(email)){
				resultMap.put("emailIsExist", "true");
				resultMap.put("emailMsg", "邮箱不能为空");
				return resultMap;
			}
			if (userService.checkUserEmail(email, regType)) {//检测邮箱
				resultMap.put("emailIsExist", "true");
				resultMap.put("emailMsg", "邮箱已经被使用");
				return resultMap;
			}
			
			String nickName = user.getNickName();//检测昵称
			if(StringUtils.isBlank(nickName)){
				resultMap.put("nickNameIsExist", "true");
				resultMap.put("nickNameMsg", "昵称不能为空");
				return resultMap;
			}
			if (userService.checkUserNickName(nickName, regType)) {
				resultMap.put("nickNameIsExist", "true");
				resultMap.put("nickNameMsg", "昵称已经被占用了，重新起一个吧");
				return resultMap;
			}
			
			UserEntity saveUser = new UserEntity();
			saveUser.setEmail(user.getEmail());
			md5 = new Md5Util();
			saveUser.setPassword(md5.md5(user.getPassword()));
			saveUser.setCreateDate(new Date());
			saveUser.setEmail(user.getEmail());
			saveUser.setNickName(user.getNickName());
			saveUser.setRegType("0");// 站内用户
			saveUser.setDelTag(Constants.NO_DEL_TAG);//启用
			saveUser.setGender(Constants.DEFAULT_GENDER);//默认未知性别
			userService.saveOrUpdate(saveUser);

			resultMap.put("result", "true");
			
			Integer messageCount  = messageService.getAllUnreadCountByUserId(saveUser.getUserId());
			
			request.getSession().setAttribute(Constants.USER_SESSION_INFO,saveUser); // 便于判断登录的人是否掌柜
			request.getSession().setAttribute(Constants.UNICKNAME_SESSION,saveUser.getNickName());
			request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION, saveUser.getImageUrl());
			request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT, messageCount);
			request.getSession().setAttribute(Constants.REG_TYPE, "0");
		} catch (Exception e) {
			resultMap.put("result", "false");
		}
		return resultMap;
	}

	/**
	 * 登录
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("isRemember") String isRemember) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			if (!userService.checkUserEmail(email, "0")) {//检测邮箱
				resultMap.put("emailIsExist", "true");
				resultMap.put("emailMsg", "邮箱不存在");
				return resultMap;
			}
			
			UserEntity user = userService.checkUserIsExist(email,Md5Util.md5(password));
			if(null==user)
			{
				user = userService.checkUserIsExist(email,password);	
			}		
			if (user != null && user.getUserId() != 0) {
				Integer messageCount  = messageService.getAllUnreadCountByUserId(user.getUserId());
				
				resultMap.put("nickName", user.getNickName());
				resultMap.put("utoken", user.getPassword());
				resultMap.put("imageUrl", user.getImageUrl());
				
				resultMap.put(Constants.PRIVATE_LETTER_COUNT, messageCount);//私信

				request.getSession().setAttribute(Constants.USER_SESSION_INFO,user);
				request.getSession().setAttribute(Constants.UNICKNAME_SESSION,user.getNickName());
				request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION, user.getImageUrl());
				request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT, messageCount);
				request.getSession().setAttribute(Constants.REG_TYPE, "0");
				// 设置cookie
				String host = request.getServerName();
				if ("on".equals(isRemember)) {
					Cookie unameCookie = new Cookie(Constants.COOKIE_SERVER_UNAME, user.getEmail());
					unameCookie.setPath("/");
					unameCookie.setDomain(host);
					unameCookie.setMaxAge(365 * 24 * 60 * 60);
					response.addCookie(unameCookie);

					Cookie pwdCookie = new Cookie(Constants.COOKIE_SERVER_UPWD, user.getPassword());
					pwdCookie.setPath("/");
					pwdCookie.setDomain(host);
					pwdCookie.setMaxAge(365 * 24 * 60 * 60);
					response.addCookie(pwdCookie);
				} else {
					Cookie[] cookies = request.getCookies();
					for (Cookie cookie : cookies) {
						if (!cookie.getName().equals("JSESSIONID")) {
							Cookie cookie2 = new Cookie(cookie.getName(), null);
							cookie2.setPath("/");
							cookie2.setDomain(host);
							cookie2.setMaxAge(0);
							response.addCookie(cookie2);
						}
					}
				}
				resultMap.put("result", "true");
				
			} else {
				resultMap.put("result", "false");
			}
		} catch (Exception e) {
			logger.error(UserController.class.getName() + ":执行logon方法"
					+ e.getMessage());
		}
		return resultMap;

	}
	
	
	/**
	 * 自动登录
	 * 
	 * @param request
	 * @param response
	 * @param uname
	 * @param utoken
	 * @return
	 */
	@RequestMapping(value = "/autologon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> autoLogon(HttpServletRequest request, 
			@RequestParam("email") String email, 
			@RequestParam("passwd") String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "false");
		try {
			UserEntity user = userService.checkUserIsExist(email,Md5Util.md5(password));

			if (user != null && user.getUserId() != 0) {
				resultMap.put("result", "true");
				resultMap.put("userInfo", user);
				Integer messageCount  = messageService.getAllUnreadCountByUserId(user.getUserId());
				
				resultMap.put("nickName", user.getNickName());
				resultMap.put("utoken", user.getPassword());
				resultMap.put("imageUrl", user.getImageUrl());
				
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,user);
				request.getSession().setAttribute(Constants.UNICKNAME_SESSION,user.getNickName());
				request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION, user.getImageUrl());
				request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT, messageCount);
				request.getSession().setAttribute(Constants.REG_TYPE, "0");
			}
		} catch (Exception e) {
			logger.error(UserController.class.getName() + ":执行autologon方法" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 微博登录
	 * 1:新浪微博  - 2:腾讯微博   TODO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/WeiboAuth")
	@ResponseBody
	public Map<String, Object> WeiboAuth(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String screenName = request.getParameter("screenName");
			//String screenName2= new String(screenName.getBytes("ISO8859_1"), "GBK"); 
			String screenName2= URLDecoder.decode(screenName,"UTF-8");
			
			String profileImageUrl = request.getParameter("profileImageUrl");
			String gender = request.getParameter("gender");
			String webPage = request.getParameter("homepage");
			String regType =  request.getParameter("regType");
			
			if(profileImageUrl!=null&& profileImageUrl.indexOf("qlogo") > -1){//腾讯微博头像图片
				profileImageUrl =profileImageUrl+"/30";
			}else if(profileImageUrl==null || profileImageUrl.indexOf("gtimg")> -1){
				profileImageUrl ="http://mat1.gtimg.com/www/mb/img/p1/head_normal_30.png";
			}
			//如果站内用户 绑定的微博帐号登录
			UserRef ur= userService.checkUserRefNickName(screenName2, regType);
			if(ur!=null){
				Long userId = ur.getUser().getUserId();
				UserEntity user = userService.get(userId);
				//绑定用户
				if(user!=null&&("0").equals(user.getRegType())){
					Integer messageCount  = messageService.getAllUnreadCountByUserId(user.getUserId());
					request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT, messageCount);
					request.getSession().setAttribute(Constants.USER_SESSION_INFO,user);
					request.getSession().setAttribute(Constants.UNICKNAME_SESSION, user.getNickName());
					request.getSession().setAttribute(Constants.REG_TYPE, "0");
					request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION, user.getImageUrl());
					resultMap.put("result", "true");
					return resultMap;
				}
			}
			
			//未绑定的微博
			if (userService.checkUserNickName(screenName2, regType)) {//如果已经存在则直接登录 
				UserEntity userEntity = userService.getUserNickName(screenName2,regType);
				Integer messageCount  = messageService.getAllUnreadCountByUserId(userEntity.getUserId());
				request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT, messageCount);
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,userEntity);
				request.getSession().setAttribute(Constants.UNICKNAME_SESSION, userEntity.getNickName());
				request.getSession().setAttribute(Constants.REG_TYPE, regType);
				request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION, userEntity.getImageUrl());
			}else{
				// 插入数据到系统数据库
				UserEntity saveUser = new UserEntity();
				saveUser.setCreateDate(new Date());
				saveUser.setNickName(screenName2);
				if("1".equals(gender)||Constants.DEFAULT_MALE.equals(gender)){//腾讯微博 性别 1 ： 男  2：女 
					gender =Constants.DEFAULT_MALE; //m--男，f--女,n--未知
				}else if("2".equals(gender)||Constants.DEFAULT_FEMALE.equals(gender)){
					gender=Constants.DEFAULT_FEMALE;
				}else{
					gender=Constants.DEFAULT_GENDER;
				}
				saveUser.setGender(gender);
				saveUser.setImageUrl(profileImageUrl);
				saveUser.setRegType(regType);
				saveUser.setWebPage(webPage);
				saveUser.setDelTag(Constants.NO_DEL_TAG);
				//	saveUser.setCity(city);
				UserRef newUr = new UserRef();
				newUr.setApptype(regType);
				newUr.setCreateDate(new Date());
				newUr.setNickname(saveUser.getNickName());
				newUr.setUser(saveUser);
				saveUser.getUserRefs().add(newUr);
				
				userService.saveOrUpdate(saveUser);
				
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,saveUser);
				Integer messageCount  = messageService.getAllUnreadCountByUserId(saveUser.getUserId());
				request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT, messageCount);
				request.getSession().setAttribute(Constants.UNICKNAME_SESSION, screenName2);
				request.getSession().setAttribute(Constants.REG_TYPE, regType);
				request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION, profileImageUrl);
			}
			resultMap.put("result", "true");
		} catch (Exception e) {
			resultMap.put("result", "false");
		}
		return resultMap;
	}
	
	
	/**
	 * 绑定微博
	 * 1:新浪微博  - 2:腾讯微博   TODO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/buildWeiboAuth")
	@ResponseBody
	public Map<String, Object> buildWeiboAuth(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserEntity userSession = null;
			if (request.getSession().getAttribute(Constants.USER_SESSION_INFO) != null) {
				userSession = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
				userSession=userService.get(userSession.getUserId());
				String screenName = request.getParameter("screenName");
				String screenName2= URLDecoder.decode(screenName,"UTF-8");
				String regType =  request.getParameter("regType");
				if (userService.checkUserNickName(screenName2, regType)) {//如果已经存在则直接登录 
					resultMap.put("result", "false");
					resultMap.put("msg", "该用户已经被使用");
					return resultMap;
				}
				//检查绑定表是否已经存在
				UserRef ur= userService.checkUserRefNickName(screenName2, regType);
				if(ur!=null) {
					resultMap.put("result", "false");
					resultMap.put("msg", "该用户已经绑定");
				}else{
					UserRef newUr = new UserRef();
					newUr.setApptype(regType);
					newUr.setCreateDate(new Date());
					newUr.setNickname(screenName2);
					newUr.setUser(userSession);
					userSession.getUserRefs().add(newUr);
					userService.saveOrUpdate(userSession);
					//request.getSession().setAttribute(Constants.USER_SESSION_INFO,userSession);
					//request.getSession().setAttribute(Constants.REG_TYPE, regType);
					resultMap.put("regType", regType);
					resultMap.put("refId", newUr.getRefId());
					resultMap.put("result", "true");
				}
			}else{
				resultMap.put("msg", "请重新登录");
				resultMap.put("result", "relogin");
			}
		} catch (Exception e) {
			resultMap.put("result", "false");
		}
		return resultMap;
	}
	
	/**
	 * 微博绑定初始化
	 * 1:新浪微博  - 2:腾讯微博   TODO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/initBuildWeiboAuth")
	@ResponseBody
	public Map<String, Object> initBuildWeiboAuth(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
			if(user!=null){
				Long userId = user.getUserId();
				List<UserRef> urlist =	userService.getUserRefsByUserId(userId );
				if (CollectionUtils.isNotEmpty(urlist)) {
					for (UserRef u : urlist) {
						if (u.getUser() == null||u.getUser().getUserId()==0) {
							u.setUser(null);
						} else {
							u.getUser().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, u.getUser());
							tue.setPassword(null);
							u.setUser(tue);
						}
					}
					resultMap.put("list", urlist);
				}else{
					resultMap.put("list", Collections.emptyList());
				}
			}else{
				resultMap.put("msg", "请重新登录");
				resultMap.put("result", "false");
			}
		} catch (Exception e) {
			resultMap.put("result", "false");
		}
		return resultMap;
	}
	
	
	/**
	 * 微博绑定初始化
	 * 1:新浪微博  - 2:腾讯微博   TODO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelBuildingWeiboAuth")
	@ResponseBody
	public Map<String, Object> cancelBuildingWeiboAuth(HttpServletRequest request,
			@RequestParam("refId") Long refId,
			@RequestParam("regType") String regType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
			if(user!=null){
				if(userService.removeUserRef(refId)){
					resultMap.put("regType", regType);
					resultMap.put("result", "true");
				}else{
					resultMap.put("result", "false");
				}
			}else{
				resultMap.put("msg", "请重新登录");
				resultMap.put("error", "1");
				resultMap.put("result", "false");
			}
		} catch (Exception e) {
			resultMap.put("result", "false");
		}
		return resultMap;
	}

	/**
	 * 检测邮箱是否已存在
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check_email_is_exist")
	@ResponseBody
	@Deprecated
	public Map<String, Object> checkEmailIsExist(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String email = request.getParameter("email").toUpperCase();
			String regType = request.getParameter("regType").toUpperCase();
			if (userService.checkUserEmail(email, regType)) {
				map.put("emailIsExist", "true");
				map.put("msg", "邮箱已经被使用");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}

	/**
	 * 检测昵称是否已存在
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/check_nickName_is_exist")
	@ResponseBody
	@Deprecated
	public Map<String, Object> checkNickNameIsExist(HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			String nickName = request.getParameter("nickName").toUpperCase();
			String regType = request.getParameter("regType").toUpperCase();

			if (userService.checkUserNickName(nickName, regType)) {
				ret.put("nickNameIsExist", "true");
				ret.put("msg", "昵称已经被使用");
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	private static String getIpAddress(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	
	/**
	 * 显示个人设置页面
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/myAccount", method = RequestMethod.GET)
	public ModelAndView showMyAccountView(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("settings");
		return modelAndView;
	}
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogout(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<?> enums = request.getSession().getAttributeNames();
		while (enums.hasMoreElements()) {
			request.getSession().removeAttribute(enums.nextElement().toString());
			request.getSession().removeAttribute(Constants.USER_SESSION_INFO);
			request.getSession().removeAttribute(Constants.UNICKNAME_SESSION);
			request.getSession().removeAttribute(Constants.PROFILE_ImageUrl_SESSION);
			request.getSession().removeAttribute(Constants.PRIVATE_LETTER_COUNT);//清空个人私信信息
			request.getSession().removeAttribute(Constants.REG_TYPE);
		}
		request.getSession().invalidate();

		Map<String, Object> map = new HashMap<String, Object>();
		/************************ FTP Server Domain ***********************************/
		if (request.getSession().getAttribute(Constants.FTP_CONFIG_FILE) == null) {
			Properties properties = null;
			try {
				properties = PropertiesUtil.getProperties(Constants.FTP_CONFIG_FILE);
			} catch (IOException e) {
				ExceptionUtil.exceptionParse(e);
			}
			request.getSession().setAttribute(Constants.FILE_SYS_DOMAIN, properties.getProperty("ftp.server"));
		}
		/************************ FTP Server Domain ***********************************/
		map.put("result", "true");
		return map;
	}
	
	
	// ****************   shizhongying    End       ********************
	
	
	/**
	 * 密码找回
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/recovery", method = RequestMethod.GET)
	public ModelAndView recovery(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("recovery");
		return modelAndView;
	}
	
	
	/**
	 * 密码找回
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/resetPwd/success", method = RequestMethod.GET)
	public ModelAndView resetPwdSuccess(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("success");
		return modelAndView;
	}
	
	/**
	 * 密码找回
	 * 
	 * @return
	 * @throws Exception 
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/doRecovery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doRecovery(HttpServletRequest request,
			@RequestParam("email") String email) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		//查询输入的email 是否存在，不存在就提示email不存在,存在的话就发送邮件
		UserEntity user = userService.getUserByEmail(email);
		if(null!=user){
			//生成随即的一串地址后缀
			String randomStr = RandomUtil.getRandomString(50);
			TokenEntity token = new TokenEntity();
			token.setCreateDate(new Date());
			token.setEmail(email);
			token.setTokenString(randomStr);
			token.setIsUse("0");
			tokenService.saveOrUpdate(token);
			Properties mailpropertie = PropertiesUtil.getProperties(Constants.DS_CONFIG_FILE);
			String link =mailpropertie.getProperty("servername") +request.getContextPath()+"/token/checkToken?token="+randomStr;
			String path = "<a href='"+link+"'>"+link+"</a>";
			//发送邮件
			String imagePath=mailpropertie.getProperty("servername") +request.getContextPath()+"/static/images/menu-logo.png";
		    SendMailHelper smtpMailSender = new SendMailHelper();
		    String emailSubjectTxt = "花果网密码修改";
		    String emailMsgTxt = "<img src='"+imagePath+"'/><br>你好，"+user.getNickName()+":<br>点击以下链接，重置登录密码:</br>"+ path+"<br>该链接在30分钟内有效，逾期失效。请尽快修改密码。";
		    try {
				smtpMailSender.postMail( new String[]{email}, emailSubjectTxt, emailMsgTxt, "");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    
		    result.put("msg", "邮件已发出!");
		}else{
			result.put("msg", "邮箱地址不存在!");
		}
		return result;
	}
	
	
	/**
	 * 密码找回
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/token/checkToken", method = RequestMethod.GET)
	public ModelAndView checkToken(HttpServletRequest request,
			@RequestParam("token") String token) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		//查询输入的token是否存在或者是超时，正常的话则跳转到修改密码页面其余跳到error页面
		if(token!=null&&!"".equals(token)){
			TokenEntity tokenEntity = tokenService.getTokenByString(token);
			if (tokenEntity != null) {
				Date tokenDate = tokenEntity.getCreateDate();
				Calendar now = Calendar.getInstance();
				now.add(Calendar.MINUTE, -30);
				if (tokenDate.after(now.getTime())) {
					modelAndView.addObject("email", tokenEntity.getEmail());
					modelAndView.setViewName("doRecovery");// 链接到修改密码页面
				} else{// 早于现在减去30分钟，说明已超时
					modelAndView.setViewName("error");// 表示链接已超时
				}
			} else {
				modelAndView.setViewName("error");// 表示链接不存在
			}
		} else {
			modelAndView.setViewName("error");// 表示链接不存在
		}
		return modelAndView;
	}
	
	
	/**
	 * 密码找回
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/doResetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doResetPassword(HttpServletRequest request,
			@RequestParam("email") String email,
			@RequestParam("password") String password) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		//查询输入的email 是否存在，不存在就提示email不存在,存在的话就发送邮件
		UserEntity user = userService.getUserByEmail(email);
		try {
			if(null!=user){	
				user.setPassword(md5.md5(password));
				userService.saveOrUpdate(user);
				tokenService.updateTokenUse(email);
				//自动为其登陆
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,
						user);
				request.getSession().setAttribute(Constants.UNICKNAME_SESSION,
						user.getNickName());
				request.getSession().setAttribute(
						Constants.PROFILE_ImageUrl_SESSION, user.getImageUrl());
				
				result.put("error", "0");
			    result.put("msg", "修改成功!");
			}
			else{
				result.put("error", "1");
				result.put("msg", "修改失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", "2");
			result.put("msg", "修改失败!");
		}
		return result;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);		
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			} else {
				UserEntity tempuser = userService.get(user.getUserId());
				List<CityEntity> cityList = cityService.findAll("0001");
				List<Map> cityMapList = new ArrayList<Map>();
				for (CityEntity city : cityList) {
					Map temp = new HashMap();
					temp.put("cityId", city.getCityId());
					temp.put("cityName", city.getAreaName());
					cityMapList.add(temp);
				}
				// 获取城市所在省内的城市信息
				if (tempuser.getProvince() != null){
					List<CityEntity> tempCityList = cityService
							.findAll(tempuser.getProvince().getCityId());
					List<Map> tempcityMapList = new ArrayList<Map>();
					for (CityEntity city : tempCityList) {
						Map temp = new HashMap();
						temp.put("cityId", city.getCityId());
						temp.put("cityName", city.getAreaName());
						tempcityMapList.add(temp);
					}
					map.put("cityList", tempcityMapList);
				}
				map.put("userId", tempuser.getUserId());
				map.put("email", tempuser.getEmail());
				map.put("nickName", tempuser.getNickName());
				map.put("trueName", tempuser.getTrueName());
				map.put("imageUrl", tempuser.getImageUrl());
				map.put("provinceId", tempuser.getProvince() == null ? ""
						: tempuser.getProvince().getCityId());
				map.put("cityId", tempuser.getCity() == null ? "" : tempuser
						.getCity().getCityId());
				map.put("gender", tempuser.getGender());
				map.put("webPage", tempuser.getWebPage());
				map.put("provinceList", cityMapList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}		
	
	
	/**
	 * 获取用户信息
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/user/getCityList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCityList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String provinceId = request.getParameter("provinceId");
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			} else {
				List<CityEntity> cityList = cityService.findAll(provinceId);
				List<Map> cityMapList = new ArrayList<Map>();
				for (CityEntity city : cityList) {
					Map temp = new HashMap();
					temp.put("cityId", city.getCityId());
					temp.put("cityName", city.getAreaName());
					cityMapList.add(temp);
				}
				map.put("cityList", cityMapList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}		
	
	
	
	/**
	 * 更新用户信息
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserInfo(UserEntity tempUser,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);		
			if (null == user){
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}else{
				if (userService.checkUserNickName(user.getUserId(),tempUser.getNickName(), "0")){
					map.put("msg", "昵称已经被占用了，重新起一个吧");
					map.put("error", "2");
					return map;
				}
				
				UserEntity updateUser = userService.get(user.getUserId());
				updateUser.setNickName(tempUser.getNickName());
				if("".equals(tempUser.getProvince().getCityId())){
					updateUser.setProvince(null);
				}else{
					updateUser.setProvince(tempUser.getProvince());
				}
				if(null==tempUser.getCity()||"".equals(tempUser.getCity().getCityId())){
					updateUser.setCity(null);
				}else{
					updateUser.setCity(tempUser.getCity());
				}				
				updateUser.setGender(tempUser.getGender());
				updateUser.setTrueName(tempUser.getTrueName());
				updateUser.setWebPage(tempUser.getWebPage());
				updateUser.setUpdateDate(new Date());		
				userService.saveOrUpdate(updateUser);
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,updateUser);
				request.getSession().setAttribute(Constants.UNICKNAME_SESSION,updateUser.getNickName());
				map.put("msg", "更新成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "更新失败");
		}
		return map;
	}		
	
	
	/**
	 * 更新用户信息
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/user/delUserImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delUserImage(UserEntity tempUser,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);		
			if (null == user)
			{
					map.put("msg", "登录超时，请重新登录");
					map.put("error", "1");
					return map;
			}
			else
			{
				
				UserEntity updateUser = userService.get(user.getUserId());
				updateUser.setImageUrl("");
				userService.saveOrUpdate(updateUser);
				request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION,null);
				map.put("msg", "更新成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "更新失败");
		}
		return map;
	}	
	
	
	/**
	 * 更新用户邮箱
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/user/updateUserEmail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserEmail(HttpServletRequest request) {
		String email = request.getParameter("email");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);		
			if (null == user)
			{
					map.put("msg", "登录超时，请重新登录");
					map.put("error", "1");
					return map;
			}
			else
			{
				if (userService.checkUserEmail(user.getUserId(),email, "0"))
				{
					map.put("msg", "邮箱已经被占用了，重新设置一个吧");
					map.put("error", "2");
					return map;
				}
				
				UserEntity updateUser = userService.get(user.getUserId());
				updateUser.setEmail(email);		
				userService.saveOrUpdate(updateUser);
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,updateUser);		
				map.put("msg", "邮箱设置成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "邮箱设置失败");
		}
		return map;
	}		
	
	
	/**
	 * 更新用户邮箱
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/user/resetpassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetpassword(HttpServletRequest request) {
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);		
			if (null == user)
			{
					map.put("msg", "登录超时，请重新登录");
					map.put("error", "1");
					return map;
			}
			else
			{
				String oldMd5 = Md5Util.md5(oldpassword);				
				UserEntity updateUser = userService.get(user.getUserId());
				if(!oldMd5.equals(updateUser.getPassword()))
				{
					map.put("msg", "旧密码不正确");
					map.put("error", "2");
					return map;
				}
				String newMd5 = Md5Util.md5(newpassword);
				updateUser.setPassword(newMd5);
				userService.saveOrUpdate(updateUser);
				request.getSession().setAttribute(Constants.USER_SESSION_INFO,updateUser);		
				map.put("msg", "密码修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "密码修改失败");
		}
		return map;
	}		
	
	
	@RequestMapping(value = "/user/uploadUserImg", method = RequestMethod.POST)
	@ResponseBody
	public String uploadUserImg(HttpServletRequest request, HttpServletResponse response) 
	{
		UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);		
		if (null == user)
		{
				return "登录超时，请重新登录";
		}
		String filePath = "";
		try {
			if (request instanceof MultipartHttpServletRequest) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("uploadImg");
				filePath = fileUploadUtil.saveFile2(request.getRealPath("/"), file.getOriginalFilename(), file.getInputStream());
				UserEntity updateUser = userService.get(user.getUserId());
				if(null!=filePath&&!"".equals(filePath))
				{
					// 上传图片
					File tempFile = new File(request.getRealPath("/") + filePath);
					if (tempFile.exists()) {
						updateUser.setImageUrl(filePath);
						//fileUploadUtil.fileUpload(tempFile.getName(), FileUtil.getByte(tempFile), 2, false));
					}
				}	
				userService.saveOrUpdate(updateUser);
				request.getSession().setAttribute(Constants.PROFILE_ImageUrl_SESSION,updateUser.getImageUrl());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;
	}
	
	
	// ****************   史中营    Start     ********************
	@RequestMapping("/user/setCitySession")
	@ResponseBody
	public String setCitySession(HttpServletRequest request, String city){
		request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, city);
		return Constants.RESULT_SUCCESS;
	}

	@RequestMapping("/user/getCitySession")
	@ResponseBody
	public Map<String, Object> getCitySession(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		String cityName = Constants.USER_DEFAULT_CITY;
		if (request.getSession() != null && request.getSession().getAttribute(Constants.USER_CITY_SESSION_INFO) != null) {
			cityName = (String) request.getSession().getAttribute(Constants.USER_CITY_SESSION_INFO);
		}else{
			request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, Constants.USER_DEFAULT_CITY);
		}
		result.put("city", cityName);
		return result;
	}
	// ****************   史中营    End       ********************
	
}
