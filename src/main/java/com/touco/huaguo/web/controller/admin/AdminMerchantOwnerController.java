/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.controller.admin.AdminMerchantOwnerController.java
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
import com.touco.huaguo.common.FileUtil;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.MerchantOwnerEntity;
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.services.IMerchantOwnerService;
import com.touco.huaguo.services.IMerchantService;
import com.touco.huaguo.services.INotificationsService;
import com.touco.huaguo.web.dto.MerchantOwnerDto;

/**
 * @author shizhongying 
 * 掌柜信息审核 --- 后台
 */
@Controller
@RequestMapping("/admin/merchantOwner")
public class AdminMerchantOwnerController {

	private IMerchantOwnerService merchantOwnerService;

	private IMerchantService adminMerchantService;
	
	@Autowired
	private INotificationsService notificationsService;


	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	@Autowired
	public void setIMerchantOwnerService(IMerchantOwnerService merchantOwnerService) {
		this.merchantOwnerService = merchantOwnerService;
	}

	@Autowired
	public void setIMerchantService(IMerchantService adminMerchantService) {
		this.adminMerchantService = adminMerchantService;
	}

	@RequestMapping(value = "/merchantOwnerList", method = RequestMethod.GET)
	public String showMerchantVerifyView() {
		return "admin/merchantOwnerList";
	}

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyMerchantOwnerPopWindow", method = RequestMethod.GET)
	public String verifyMerchantOwnerPopWindow(HttpServletRequest request){
		return "admin/verifyMerchantOwner";
	}

	@RequestMapping(value = "/viewMerchantOwnerPopWindow", method = RequestMethod.GET)
	public String showMerchantOwnerPopWindow(HttpServletRequest request){
		return "admin/viewMerchantOwner";
	}

	/**
	 * 查询所有掌柜的数据
	 * 
	 */
	@RequestMapping(value = "/queryAllManagerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryListVerifyMmerchantOwner(DataGridModel dgm,
			MerchantOwnerDto merchantOwnerDto, HttpServletRequest request) {
		Map<String, Map<String, Map<String, Object>>> queryParams = new HashMap<String, Map<String, Map<String, Object>>>();

		if (merchantOwnerDto.getNickName() != null) {
			Map<String, Map<String, Object>> eventMap = new HashMap<String, Map<String, Object>>();
			Map<String, Object> nickNameLikeMap = new HashMap<String, Object>();
			nickNameLikeMap.put("nickName", merchantOwnerDto.getNickName());
			eventMap.put(Constants.CRITERIA_LIKE, nickNameLikeMap);
			queryParams.put("user", eventMap);
		}

		if (merchantOwnerDto.getMerchantName() != null) {
			Map<String, Map<String, Object>> merchantMap = new HashMap<String, Map<String, Object>>();
			Map<String, Object> merchantNameLikeMap = new HashMap<String, Object>();
			merchantNameLikeMap.put("name", merchantOwnerDto.getMerchantName());
			merchantMap.put(Constants.CRITERIA_LIKE, merchantNameLikeMap);
			queryParams.put("merchant", merchantMap);
		}

		Map<String, Map<String, Object>> thisMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> equalMap = new HashMap<String, Object>();
		equalMap.put("isOpen", "1");
		thisMap.put(Constants.CRITERIA_EQUALS, equalMap);
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
			thisMap.put(Constants.CRITERIA_BETWEEN, betweenMap);			
		}

