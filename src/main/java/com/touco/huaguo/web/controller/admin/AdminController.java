package com.touco.huaguo.web.controller.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.common.Md5Util;
import com.touco.huaguo.common.PropertiesUtil;
import com.touco.huaguo.domain.AdminEntity;
import com.touco.huaguo.services.admin.IAdminService;


/**
 * @author shizhongying
 *  花果管理员后台
 *
 */
@Controller
public class AdminController {

	// private static Logger log = Logger.getLogger(AdminController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	@Autowired
	private IAdminService adminService;
	
	
	
	/**
	 * 管理后台登录
	 * 
	 * @param user
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/adminLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public String adminlogin(AdminEntity user, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		/*if (request.getSession() != null && request.getSession().getAttribute(Constants.ADMIN_SESSION_INFO) != null) {
			return "admin/adminLogin";
		}*/

		if (user.getUserName() == null || "".equals(user.getUserName())) {
			model.addAttribute("message", "登陆超时，请重新登录");
			//return "redirect:/admin"; 
			return "admin/adminLogin";
		}

		String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("txt/html");
		String captchaReceived = request.getParameter("captcha");
		if (captchaReceived == null || !captchaReceived.equalsIgnoreCase(kaptchaExpected)) {
			model.addAttribute("message", "验证码错误");
			//return "redirect:/admin"; 
			return "admin/adminLogin";
		}

		List<AdminEntity> userList = adminService.findByNamedParam("userName", user.getUserName());
		if (userList == null || userList.isEmpty()) {
			model.addAttribute("message", "用户不存在");
			//return "redirect:/admin";  
			return "admin/adminLogin";
		}
		AdminEntity user1 = userList.get(0);
		String temppassword = Md5Util.md5(user.getPassword().trim());
		String temppassword2 = user1.getPassword();
		
		if (user.getPassword() == null || !temppassword.equals(temppassword2)) {
			model.addAttribute("message", "密码错误");
			
			//return "redirect:/admin";   
			return "admin/adminLogin";
		} else {
			//request.getSession().removeAttribute(Constants.CUSTOMER_ACCOUNT_SESSION_INFO);
			request.getSession().setAttribute(Constants.ADMIN_SESSION_INFO, user1);
			/************************ FTP Server Domain ***********************************/
			Properties properties = PropertiesUtil.getProperties(Constants.FTP_CONFIG_FILE);
			request.getSession().setAttribute(Constants.FILE_SYS_DOMAIN, properties.getProperty("ftp.server"));
			/************************ FTP Server Domain ***********************************/

			// 设置cookie
			String host = request.getServerName();
			if ("on".equals(user.getIsSave())) {
				Cookie unameCookie = new Cookie(Constants.COOKIE_SERVER_UNAME, user.getUserName());
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

			return "admin/adminIndex";
		}
	}
	
	
	
	/**
	 * 管理后台注销
	 * Function  : 注销操作
	 * @author   : shizhongying
	 * @throws IOException 
	 */
	@RequestMapping(value="/adminLogout",method=RequestMethod.POST)
	public ModelAndView doLogout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		Enumeration<?> enums = request.getSession().getAttributeNames();
		while(enums.hasMoreElements()){
			request.getSession().removeAttribute(enums.nextElement().toString());
			request.getSession().removeAttribute(Constants.ADMIN_SESSION_INFO);
		}
		request.getSession().invalidate();
		/*response.getWriter().write("<script language=javascript>window.top.location.href="+request.getContextPath()+"'/login.jsp'</script>");
		response.sendRedirect(request.getContextPath() + "/login.jsp");*/
		//return "admin/adminLogin";
		modelAndView.setViewName("redirect:/admin");
		
