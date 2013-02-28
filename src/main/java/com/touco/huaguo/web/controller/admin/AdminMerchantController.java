/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.controller.admin.AdminMerchantVerController.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-23下午5:04:29
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.DateUtil;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.common.FTPFileOperator;
import com.touco.huaguo.common.FileUtil;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.MerchantOwnerEntity;
import com.touco.huaguo.domain.MerchantVerifyEntity;
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.services.IMerchantOwnerService;
import com.touco.huaguo.services.IMerchantService;
import com.touco.huaguo.services.IMerchantVerifyService;
import com.touco.huaguo.services.IMessageService;
import com.touco.huaguo.services.INotificationsService;
import com.touco.huaguo.web.dto.MerchantDto;

/**
 * @author shizhongying 
 * 餐厅信息审核后台
 */
@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController {
	
	private IMerchantService adminMerchantService;
	
	private IMerchantOwnerService merchantOwnerService;
	
	private IMerchantVerifyService adminMerchantVerifyService;
	
	@Autowired
	private INotificationsService notificationsService;
	

	
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	
	@Autowired
	public void setIMerchantService(IMerchantService adminMerchantService) {
		this.adminMerchantService = adminMerchantService;
	}

	@Autowired
	public void setIMerchantVerifyService(IMerchantVerifyService adminMerchantVerifyService) {
		this.adminMerchantVerifyService = adminMerchantVerifyService;
	}

	@Autowired
	public void setIMerchantOwnerService(IMerchantOwnerService merchantOwnerService) {
		this.merchantOwnerService = merchantOwnerService;
	}


	@RequestMapping(value = "/merchantList", method = RequestMethod.GET)
	public String showMerchantVerifyView() {
		return "admin/merchantList";
	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyMerchantPopWindow", method = RequestMethod.GET)
	public String verifyMerchantEventPopWindow(HttpServletRequest request) {
		return "admin/verifyMerchant";
	}
	
	
	//=============== 餐厅信息审核  ====================
	/**
	 * 查询所有餐厅信息的数据
	 * 审核---
	 * 
	 */
	@RequestMapping(value = "/verifyMmerchantQueryList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryListVerifyMmerchant(DataGridModel dgm, 
			MerchantDto merchantDto,
			HttpServletRequest request) {
		Map<String, Map<String, Map<String, Object>>> queryParams = new HashMap<String, Map<String, Map<String, Object>>>();
		
		if (merchantDto.getNickName() != null) {
			Map<String, Map<String, Object>> eventMap = new HashMap<String, Map<String, Object>>();
			Map<String, Object> creditLikeMap = new HashMap<String, Object>();
			creditLikeMap.put("nickName",  merchantDto.getNickName());
			eventMap.put(Constants.CRITERIA_LIKE, creditLikeMap);
			queryParams.put("createUser", eventMap);
		}
		
		if (merchantDto.getMerchantName() != null) {
			Map<String, Map<String, Object>> merchantMap = new HashMap<String, Map<String, Object>>();
			Map<String, Object> userLikeMap = new HashMap<String, Object>();
			userLikeMap.put("name", merchantDto.getMerchantName());
			merchantMap.put(Constants.CRITERIA_LIKE, userLikeMap);
			queryParams.put("merchant", merchantMap);
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
		
		if(merchantDto.getVerifyStatus()!=null){
			Map<String, Object> equalsMap = new HashMap<String, Object>();
			equalsMap.put("merchantStatus", merchantDto.getVerifyStatus());
			thisMap.put(Constants.CRITERIA_EQUALS, equalsMap);
		}else{
			Map<String, Object> inMap = new HashMap<String, Object>();
			inMap.put("merchantStatus", new String[] {"0",  "1", "2" });
			thisMap.put(Constants.CRITERIA_IN, inMap);
		}
		
		queryParams.put(Constants.RELEVANCE_NULL, thisMap);

		try {
			
			dgm.setOrder(Constants.PAGE_ORDER_DESC);
			if(dgm.getSort()==null){
				dgm.setSort("createDate");
			}

			Map<String, Object> result = adminMerchantVerifyService.getPageListByManyEntity(dgm, queryParams, null);
			List<MerchantVerifyEntity> entitylist = (List<MerchantVerifyEntity>) result.get(Constants.PAGE_ROWS);
			List<MerchantDto> dtoList = new ArrayList<MerchantDto>();
			if (CollectionUtils.isNotEmpty(entitylist)) {
				for (MerchantVerifyEntity tmpEntity : entitylist) {
					MerchantDto tmpDto = new MerchantDto();
					tmpDto.setVerifyMerchantId(tmpEntity.getRecordId());//
					if(tmpEntity.getMerchant()!=null){
						tmpDto.setMerchantId(tmpEntity.getMerchant().getMerchantId());//对应餐厅ID
						tmpDto.setMerchantName(tmpEntity.getMerchant().getName());//餐厅名称
					}
					if(tmpEntity.getCreateUser()!=null){
						tmpDto.setNickName(tmpEntity.getCreateUser().getNickName());//昵称
						tmpDto.setEmail(tmpEntity.getCreateUser().getEmail());//邮箱
					}
					tmpDto.setCreateDate(tmpEntity.getCreateDate()); //提交时间
					tmpDto.setVerifyStatus(tmpEntity.getMerchantStatus());//审核状态
					tmpDto.setVerifyDate(tmpEntity.getUpdateDate());//审核时间
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
	@RequestMapping(value = "/merchantBatchDelete", method = RequestMethod.POST)
	@ResponseBody
	public String adminBatchDelete(HttpServletRequest request,
			@RequestParam("idList") List<String> idList) {
		try {
			List<Long> list = new ArrayList<Long>();
			for (String str : idList) {
				list.add(Long.parseLong(str));
			}

			adminMerchantVerifyService.removeAll(list);
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
	@RequestMapping(value = "/getVerifyMerchant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVerifyMerchant(HttpServletRequest request
			,@RequestParam("verifyMerchantId") Long verifyMerchantId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(verifyMerchantId!=null){
				MerchantVerifyEntity tmpEntity =adminMerchantVerifyService.get(verifyMerchantId);
				if(tmpEntity!=null){
					if(tmpEntity.getCreateUser()!=null){
						map.put("nickName", tmpEntity.getCreateUser().getNickName());
						map.put("email",tmpEntity.getCreateUser().getEmail());
					}
					map.put("verifyMerchantId",verifyMerchantId);//MerchantVerifyEntity id 
					if(tmpEntity.getMerchant()!=null){
						map.put("merchantId", tmpEntity.getMerchant().getMerchantId());
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
					
					map.put("merchantStatus",tmpEntity.getMerchantStatus());// 状态
				}
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
	@RequestMapping(value = "/verifyMerchantIsPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyMerchantEventIsPass(HttpServletRequest request
			,@RequestParam("verifyMerchantId") Long verifyMerchantId 
			,@RequestParam("merchantId") Long merchantId 
			,@RequestParam("isVerify")  String isVerifyStatus
			,@RequestParam("reason")  String reason
			) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(verifyMerchantId!=null){
				MerchantVerifyEntity tmpEntity =adminMerchantVerifyService.get(verifyMerchantId);
				if(tmpEntity!=null){
					tmpEntity.setMerchantStatus(isVerifyStatus);//1-已通过,2-未通过
					
					tmpEntity.setUpdateDate(new Date());
					adminMerchantVerifyService.update(tmpEntity);
					
					MerchantEntity merchantEntity = adminMerchantService.get(merchantId);
					if(merchantEntity!=null){
						
						merchantEntity.setUpdateDate(new Date());						
						if("1".equals(isVerifyStatus)){//设置成已上线
							merchantEntity.setMerchantStatus("1");
							merchantEntity.setName(tmpEntity.getName());
							merchantEntity.setMerchantStyle(tmpEntity.getMerchantStyle());
							merchantEntity.setArea(tmpEntity.getArea());
							merchantEntity.setAddress(tmpEntity.getAddress());
							merchantEntity.setImageUrl(tmpEntity.getImageUrl());
							merchantEntity.setDescription(tmpEntity.getDescription());
							merchantEntity.setTel(tmpEntity.getTel());
							merchantEntity.setPriceRegion(tmpEntity.getPriceRegion());
							merchantEntity.setContent(tmpEntity.getContent());
							merchantEntity.setUpdateDate(new Date());
							merchantEntity.setIsOpen("1");
							merchantEntity.setUpdateUser(tmpEntity.getCreateUser());
							adminMerchantService.update(merchantEntity);
						}else
						{
							merchantEntity.setMerchantStatus("2");
						}
						
						//如果掌柜与创建者不是同一个人的话 则发通知给创建者
						if(merchantEntity.getCreateUser()!=null&&merchantEntity.getUser()!=null){
							if(!merchantEntity.getCreateUser().getNickName().equals(merchantEntity.getUser().getNickName())){
								NotificationsEntity createUser = new NotificationsEntity();
								createUser.setCreateDate(new Date());
								createUser.setType(Constants.MESSAGE_NOTIFICATIONS);//通知
								createUser.setCategory(Constants.CATEGORY_MERCHANT_VERIFY);  //0--餐厅信息审核  1-- 动态审核 2--掌柜审核
								createUser.setIsVerify(isVerifyStatus);
								createUser.setMerchantId(merchantEntity.getMerchantId());
								createUser.setMerchantName(tmpEntity.getName());
								String link ="${ctx}/MerchantView/MerchantIndex/"+merchantEntity.getMerchantId();
								String path = "<a href='"+link+"'>"+merchantEntity.getName()+"</a>";
								if(("1").equals(isVerifyStatus)){
									createUser.setContent("你好，你分享的"+path+ "餐厅信息审核通过了，已经正式上线了。");
									map.put("msg", "提交申请成功");
								}else if(("2").equals(isVerifyStatus)){
									createUser.setContent(reason);
									map.put("msg", "拒绝申请了哦");
								}
								
								createUser.setIsRead(Constants.MESSAGE_NOT_READED);//未读
								
								if(tmpEntity.getCreateUser()!=null){
									createUser.setReceiver(merchantEntity.getCreateUser());
								}else{
									createUser.setReceiver(null);
								}
								notificationsService.saveOrUpdate(createUser);
							}
						}
						
						//通知
						NotificationsEntity notificationsEntity = new NotificationsEntity();
						notificationsEntity.setCreateDate(new Date());
						notificationsEntity.setType(Constants.MESSAGE_NOTIFICATIONS);//通知
						notificationsEntity.setCategory(Constants.CATEGORY_MERCHANT_VERIFY);  //0--餐厅信息审核  1-- 动态审核 2--掌柜审核
						notificationsEntity.setIsVerify(isVerifyStatus);
						notificationsEntity.setMerchantId(merchantEntity.getMerchantId());
						notificationsEntity.setMerchantName(tmpEntity.getName());
						String link ="${ctx}/MerchantView/MerchantIndex/"+merchantEntity.getMerchantId();
						String path = "<a href='"+link+"'>"+merchantEntity.getName()+"</a>";
						if(("1").equals(isVerifyStatus)){
							notificationsEntity.setContent("你好，你分享的"+path+ "餐厅信息审核通过了，已经正式上线了。");
							map.put("msg", "提交申请成功");
						}else if(("2").equals(isVerifyStatus)){
							notificationsEntity.setContent(reason);
							map.put("msg", "拒绝申请了哦");
						}
						
						notificationsEntity.setIsRead(Constants.MESSAGE_NOT_READED);//未读
						
						if(tmpEntity.getCreateUser()!=null){
							notificationsEntity.setReceiver(tmpEntity.getCreateUser());
						}else{
							notificationsEntity.setReceiver(null);
						}
						notificationsService.saveOrUpdate(notificationsEntity);
					}
				}else{
					map.put("msg", "申请失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "申请失败");
		}
		return map;
	}
	// =============================   审核 END  =======
	
	
	// =============================  餐厅信息  推荐 Start   ===========
	@RequestMapping(value = "/merchantInfoList", method = RequestMethod.GET)
	public String showMerchantView() {
		return "admin/merchantInfoList";
	}
	
	/**
	 * 查询所有餐厅信息的数据
	 * 
	 */
	@RequestMapping(value = "/merchantInfoQueryList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> merchantInfoQueryList(DataGridModel dgm, 
			MerchantDto merchantDto,
			HttpServletRequest request) {
		Map<String, Map<String, Map<String, Object>>> queryParams = new HashMap<String, Map<String, Map<String, Object>>>();
		
		Map<String, Object> equalsMap = new HashMap<String, Object>();
		Map<String, Map<String, Object>> thisMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> likeMap = new HashMap<String, Object>();
		
		if(merchantDto.getCityName()!=null&&!("请选择").equals(merchantDto.getCityName())){//所在地区
			equalsMap.put("cityName", merchantDto.getCityName());
			thisMap.put(Constants.CRITERIA_EQUALS, equalsMap);
			queryParams.put(Constants.RELEVANCE_NULL, thisMap);
		}
		if(merchantDto.getArea()!=null&&!("请选择").equals(merchantDto.getArea())){//区域
			likeMap.put("area", merchantDto.getArea());
			thisMap.put(Constants.CRITERIA_LIKE, likeMap);
			queryParams.put(Constants.RELEVANCE_NULL, thisMap);
		}
		
		if (merchantDto.getMerchantName() != null) {//餐厅名称
			likeMap.put("name", merchantDto.getMerchantName());
			thisMap.put(Constants.CRITERIA_LIKE, likeMap);
			queryParams.put(Constants.RELEVANCE_NULL, thisMap);
		}
		
		if (merchantDto.getManager() != null) {//掌柜
			Map<String, Map<String, Object>> eventMap = new HashMap<String, Map<String, Object>>();
			Map<String, Object> nickNameLikeMap = new HashMap<String, Object>();
			nickNameLikeMap.put("nickName",  merchantDto.getManager());
			eventMap.put(Constants.CRITERIA_LIKE, nickNameLikeMap);
			queryParams.put("user", eventMap);
		}
		//上线时间
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
			betweenMap.put("updateDate", queryDate);
			thisMap.put(Constants.CRITERIA_BETWEEN, betweenMap);
			queryParams.put(Constants.RELEVANCE_NULL, thisMap);
		}
		//审核通过的记录
		equalsMap.put("merchantStatus", "1");
		thisMap.put(Constants.CRITERIA_EQUALS, equalsMap);
		queryParams.put(Constants.RELEVANCE_NULL, thisMap);

		try {
			
			dgm.setOrder(Constants.PAGE_ORDER_DESC);
			if(dgm.getSort()==null){
				dgm.setSort("updateDate");
			}

			Map<String, Object> result = adminMerchantService.getPageListByManyEntity(dgm, queryParams, null);
			List<MerchantEntity> entitylist = (List<MerchantEntity>) result.get(Constants.PAGE_ROWS);
			List<MerchantDto> dtoList = new ArrayList<MerchantDto>();
			if (CollectionUtils.isNotEmpty(entitylist)) {
				for (MerchantEntity tmpEntity : entitylist) {
					MerchantDto tmpDto = new MerchantDto();
					tmpDto.setMerchantId(tmpEntity.getMerchantId());//
					tmpDto.setMerchantName(tmpEntity.getName());//餐厅名称
					tmpDto.setMerchantStyle(tmpEntity.getMerchantStyle());//菜系
					tmpDto.setCityName(tmpEntity.getCityName());
					tmpDto.setArea(tmpEntity.getArea());
					tmpDto.setSupportNum(tmpEntity.getSupportNum());//人气
					tmpDto.setViewNum(tmpEntity.getViewNum());//喜欢
					if(tmpEntity.getCreateUser()!=null){
						tmpDto.setCreateUser(tmpEntity.getCreateUser().getNickName());//创建者
						tmpDto.setEmail(tmpEntity.getCreateUser().getEmail());//邮箱
					}else{
						tmpDto.setCreateUser(null);
					}
					if(tmpEntity.getUser()!=null){
						tmpDto.setManagerId(tmpEntity.getUser().getUserId());//掌柜 ID
						tmpDto.setManager(tmpEntity.getUser().getNickName());//掌柜 
					}else{
						tmpDto.setManager(null);
					}
					
					tmpDto.setOnLineDate(tmpEntity.getUpdateDate()); //上线时间
					tmpDto.setRecommendStatus(tmpEntity.getRecommendStatus());//推荐状态
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
	
	
	
	/**
	 * 推荐 /取消推荐
	 * @param request
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setRecommendRow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setRecommendRow(HttpServletRequest request
			,@RequestParam("idList") List<String> idList 
			,@RequestParam("isRecommend")  String isRecommend
			) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<MerchantEntity> merchantList = new ArrayList<MerchantEntity>();
			for (String str : idList) {
				if(StringUtils.isNotBlank(str)){
					MerchantEntity merchantEntity =adminMerchantService.get(Long.parseLong(str));
					if(Constants.RECOMMEND_STATUS.equals(merchantEntity.getRecommendStatus())){
						merchantEntity.setRecommendStatus(Constants.UN_RECOMMEND_STATUS);
					}else if(Constants.UN_RECOMMEND_STATUS.equals(merchantEntity.getRecommendStatus())){
						merchantEntity.setRecommendStatus(Constants.RECOMMEND_STATUS);
					}
					merchantList.add(merchantEntity);
				}
			}
			
			String msg="";
			if(("1").equals(isRecommend)){
				msg="推荐";
			}else if(("0").equals(isRecommend)){
				msg="取消推荐";
			}
			
			if (null != merchantList && !merchantList.isEmpty()) {
				adminMerchantService.updateII(merchantList);
				map.put("msg",msg+"成功");
			} else{
				map.put("msg",msg+"失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param merchantIdList
	 * @return
	 */
	@RequestMapping(value = "/batchDeleteMerchant", method = RequestMethod.POST)
	@ResponseBody
	public String BatchDelete(HttpServletRequest request,
			@RequestParam("merchantIdList") List<String> merchantIdList) {
		try {
			List<Long> list = new ArrayList<Long>();
			for (String idStr : merchantIdList) {
				list.add(Long.parseLong(idStr));
			}
			adminMerchantService.delMerchantList(list);
			return "true";
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			return "false";
		}
	}
	
	
	
	/**
	 * 餐厅信息部分 查看掌柜
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVerifyMerchantManager2Merchant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVerifyMerchantManager2Merchant(
			HttpServletRequest request, @RequestParam("userId") Long userId,
			@RequestParam("merchantId") Long merchantId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (userId != null && merchantId != null) {
				MerchantOwnerEntity tmpEntity = merchantOwnerService.getMerchantOwnerByUserIdAndMerchantId(userId, merchantId);
				if (tmpEntity != null) {
					if (tmpEntity.getUser() != null) {
						map.put("nickName", tmpEntity.getUser().getNickName());
						map.put("email", tmpEntity.getUser().getEmail());
						map.put("gender", tmpEntity.getUser().getGender());// 性别
						map.put("trueName", tmpEntity.getUser().getTrueName());// 真实姓名
					}
					map.put("userId", userId);//user userId
					if (tmpEntity.getMerchant() != null) {
						map.put("merchantId", tmpEntity.getMerchant().getMerchantId());
						map.put("merchantName", tmpEntity.getMerchant().getName());
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
					map.put("linkMan", tmpEntity.getLinkMan());// 联系人
					map.put("telephone", tmpEntity.getMerchantTel());
					map.put("picUrl", tmpEntity.getPicUrl());
					map.put("isPass", tmpEntity.getIsPass());// 状态
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "");
		}
		return map;
	}
	
	
	
	/**
	 * 掌柜下载
	 * 
	 * @param response
	 * @param request
	 * @param lotteryInfoId
	 * @throws IOException 
	 */
	@RequestMapping(value = "/download2MerchantInfo/{userId}/{merchantId}", method = RequestMethod.GET)
	public void download2MerchantInfo(HttpServletResponse response,
			HttpServletRequest request, @PathVariable("userId") Long userId,
			@PathVariable("merchantId") Long merchantId) throws IOException {
		try {
			if (StringUtils.isNotBlank(userId.toString())&& StringUtils.isNotBlank(merchantId.toString())) {
				MerchantOwnerEntity merchantOwnerEntity = merchantOwnerService.getMerchantOwnerByUserIdAndMerchantId(userId,merchantId);
				if (merchantOwnerEntity != null) {
					String filePath = merchantOwnerEntity.getPicUrl();
					
					String dowloadPath  = request.getRealPath("/") + Constants.TEMP_DIR;
					
					File file = new File(dowloadPath);
					if (!file.exists()) {
						file.mkdirs();
					}
					
					FTPFileOperator fileOperator = new FTPFileOperator();
					Boolean downFlag = fileOperator.singleDownload(filePath, dowloadPath);
					//fileOperator.singleDownload(filePath,dowloadPath);
					if(downFlag.booleanValue()){
						fileOperator.logout();
						String dfileName = filePath.substring(filePath.lastIndexOf("/") + 1);
						byte[] data = FileUtil.getByte(new File(dowloadPath + dfileName));
						
						dfileName = new String(dfileName.getBytes("GBK"),"ISO-8859-1");
						response.addHeader("Content-Disposition","attachment; filename=" + dfileName);
						response.setContentType("application/octet-stream");
						response.getOutputStream().write(data);
						response.getOutputStream().flush();
						response.getOutputStream().close();
					}else{
						response.getOutputStream().close();
						response.getWriter().print("<script>history.go(-1);</script>");
					}
				} else {
					response.addHeader("Content-Disposition","attachment; filename=0");
					response.setContentType("application/octet-stream");
					response.getOutputStream().flush();
					response.getOutputStream().close();
					response.getWriter().print("<script>history.go(-1);</script>");
				}
			} else {
				response.addHeader("Content-Disposition","attachment; filename=0");
				response.setContentType("application/octet-stream");
				response.getOutputStream().flush();
				response.getOutputStream().close();
				response.getWriter().print("<script>history.go(-1);</script>");
			}
		} catch (Exception e) {
			response.getOutputStream().close();
			response.getWriter().print("<script>history.go(-1);</script>");
			ExceptionUtil.exceptionParse(e);
		}
	}
	
	
	@RequestMapping(value = "/newMerchant", method = RequestMethod.GET)
	public String creditInfo(HttpServletRequest request) throws Exception {
		return "admin/newMerchant";
	}
	
}
