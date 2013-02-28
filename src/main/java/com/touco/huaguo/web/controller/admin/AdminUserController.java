package com.touco.huaguo.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.DateUtil;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.common.Md5Util;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.services.IUserService;

/**
 * @author shizhongying 花果 后台 用户管理
 * 
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	@InitBinder
	public void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	@Autowired
	private IUserService adminUserService;

	/**
	 * 
	 * 显示用户的list页面
	 * 
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String showUserListView(HttpServletRequest request) {
		return "admin/userList";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUserListView(HttpServletRequest request) {
		return "admin/addUser";
	}

	/**
	 * 查询所有用户的数据
	 */
	@RequestMapping(value = "/queryAllUserList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllUserList(DataGridModel dgm,
			HttpServletRequest request, UserEntity userEntity) {
		Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
		try {
			Map<String, Object> likesMap = new HashMap<String, Object>();
			if (null != userEntity.getNickName() && !"".equals(userEntity.getNickName())) {
				likesMap.put("nickName", userEntity.getNickName());
			}
			if (null != userEntity.getEmail() && !"".equals(userEntity.getEmail())) {
				likesMap.put("email", userEntity.getEmail());
			}
			queryParams.put(Constants.CRITERIA_LIKE, likesMap);

			Date startDate = null;
			Date endDate = null;
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			if ((null != startDateStr || "".equals(startDateStr))
					|| (null != endDateStr || "".equals(endDateStr))) {
				Date tempStart = DateUtil.getDate(startDateStr);
				Date tempEnd = DateUtil.getDate(endDateStr);
				Calendar startDateTemp = Calendar.getInstance();
				startDateTemp.setTime(tempStart);
				setStartDateHourAndMin(startDateTemp);
				startDate = new Date(startDateTemp.getTimeInMillis());
				Calendar endDateTemp = Calendar.getInstance();
				endDateTemp.setTime(tempEnd);
				setEndDateHourAndMin(endDateTemp);
				endDate = new Date(endDateTemp.getTimeInMillis());
				Map<String, Object> betweenMap = new HashMap<String, Object>();
				List<Date> queryDate = new ArrayList<Date>();
				queryDate.add(startDate);
				queryDate.add(endDate);
				betweenMap.put("createDate", queryDate);
				queryParams.put(Constants.CRITERIA_BETWEEN, betweenMap);
			}
			
			Map<String, Object> equalsMap = new HashMap<String, Object>();
			equalsMap.put("delTag", Constants.NO_DEL_TAG);
			queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
			
			Map<String, Object> result = adminUserService.getPageListByCriteria(dgm, queryParams);
			List<UserEntity> list = (List<UserEntity>) result.get(Constants.PAGE_ROWS);
			if (null != list && !list.isEmpty()) {
				for (UserEntity entity : list) {
					entity.setUserRefs(null);
				}
			}
			return result;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return null;
		}
	}

	private void setStartDateHourAndMin(Calendar startDateTemp) {
		startDateTemp.set(Calendar.HOUR_OF_DAY, 0);
		startDateTemp.set(Calendar.MINUTE, 0);
		startDateTemp.set(Calendar.SECOND, 0);
	}

	private void setEndDateHourAndMin(Calendar endDateTemp) {
		endDateTemp.set(Calendar.HOUR_OF_DAY, 23);
		endDateTemp.set(Calendar.MINUTE, 59);
		endDateTemp.set(Calendar.SECOND, 59);
	}

	/**
	 * 添加用户
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/adminAddOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String adminAddOrUpdate(HttpServletRequest request,
			UserEntity userEntity) {
		String oper = "";
		try {
			if (userEntity.getUserId() != null) {
				userEntity = adminUserService.get(userEntity.getUserId());
				oper = "update";
			} else {
				userEntity = new UserEntity();
				oper = "new";
			}
			adminUserService.saveOrUpdate(userEntity);
			return oper;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return "false";
		}
	}

	/**
	 * 批量删除admin用户
	 * 
	 * @param request
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "/userBatchDelete", method = RequestMethod.POST)
	@ResponseBody
	public String userBatchDelete(HttpServletRequest request,
			@RequestParam("idList") List<String> idList) {
		String result= "false";
		try {
			List<UserEntity> listEntities = new ArrayList<UserEntity>();
			for (String str : idList) {
				UserEntity user = adminUserService.get(Long.parseLong(str));
				user.setDelTag(Constants.DEL_TAG);
				listEntities.add(user);
			}
			if(listEntities!=null&&!listEntities.isEmpty()){
				adminUserService.updateII(listEntities);
				result= "true";
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		return result;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDelTag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateDelTag(HttpServletRequest request,
			@RequestParam("userId") Long userId,
			@RequestParam("delTag") String delTag) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = adminUserService.get(userId);
			if (Constants.DEL_TAG.equals(delTag)) {
				user.setDelTag(Constants.NO_DEL_TAG);
			} else if (Constants.NO_DEL_TAG.equals(delTag)) {
				user.setDelTag(Constants.DEL_TAG);
			}
			adminUserService.update(user);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "操作失败");
			throw e;
		}
		return map;
	}

	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveOrUpdate(UserEntity user,
			HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			resetEntity(user);

			if (adminUserService.checkUserNickName(user.getNickName(), "0")) {
				map.put("error", "true");
				map.put("msg", "昵称已经被占用了，重新起一个吧");
				return map;
			}

			if (adminUserService.checkUserEmail(user.getEmail(), "0")) {// 检测邮箱
				map.put("error", "true");
				map.put("msg", "邮箱已经被使用");
				return map;
			}

			if (user.getUserId() == null || user.getUserId() == 0) {
				user.setCreateDate(new Date());
				user.setRegType("0");
				user.setDelTag(Constants.NO_DEL_TAG);
				// request.getSession().setAttribute(Constants.LOG_SESSION_INFO,
				// "新增用户");
			} else {
				UserEntity user2 = adminUserService.get(user.getUserId());
				user.setCreateDate(user2.getCreateDate());
				user.setDelTag(user2.getDelTag());
				user.setUpdateDate(new Date());
				user.setRegType(user2.getRegType());
				// request.getSession().setAttribute(Constants.LOG_SESSION_INFO,
				// "编辑用户");
			}
			user.setPassword(Md5Util.md5(user.getPassword()));

			adminUserService.saveOrUpdate(user);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "操作失败");
			throw e;
		}
		return map;
	}

	private void resetEntity(UserEntity user) {
		if (user.getCity().getCityId() == null
				|| "".equals(user.getCity().getCityId().toString())) {
			user.setCity(null);
		}

		if (user.getProvince().getCityId() == null
				|| "".equals(user.getProvince().getCityId().toString())) {
			user.setProvince(null);
		}
	}

}
