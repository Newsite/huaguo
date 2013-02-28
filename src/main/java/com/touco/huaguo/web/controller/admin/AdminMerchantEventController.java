/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.controller.admin.AdminMerchantEventController.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-23下午5:04:29
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.DateUtil;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.domain.MerchantCommentEntity;
import com.touco.huaguo.domain.MerchantEventEntity;
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.services.IMerchantCommentService;
import com.touco.huaguo.services.IMerchantEventService;
import com.touco.huaguo.services.INotificationsService;
import com.touco.huaguo.web.dto.MerchantEventDto;

/**
 * @author shizhongying 
 * 花果餐厅动态审核后台
 * 
 */
@Controller
public class AdminMerchantEventController {

	// private static Logger log = Logger.getLogger(AdminController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	@InitBinder
	public void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	
	private IMerchantEventService adminMerchantEventService;

	@Autowired
	public void setIMerchantEventService(IMerchantEventService adminMerchantEventService) {
		this.adminMerchantEventService = adminMerchantEventService;
	}

	@Autowired
	private INotificationsService notificationsService;


	@Autowired
	private IMerchantCommentService merchantCommentService;

	/**
	 * 
	 * 
	 */
	@RequestMapping(value = "/admin/merchantEvent/merchantEventList", method = RequestMethod.GET)
	public ModelAndView adminMerchantEventList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/merchantEventList");
		return modelAndView;
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/merchantEvent/verifyMerchantEventPopWindow", method = RequestMethod.GET)
	public String verifyMerchantEventPopWindow(HttpServletRequest request) {
		return "admin/verifyMerchantEvent";
	}