		if (merchantOwnerDto.getIsPass() != null) {
			Map<String, Object> equalsMap = new HashMap<String, Object>();
			equalsMap.put("isPass", merchantOwnerDto.getIsPass());
			thisMap.put(Constants.CRITERIA_EQUALS, equalsMap);
		}
		queryParams.put(Constants.RELEVANCE_NULL, thisMap);
		
		
		try {

			dgm.setOrder(Constants.PAGE_ORDER_DESC);
			if(dgm.getSort()==null){
				dgm.setSort("createDate");
			}

			Map<String, Object> result = merchantOwnerService.getPageListByManyEntity(dgm, queryParams, null);
			List<MerchantOwnerEntity> entitylist = (List<MerchantOwnerEntity>) result.get(Constants.PAGE_ROWS);
			List<MerchantOwnerDto> dtoList = new ArrayList<MerchantOwnerDto>();
			if (CollectionUtils.isNotEmpty(entitylist)) {
				for (MerchantOwnerEntity tmpEntity : entitylist) {
					MerchantOwnerDto tmpDto = new MerchantOwnerDto();
					tmpDto.setOwnerId(tmpEntity.getOwnId());//
					if (tmpEntity.getMerchant() != null) {
						tmpDto.setMerchantId(tmpEntity.getMerchant().getMerchantId());// 对应餐厅ID
						tmpDto.setMerchantName(tmpEntity.getMerchant().getName());// 餐厅名称
						tmpDto.setMerchantStatus(tmpEntity.getMerchant().getMerchantStatus());// 是否通过审核
					}
					if (tmpEntity.getUser() != null) {
						tmpDto.setNickName(tmpEntity.getUser().getNickName());// 昵称
						tmpDto.setEmail(tmpEntity.getUser().getEmail());// 邮箱
					}
					tmpDto.setCreateDate(tmpEntity.getCreateDate()); // 提交时间
					tmpDto.setIsPass(tmpEntity.getIsPass());// 审核状态
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
	@RequestMapping(value = "/merchantOwnerBatchDelete", method = RequestMethod.POST)
	@ResponseBody
	public String adminBatchDelete(HttpServletRequest request,
			@RequestParam("idList") List<String> idList) {
		try {
			List<Long> list = new ArrayList<Long>();
			for (String str : idList) {
				list.add(Long.parseLong(str));
			}

			merchantOwnerService.removeAll(list);
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
	@RequestMapping(value = "/getVerifyMerchantManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVerifyMerchantManager(
			HttpServletRequest request, @RequestParam("ownerId") Long ownerId
			//,@RequestParam("merchantId") Long merchantId
			) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (ownerId != null ) {
				MerchantOwnerEntity tmpEntity = merchantOwnerService.get(ownerId);
				if (tmpEntity != null) {
					if (tmpEntity.getUser() != null) {
						map.put("nickName", tmpEntity.getUser().getNickName());
						map.put("email", tmpEntity.getUser().getEmail());
						map.put("gender", tmpEntity.getUser().getGender());// 性别
						map.put("trueName", tmpEntity.getUser().getTrueName());// 真实姓名
					}
					map.put("ownerId", ownerId);// MerchantOwnerEntity id
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
	 * 掌柜申请审核
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyMerchantOwnerIsPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyMerchantOwnerIsPass(
			HttpServletRequest request, @RequestParam("ownerId") Long ownerId,
			@RequestParam("isVerify") String isVerifyStatus,
			@RequestParam("reason") String reason) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (ownerId != null) {
				MerchantOwnerEntity tmpEntity = merchantOwnerService.get(ownerId);
				if (tmpEntity != null) {
					tmpEntity.setIsPass(isVerifyStatus);// 1-已通过,2-未通过
					tmpEntity.setVerifyDate(new Date());
					merchantOwnerService.update(tmpEntity);
					
					MerchantEntity merchantEntity = tmpEntity.getMerchant();
					NotificationsEntity notificationsEntity = new NotificationsEntity();
					notificationsEntity.setCreateDate(new Date());
					notificationsEntity.setType(Constants.MESSAGE_NOTIFICATIONS);//通知
					//0--餐厅信息审核  1-- 动态审核 2--掌柜审核
					notificationsEntity.setCategory(Constants.CATEGORY_OWNER_VERIFY);  
					notificationsEntity.setIsVerify(isVerifyStatus);
					if(tmpEntity.getMerchant()!=null){
						notificationsEntity.setMerchantId(tmpEntity.getMerchant().getMerchantId());
						notificationsEntity.setMerchantName(tmpEntity.getMerchant().getName());
					}
					
					if(tmpEntity.getUser()!=null){
						notificationsEntity.setReceiver(tmpEntity.getUser());
					}else{
						notificationsEntity.setReceiver(null);
					}
					
					notificationsEntity.setIsRead(Constants.MESSAGE_NOT_READED);//未读
					
					if("1".equals(isVerifyStatus)){
						merchantEntity.setUser(tmpEntity.getUser());
						merchantEntity.setUpdateDate(new Date());
						adminMerchantService.update(merchantEntity);
						//如果掌柜与创建者不是同一个人的话 则发通知给创建者
						if(merchantEntity.getCreateUser()!=null&&merchantEntity.getUser()!=null){
							if(!merchantEntity.getCreateUser().getNickName().equals(merchantEntity.getUser().getNickName())){
								NotificationsEntity createUser = new NotificationsEntity();
								createUser.setCreateDate(new Date());
								createUser.setType(Constants.MESSAGE_NOTIFICATIONS);//通知
								createUser.setCategory(Constants.CATEGORY_MERCHANT_OWNER_VERIFY);  //0--餐厅信息审核  1-- 动态审核 2--掌柜审核
								createUser.setIsVerify(isVerifyStatus);
								createUser.setMerchantId(merchantEntity.getMerchantId());
								createUser.setMerchantName(merchantEntity.getName());
								
								String link ="${ctx}/MerchantView/MerchantIndex/"+merchantEntity.getMerchantId();
								String path = "<a href='"+link+"'>"+merchantEntity.getName()+"</a>";
								
								createUser.setContent(path);
								createUser.setIsRead(Constants.MESSAGE_NOT_READED);//未读
								
								if(tmpEntity.getUser()!=null){
									createUser.setReceiver(merchantEntity.getCreateUser());//用户A
									createUser.setSender(merchantEntity.getUser());//用户B
								}else{
									createUser.setReceiver(null);
								}
								notificationsService.saveOrUpdate(createUser);
							}
						}
						map.put("msg", "提交申请成功");
					} else if (("2").equals(isVerifyStatus)) {
						notificationsEntity.setContent(reason);
						map.put("msg", "拒绝申请了哦");
					}
					
					notificationsService.saveOrUpdate(notificationsEntity);
				} else {
					map.put("msg", "申请失败");
				}
			}else {
				map.put("msg", "申请失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "申请失败");
		}
		return map;
	}
	

	
	
	
	
	/**
	 * 掌柜资料下载
	 * 
	 * @param response
	 * @param request
	 * @param lotteryInfoId
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/download/{ownerId}", method = RequestMethod.GET)
	public void download(HttpServletResponse response,HttpServletRequest request, 
			@PathVariable("ownerId") Long ownerId) throws IOException {
		try {
			if (StringUtils.isNotBlank(ownerId.toString())) {
				MerchantOwnerEntity merchantOwnerEntity = merchantOwnerService.get(ownerId);// merchantId);
				if (merchantOwnerEntity != null) {
					String filePath = merchantOwnerEntity.getPicUrl();
					
					String dowloadPath  = request.getRealPath("/") + Constants.TEMP_DIR;
					
//					URL url = new URL("file:///"+request.getRealPath("/")+filePath);
//					File file = new File(dowloadPath);
//					if (!file.exists()) {
//						file.mkdirs();
//					}
					
//					OutputStream os = new FileOutputStream(file);
//					InputStream is = url.openStream();
//					byte[] buff = new byte[1024];
//					while(true) {
//						int readed = is.read(buff);
//						if(readed == -1) {
//							break;
//						}
//						byte[] temp = new byte[readed];
//						System.arraycopy(buff, 0, temp, 0, readed);
//						os.write(temp);
//					}
					
					String dfileName = filePath.substring(filePath.lastIndexOf("/") + 1);
					
					byte[] data = FileUtil.getByte(new File(filePath + dfileName));
					
					dfileName = new String(dfileName.getBytes("GBK"),"ISO-8859-1");
					response.addHeader("Content-Disposition","attachment; filename=" + dfileName);
					response.setContentType("application/octet-stream");
					response.getOutputStream().write(data);
					response.getOutputStream().flush();
					response.getOutputStream().close();
				} else {
					// 查不到时返回空图
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
	// ============================= 审核 END =======

}
