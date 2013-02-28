/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.controller.MessageController.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午9:18:36
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.services.INotificationsService;
import com.touco.huaguo.web.util.GeneralUtils;

/**
 * @author shizhongying
 * 
 * 
 */
@Controller
public class NotificationController {

	@Autowired
	private INotificationsService notificationsService;

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public ModelAndView showNotificationsView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Long userId = getUserId(request);
		if (null == userId || userId == 0) {
			mav.setViewName("redirect:/login");
		} else {
			mav.setViewName("notifications");
		}
		return mav;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notifications/updataIsRead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updataIsRead(HttpServletRequest request,
			@RequestParam("recordId") Long recordId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			NotificationsEntity notificat = notificationsService.get(recordId);
			notificat.setIsRead(Constants.MESSAGE_READED);
			notificat.setUpdateDate(new Date());
			notificationsService.update(notificat);
			map.put("msg", "操作成功");
			map.put("error", "1");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 取当前登录人员的ID
	 * 
	 * @param request
	 * @return
	 */
	private Long getUserId(HttpServletRequest request) {
		Long userId = 0L;
		UserEntity userSession = null;
		if (request.getSession().getAttribute(Constants.USER_SESSION_INFO) != null) {
			userSession = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
			userId = userSession.getUserId();
		}

		return userId;
	}

	/**
	 * 获取通知列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notifications/getMyNoticeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyNoticeList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int page = 1;
			String pageStr = request.getParameter("page");
			if (pageStr != null && !"".equals(pageStr)) {
				page = Integer.valueOf(pageStr);
			}
			String isRead = request.getParameter("isRead");
			Long userId = getUserId(request);
			if (null == userId || userId == 0) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			} else {
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
				DataGridModel dgm = new DataGridModel();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("receiver.userId", userId);
				equalsMap.put("isRead",isRead);// 0 -- 未读
				if(Constants.MESSAGE_NOT_READED.equals(isRead)){
					dgm.setOrder("desc");
					dgm.setSort("createDate");
				}else if(Constants.MESSAGE_READED.equals(isRead)){
					dgm.setOrder("desc");
					dgm.setSort("updateDate");
				}
				equalsMap.put("type", Constants.MESSAGE_NOTIFICATIONS); // 0 -- 通知
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

				
				dgm.setRows(10);
				dgm.setPage(page);

				Map<String, Object> messageData = notificationsService.getPageListByCriteria(dgm, queryParams);
				List<NotificationsEntity> list = (List<NotificationsEntity>) messageData.get(Constants.PAGE_ROWS);
				List<NotificationsEntity> pageList = new ArrayList<NotificationsEntity>();
				if (CollectionUtils.isNotEmpty(list)) {
					for (NotificationsEntity tm : list) {
						NotificationsEntity messageEntity = new NotificationsEntity();
						if (tm.getSender() == null
								|| tm.getSender().getUserId() == 0) {
							messageEntity.setSender(null);
						} else {
							tm.getSender().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, tm.getSender());
							tue.setPassword(null);
							messageEntity.setSender(tue);
						}
						if (tm.getReceiver() == null
								|| tm.getReceiver().getUserId() == 0) {
							messageEntity.setReceiver(null);
						} else {
							tm.getReceiver().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, tm.getReceiver());
							tue.setPassword(null);
							messageEntity.setReceiver(tue);
						}

						messageEntity.setRecordId(tm.getRecordId());
						messageEntity.setShowDate(GeneralUtils.getShowTime(tm.getCreateDate()));
						messageEntity.setContent(tm.getContent());
						messageEntity.setCommentType(tm.getCommentType());
						messageEntity.setIsRead(tm.getIsRead());
						messageEntity.setType(tm.getType());
						if (tm.getCategory() != null) {
							messageEntity.setCategory(tm.getCategory());
						}
						if (tm.getIsVerify() != null) {
							messageEntity.setIsVerify(tm.getIsVerify());
						}
						if (tm.getMerchantId() != null) {
							messageEntity.setMerchantId(tm.getMerchantId());
						}
						if (tm.getMerchantName() != null) {
							messageEntity.setMerchantName(tm.getMerchantName());
						}
						pageList.add(messageEntity);
					}
					map.put(Constants.PAGE_ROWS, pageList);
					map.put("total", messageData.get(Constants.PAGE_TOTAL));
				} else {
					map.put(Constants.PAGE_ROWS, new ArrayList<NotificationsEntity>());
					map.put("total", messageData.get(Constants.PAGE_TOTAL));
				}
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		return map;
	}

	

	

	

}