	/**
	 * 查询餐厅动态
	 * 
	 */
	@RequestMapping(value = "/admin/merchantEvent/merchantEventQueryList", 
			method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> merchantEventQueryList(DataGridModel dgm,
			MerchantEventDto merchantEventDto, 
			HttpServletRequest request) {
		
		Map<String, Map<String, Map<String, Object>>> queryParams = new HashMap<String, Map<String, Map<String, Object>>>();
		Map<String, Map<String, Object>> eventMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> userLikeMap = new HashMap<String, Object>();
		if (merchantEventDto.getNickName() != null) {
			userLikeMap.put("nickName",  merchantEventDto.getNickName());
			eventMap.put(Constants.CRITERIA_LIKE, userLikeMap);
			queryParams.put("createUser", eventMap);
		}
		if (merchantEventDto.getMerchantName() != null) {
			userLikeMap.put("name", merchantEventDto.getMerchantName());
			eventMap.put(Constants.CRITERIA_LIKE, userLikeMap);
			queryParams.put("merchant", eventMap);
		}
		
		Map<String, Map<String, Object>> thisMap = new HashMap<String, Map<String, Object>>();
		Date startDate = null;
		Date endDate = null;
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		if ((null != startDateStr || "".equals(startDateStr)) || (null != endDateStr || "".equals(endDateStr))) {
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
			thisMap.put(Constants.CRITERIA_BETWEEN, betweenMap);
		}
		
		Map<String, Object> equalsMap = new HashMap<String, Object>();
		equalsMap.put("status", merchantEventDto.getStatus());
		thisMap.put(Constants.CRITERIA_EQUALS, equalsMap);
		queryParams.put(Constants.RELEVANCE_NULL, thisMap);
		
		try {
		
			dgm.setOrder(Constants.PAGE_ORDER_DESC);
			if(dgm.getSort()==null){
				dgm.setSort("createDate");
			}

			Map<String, Object> result = adminMerchantEventService.getPageListByManyEntity(dgm, queryParams, null);
			List<MerchantEventEntity> entitylist = (List<MerchantEventEntity>) result.get(Constants.PAGE_ROWS);
			List<MerchantEventDto> dtoList = new ArrayList<MerchantEventDto>();
			if (CollectionUtils.isNotEmpty(entitylist)) {
				for (MerchantEventEntity tmpEntity : entitylist) {
					MerchantEventDto tmpDto = new MerchantEventDto();
					tmpDto.setRecordId(tmpEntity.getRecordId());
					if(tmpEntity.getCreateUser()!=null){
						tmpDto.setNickName(tmpEntity.getCreateUser().getNickName());
						tmpDto.setEmail(tmpEntity.getCreateUser().getEmail());
					}
					if(tmpEntity.getMerchant()!=null){
						tmpDto.setMerchantName(tmpEntity.getMerchant().getName());
						tmpDto.setMerchantId(tmpEntity.getMerchant().getMerchantId());
					}
					tmpDto.setCreateDate(tmpEntity.getCreateDate());// 提交时间
					tmpDto.setStatus(tmpEntity.getStatus());// 状态
					tmpDto.setVerifyDate(tmpEntity.getVerifyDate());// 审核时间
					dtoList.add(tmpDto);
				}
			}
			result.put(Constants.PAGE_ROWS, dtoList);
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
	 * 批量删除
	 * 
	 * @param request
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "/admin/merchantEvent/merchantEventBatchDelete", method = RequestMethod.POST)
	@ResponseBody
	public String adminBatchDelete(HttpServletRequest request,
			@RequestParam("idList") List<String> idList) {
		try {
			List<Long> list = new ArrayList<Long>();
			for (String str : idList) {
				list.add(Long.parseLong(str));
			}

			adminMerchantEventService.removeAll(list);

			return "true";
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return "false";
		}
	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/merchantEvent/getVerifyMerchantEvent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVerifyMerchantEvent(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String recordId = request.getParameter("recordId");
			MerchantEventEntity tmpEntity =adminMerchantEventService.get(Long.parseLong(recordId));
			if(tmpEntity!=null){
				if(tmpEntity.getCreateUser()!=null){
					map.put("nickName", tmpEntity.getCreateUser().getNickName());
					map.put("email",tmpEntity.getCreateUser().getEmail());
				}
				if(tmpEntity.getMerchant()!=null){
					map.put("merchantId",Long.parseLong(tmpEntity.getMerchant().getMerchantId().toString()));
					map.put("merchantName",tmpEntity.getMerchant().getName());
					map.put("merchantStyle", tmpEntity.getMerchant().getMerchantStyle());
					map.put("cityName", tmpEntity.getMerchant().getCityName());
					map.put("area", tmpEntity.getMerchant().getArea());
					map.put("address", tmpEntity.getMerchant().getAddress());
					map.put("description", tmpEntity.getMerchant().getDescription());
				}
				Date createDate = tmpEntity.getCreateDate();
				if (null != createDate) {
					map.put("createDate", sdf2.format(createDate));
				} else {
					map.put("createDate", "");
				}
				// 动态类型:0-店长说(餐厅动态),1-团购优惠,2-促销活动
				map.put("eventType", tmpEntity.getEventType());
				map.put("eventlink", tmpEntity.getEventlink());
				map.put("content", tmpEntity.getContent());
				map.put("recordId", Long.parseLong(recordId));
				map.put("status",tmpEntity.getStatus());// 状态
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "");
		}
		return map;
	}
	
	
	/**
	 * 申请审核
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/merchantEvent/verifyMerchantEventIsPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyMerchantEventIsPass(HttpServletRequest request
			,@RequestParam("recordId") Long recordId
			,@RequestParam("isVerify")  String isVerifyStatus
			,@RequestParam("reason")  String reason
			) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MerchantEventEntity tmpEntity =adminMerchantEventService.get(recordId);
			if(tmpEntity!=null){
				tmpEntity.setStatus(isVerifyStatus);//1-已通过,2-未通过
				tmpEntity.setVerifyDate(new Date());
				adminMerchantEventService.update(tmpEntity);
				//通知
				NotificationsEntity notificationsEntity = new NotificationsEntity();
				notificationsEntity.setCreateDate(new Date());
				notificationsEntity.setType(Constants.MESSAGE_NOTIFICATIONS);//通知
				notificationsEntity.setIsRead(Constants.MESSAGE_NOT_READED);
				notificationsEntity.setCategory(Constants.CATEGORY_EVENT_VERIFY);  
				
				if(("1").equals(isVerifyStatus)){
					/**
					 * @author 史中营
					 */
					/********************************通过审核后，同步评论表*****************************/
					MerchantCommentEntity mc = new MerchantCommentEntity();
					if (tmpEntity.getEventType().equals(Constants.MERCHANT_OWNER_SAY)) {
						mc.setContent(StringUtils.replace(Constants.MERCHANT_COMMENT_OWNER_SAY, "xxx", tmpEntity.getCreateUser().getNickName()));
						mc.setCommentType(Constants.MERCHANT_OWNER_SAY);
						
						notificationsEntity.setCommentType(Constants.MERCHANT_OWNER_SAY);
					
					} else if (tmpEntity.getEventType().equals(Constants.MERCHANT_GROUP_BUY)) {
						mc.setContent(StringUtils.replace(Constants.MERCHANT_COMMENT_GROUP_BUY, "xxx", tmpEntity.getCreateUser().getNickName()));
						mc.setCommentType(Constants.MERCHANT_GROUP_BUY);
						
						notificationsEntity.setCommentType(Constants.MERCHANT_GROUP_BUY);
						
					} else if (tmpEntity.getEventType().equals(Constants.MERCHANT_PROMOTION)) {
						mc.setContent(StringUtils.replace(Constants.MERCHANT_COMMENT_PROMOTION, "xxx", tmpEntity.getCreateUser().getNickName()));
						mc.setCommentType(Constants.MERCHANT_PROMOTION);
						
						notificationsEntity.setCommentType(Constants.MERCHANT_PROMOTION);
					}
					mc.setMerchant(tmpEntity.getMerchant());
					mc.setCreateDate(new Date());
					mc.setParentId(0L);
					mc.setSender(tmpEntity.getCreateUser());
					this.merchantCommentService.saveOrUpdate(mc);
					/*********************************************************************************/
					 //0--餐厅信息审核  1-- 动态审核 2--掌柜审核 3 评价 4 掌柜收到评价
					if(tmpEntity.getMerchant()!=null){
						notificationsEntity.setMerchantId(tmpEntity.getMerchant().getMerchantId());
						notificationsEntity.setMerchantName(tmpEntity.getMerchant().getName());
					}
					notificationsEntity.setIsVerify(isVerifyStatus);
					
					map.put("msg", "提交申请成功");
				}else if(("2").equals(isVerifyStatus)){
					notificationsEntity.setContent(reason);
					if (tmpEntity.getEventType().equals(Constants.MERCHANT_OWNER_SAY)) {
						notificationsEntity.setCommentType(Constants.MERCHANT_OWNER_SAY);
					} else if (tmpEntity.getEventType().equals(Constants.MERCHANT_GROUP_BUY)) {
						notificationsEntity.setCommentType(Constants.MERCHANT_GROUP_BUY);
					} else if (tmpEntity.getEventType().equals(Constants.MERCHANT_PROMOTION)) {
						notificationsEntity.setCommentType(Constants.MERCHANT_PROMOTION);
					}
					/*********************************************************************************/
					 //0--餐厅信息审核  1-- 动态审核 2--掌柜审核 3 评价 4 掌柜收到评价
					if(tmpEntity.getMerchant()!=null){
						notificationsEntity.setMerchantId(tmpEntity.getMerchant().getMerchantId());
						notificationsEntity.setMerchantName(tmpEntity.getMerchant().getName());
					}
					notificationsEntity.setIsVerify(isVerifyStatus);
					
					map.put("msg", "拒绝申请了哦");
				}
				if(tmpEntity.getCreateUser()!=null){
					notificationsEntity.setReceiver(tmpEntity.getCreateUser());
				}else{
					notificationsEntity.setReceiver(null);
				}
				notificationsEntity.setIsRead(Constants.MESSAGE_NOT_READED);//未读
				notificationsService.saveOrUpdate(notificationsEntity);
				
			}else{
				map.put("msg", "申请失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "申请失败");
		}
		return map;
	}
	
	

}