		return modelAndView;
	}
	
	
	/**
	 * 
	 * 显示admin的list页面
	 * 
	 */
	@RequestMapping(value = "/admin/adminList", method = RequestMethod.GET)
	public ModelAndView adminAccountList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/adminList");
		return modelAndView;
	}
	
	
	

	/**
	 * 查询所有admin的数据
	 * 
	 */
	@RequestMapping(value = "/admin/queryList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryList(DataGridModel dgm, AdminEntity administratorEntity) {
		Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

		Map<String, Object> likesMap = new HashMap<String, Object>();
		likesMap.put("userName", administratorEntity.getUserName());
		queryParams.put(Constants.CRITERIA_LIKE, likesMap);

		try {
			return adminService.getPageListByCriteria(dgm, queryParams);
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return null;
		}
	}

	
	
	
	/**
	 * 添加admin用户
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/adminAddOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String adminAddOrUpdate(HttpServletRequest request, 
			@RequestParam("userId") long adminId, 
			@RequestParam("userName") String userName, 
			@RequestParam("adminPassword") String adminPassword) {
		AdminEntity adminiEntity = new AdminEntity();
		String oper = "";
		try {
			if (adminId != 0) {
				adminiEntity = adminService.get(adminId);
				oper = "update";
			} else {
				String password = Md5Util.md5(adminPassword);
				adminiEntity.setPassword(password);
				oper = "new";
			}
			adminiEntity.setUserName(userName);
			adminService.saveOrUpdate(adminiEntity);
			return oper;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return "false";
		}
	}

	/**
	 * @author 史中营
	 * @param idList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/restorePwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> restorePwd(@RequestParam("idList") String[] idList, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (null != idList && idList.length != 0) {
				List<AdminEntity> list = new ArrayList<AdminEntity>();
				for (int i = 0; i < idList.length; i++) {
					AdminEntity accountUpdate = adminService.get(Long.valueOf(idList[i]));
					accountUpdate.setPassword(Md5Util.md5("123456"));
					list.add(accountUpdate);
				}
				adminService.updateII(list);
			}
			map.put("msg", "还原成功");
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			map.put("msg", "还原失败");
		}
		return map;
	}

	/**
	 * 批量删除admin用户
	 * 
	 * @param request
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "/admin/adminBatchDelete", method = RequestMethod.POST)
	@ResponseBody
	public String adminBatchDelete(HttpServletRequest request, @RequestParam("idList") List<String> idList) {
		try {
			List<Long> list = new ArrayList<Long>();
			for (String str : idList) {
				list.add(Long.parseLong(str));
			}
			adminService.removeAll(list);
			return "true";
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return "false";
		}
	}

	/**
	 * 检查是否有相同记录
	 * 
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/admin/checkSame", method = RequestMethod.POST)
	@ResponseBody
	public String checkSameRecord(HttpServletRequest request, @RequestParam("userId") Long userId, @RequestParam("paramName") String paramName, @RequestParam("value") String value) {
		try {
			List<AdminEntity> list =adminService.findByNamedParam(paramName, value);
			if (CollectionUtils.isEmpty(list)) {
				return "true";
			} else if (list.size() == 1 && list.get(0).getUserId() == userId) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return "false";
		}
	}

	
	
	@RequestMapping(value = "/modifyAdminPwd", method = RequestMethod.GET)
	public String modifyAdminPwd(HttpServletRequest request) throws Exception {
		return "admin/modifyAdminPwd";
	}
	


	@RequestMapping(value = "/admin/addAdmin", method = RequestMethod.GET)
	public String addAdmin(HttpServletRequest request) throws Exception {
		return "admin/addAdmin";
	}
	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAccount", method = RequestMethod.POST)
	@ResponseBody
	public Boolean getAccount(AdminEntity user) throws Exception {

		Boolean isFlag = Boolean.FALSE;
		try {
			AdminEntity user2 = adminService.get(user.getUserId());
			String password = Md5Util.md5(user.getPassword());
			if (user2 != null && user2.getPassword().equals(password)) {// md5.MD5Encode())
				isFlag = Boolean.TRUE;
			} else {
				isFlag = Boolean.FALSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isFlag = Boolean.FALSE;
		}

		return isFlag;
	}
	
	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/modifyAdminPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> modifyAdminPwd(AdminEntity user, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			AdminEntity user2 = adminService.get(user.getUserId());
			String password = Md5Util.md5(user.getPassword());
			user2.setPassword(password);
			adminService.saveOrUpdate(user2);
			request.getSession().setAttribute(Constants.ADMIN_SESSION_INFO, user2);
			map.put("msg", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "修改失败");
		}
		return map;
	}

}
