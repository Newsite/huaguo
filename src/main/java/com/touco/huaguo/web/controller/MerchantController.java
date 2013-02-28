package com.touco.huaguo.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.common.FTPFileOperator;
import com.touco.huaguo.common.FileUploadUtil;
import com.touco.huaguo.common.FileUtil;
import com.touco.huaguo.domain.DianPingEntity;
import com.touco.huaguo.domain.MerchantCommentEntity;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.MerchantEventEntity;
import com.touco.huaguo.domain.MerchantLikeEntity;
import com.touco.huaguo.domain.MerchantOwnerEntity;
import com.touco.huaguo.domain.MerchantVerifyEntity;
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.services.IDianPingService;
import com.touco.huaguo.services.IMerchantCommentService;
import com.touco.huaguo.services.IMerchantEventService;
import com.touco.huaguo.services.IMerchantLikeService;
import com.touco.huaguo.services.IMerchantOwnerService;
import com.touco.huaguo.services.IMerchantService;
import com.touco.huaguo.services.IMerchantVerifyService;
import com.touco.huaguo.services.INotificationsService;
import com.touco.huaguo.web.dto.MerchantLikeDTO;
import com.touco.huaguo.web.util.GeneralUtils;

@Controller
@RequestMapping("merchant")
public class MerchantController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Autowired
	private IMerchantService merchantService;

	@Autowired
	private IMerchantVerifyService merchantVerifyService;

	@Autowired
	private IMerchantEventService merchantEventService;

	@Autowired
	private IMerchantOwnerService merchantOwnerService;
	@Autowired
	private IMerchantCommentService merchantCommentService;
	@Autowired
	private INotificationsService notificationsService;

	@Autowired
	private IMerchantLikeService merchantLikeService;
	@Autowired
	private IDianPingService dianPingService;

	@InitBinder
	public void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	private FileUploadUtil fileUploadUtil = new FileUploadUtil();

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createMerchant", method = RequestMethod.GET)
	public ModelAndView createMerchant(HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		UserEntity user = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("Merchant/createMerchant");

		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMerchant", method = RequestMethod.GET)
	public ModelAndView updateMerchant(HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("Merchant/updateMerchant");
		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/merchantOwner", method = RequestMethod.GET)
	public ModelAndView merchantOwner(HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("Merchant/merchantOwner");
		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveMerchantOwner", method = RequestMethod.GET)
	public ModelAndView approveMerchantOwner(HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("Merchant/merchantOwner");
		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myMerchant", method = RequestMethod.GET)
	public ModelAndView myMerchant(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("Merchant/myMerchant");
		return modelAndView;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editMyMerchant", method = RequestMethod.GET)
	public ModelAndView editMyMerchant(HttpServletRequest request)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
		if (null == user) {
			modelAndView.setViewName("login");
			return modelAndView;
		}
		modelAndView.setViewName("Merchant/editMyMerchant");
		return modelAndView;
	}

	/**
	 * 修改成功
	 * 
	 * @return
	 * @throws WeiboException
	 */
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView resetPwdSuccess() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("updateSuccess");
		return modelAndView;
	}

	/**
	 * 餐厅列表
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getMerchantList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMerchantList(HttpServletRequest request,
			DataGridModel dgm, MerchantEntity merchantEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

			Map<String, Object> equalsMap = new HashMap<String, Object>();
			equalsMap.put("merchantStatus", Constants.MERCHANT_STATUS_PASS);
			if (StringUtils.isNotEmpty(merchantEntity.getRecommendStatus())
					&& !merchantEntity.getRecommendStatus().equals("0")) {
				equalsMap.put("recommendStatus",
						merchantEntity.getRecommendStatus());
			}
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_CITY_SESSION_INFO) != null) {
				String city = (String) request.getSession().getAttribute(
						Constants.USER_CITY_SESSION_INFO);
				equalsMap.put("cityName", city);
			} else {
				request.getSession().setAttribute(
						Constants.USER_CITY_SESSION_INFO,
						Constants.USER_DEFAULT_CITY);
				equalsMap.put("cityName", Constants.USER_DEFAULT_CITY);
			}
			queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

			Map<String, Object> likesMap = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(merchantEntity.getName())) {
				likesMap.put("name", merchantEntity.getName());
			}
			if (StringUtils.isNotBlank(merchantEntity.getMerchantStyle())) {
				likesMap.put("merchantStyle", merchantEntity.getMerchantStyle());
			}
			if (StringUtils.isNotBlank(merchantEntity.getArea())) {
				likesMap.put("area", merchantEntity.getArea());
			}
			queryParams.put(Constants.CRITERIA_LIKE, likesMap);

			if (StringUtils.isNotBlank(dgm.getSort())
					&& dgm.getSort().equals("viewNum")) {
				dgm.setSort("viewNum/datediff(now(),createDate)");
			}

			// List<MerchantEntity> list =
			// this.merchantService.getListByCriteria(dgm, queryParams);
			List<MerchantEntity> list = this.merchantService.getMerchantList(
					dgm, queryParams);
			// List<Map<String,Object>> list =
			// this.merchantService.getMerchantList(dgm, queryParams);
			if (CollectionUtils.isNotEmpty(list)) {
				// for (MerchantEntity tm : list) {
				// if (tm.getUser() == null || tm.getUser().getUserId() == 0) {
				// tm.setUser(null);
				// } else {
				// tm.getUser().setUserRefs(null);
				// UserEntity tue = new UserEntity();
				// PropertyUtils.copyProperties(tue, tm.getUser());
				// tue.setPassword(null);
				// tm.setUser(tue);
				// }
				// tm.setCreateUser(null);
				// tm.setUpdateUser(null);
				// }
				map.put("list", list);
			} else {
				map.put("list", new ArrayList<MerchantEntity>());
			}
			return map;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}

		return map;
	}

	/**
	 * 餐厅列表
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getMerchantOtherList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMerchantOtherList(HttpServletRequest request,
			DataGridModel dgm, MerchantEntity merchantEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

			Map<String, Object> equalsMap = new HashMap<String, Object>();
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_CITY_SESSION_INFO) != null) {
				String city = (String) request.getSession().getAttribute(
						Constants.USER_CITY_SESSION_INFO);
				equalsMap.put("city", city);
			} else {
				request.getSession().setAttribute(
						Constants.USER_CITY_SESSION_INFO,
						Constants.USER_DEFAULT_CITY);
				equalsMap.put("city", Constants.USER_DEFAULT_CITY);
			}
			queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

			Map<String, Object> likesMap = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(merchantEntity.getName())) {
				likesMap.put("name", merchantEntity.getName());
			}
			if (StringUtils.isNotBlank(merchantEntity.getMerchantStyle())) {
				likesMap.put("category2", merchantEntity.getMerchantStyle());
			}
			if (StringUtils.isNotBlank(merchantEntity.getArea())) {
				likesMap.put("region", merchantEntity.getArea());
			}
			queryParams.put(Constants.CRITERIA_LIKE, likesMap);

			dgm.setSort("recordId");

			List<DianPingEntity> list = this.dianPingService.getListByCriteria(
					dgm, queryParams);
			if (CollectionUtils.isNotEmpty(list)) {
				map.put("list", list);
			} else {
				map.put("list", new ArrayList<DianPingEntity>());
			}
			return map;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}

		return map;
	}

	/**
	 * 餐厅列表
	 * 
	 * @author 史中营
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMyMerchantList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyMerchantList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int page = 1;
			String pageStr = request.getParameter("page");
			if (pageStr != null && !"".equals(pageStr)) {
				page = Integer.valueOf(pageStr);
			}
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
			DataGridModel dgm = new DataGridModel();
			String sortType = request.getParameter("sortType");
			if ("0".equals(sortType) || null == sortType || "".equals(sortType)) {
				Map<String, Object> orMap = new HashMap<String, Object>();
				orMap.put("keyName", new String[] { "user.userId",
						"createUser.userId" });
				orMap.put("keyValue",
						new Object[] { user.getUserId(), user.getUserId() });
				queryParams.put(Constants.CRITERIA_OR, orMap);
			} else if ("1".equals(sortType))// 掌柜
			{
				Map<String, Object> eqMap = new HashMap<String, Object>();
				eqMap.put("user.userId", user.getUserId());
				queryParams.put(Constants.CRITERIA_EQUALS, eqMap);
			} else if ("2".equals(sortType))// 创建者
			{
				Map<String, Object> eqMap = new HashMap<String, Object>();
				eqMap.put("createUser.userId", user.getUserId());
				queryParams.put(Constants.CRITERIA_EQUALS, eqMap);
				Map<String, Object> nullMap = new HashMap<String, Object>();
				nullMap.put("user.userId", user.getUserId());
				queryParams.put(Constants.CRITERIA_ISNULL, nullMap);
			} else {
				Map<String, Object> orMap = new HashMap<String, Object>();
				orMap.put("keyName", new String[] { "user.userId",
						"createUser.userId" });
				orMap.put("keyValue",
						new Object[] { user.getUserId(), user.getUserId() });
				queryParams.put(Constants.CRITERIA_OR, orMap);
			}
			dgm.setOrder("desc");
			dgm.setSort("createDate");
			dgm.setRows(5);
			dgm.setPage(page);

			Map<String, Object> merchantData = merchantService
					.getPageListByCriteria(dgm, queryParams);
			List<MerchantEntity> list = (List<MerchantEntity>) merchantData
					.get(Constants.PAGE_ROWS);
			if (CollectionUtils.isNotEmpty(list)) {
				for (MerchantEntity tm : list) {
					if (tm.getUser() == null || tm.getUser().getUserId() == 0) {
						tm.setUser(null);
					} else {
						tm.getUser().setUserRefs(null);
						UserEntity tue = new UserEntity();
						PropertyUtils.copyProperties(tue, tm.getUser());
						tue.setPassword(null);
						tm.setUser(tue);
					}
					if (null != tm.getUser()) {
						if (tm.getUser().getUserId().longValue() == user
								.getUserId().longValue()) {
							tm.setIsMerchantOwner("掌柜");
						} else {
							tm.setIsMerchantOwner("创建者");
						}
					} else {
						tm.setIsMerchantOwner("创建者");
					}
					tm.setCreateUser(null);
					tm.setUpdateUser(null);
				}
				map.put(Constants.PAGE_ROWS, list);
				map.put("total", merchantData.get(Constants.PAGE_TOTAL));
			} else {
				map.put(Constants.PAGE_ROWS, new ArrayList<MerchantEntity>());
				map.put("total", merchantData.get(Constants.PAGE_TOTAL));
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}

		return map;
	}

	/**
	 * 我喜欢的餐厅列表
	 * 
	 * @author 史中营
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMyFavoriteMerchantList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyFavoriteMerchantList(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int page = 1;
			String pageStr = request.getParameter("page");
			if (pageStr != null && !"".equals(pageStr)) {
				page = Integer.valueOf(pageStr);
			}
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
			Map<String, Object> eqMap = new HashMap<String, Object>();
			eqMap.put("createUser.userId", user.getUserId());
			queryParams.put(Constants.CRITERIA_EQUALS, eqMap);

			DataGridModel dgm = new DataGridModel();
			dgm.setOrder("desc");
			dgm.setSort("createDate");
			dgm.setRows(5);
			dgm.setPage(page);

			Map<String, Object> merchantData = merchantLikeService
					.getPageListByCriteria(dgm, queryParams);
			List<MerchantLikeEntity> list = (List<MerchantLikeEntity>) merchantData
					.get(Constants.PAGE_ROWS);
			List<MerchantLikeDTO> dtoList = new ArrayList<MerchantLikeDTO>();
			if (CollectionUtils.isNotEmpty(list)) {
				for (MerchantLikeEntity tm : list) {
					MerchantLikeDTO dto = new MerchantLikeDTO();
					dto.setRecordId(tm.getRecordId());
					dto.setDateShow(GeneralUtils.getShowTime(tm.getCreateDate()));
					dto.setImageUrl(tm.getMerchant().getImageUrl());
					boolean isNew = hasNewEvent(tm.getMerchant()
							.getMerchantId());
					if (isNew) {
						dto.setIsNew("1");
					} else {
						dto.setIsNew("0");
					}
					dto.setMerchantId(tm.getMerchant().getMerchantId());
					dto.setSupportNum(tm.getMerchant().getSupportNum());
					dto.setMerchantName(tm.getMerchant().getName());
					dto.setMerchantStyle(tm.getMerchant().getMerchantStyle());
					dtoList.add(dto);
				}
				map.put(Constants.PAGE_ROWS, dtoList);
				map.put("total", merchantData.get(Constants.PAGE_TOTAL));
			} else {
				map.put(Constants.PAGE_ROWS,
						new ArrayList<MerchantLikeEntity>());
				map.put("total", merchantData.get(Constants.PAGE_TOTAL));
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}

		return map;
	}

	@Deprecated
	private String getShowTime(Date recordDate) {
		String result = "";
		Date now = new Date();
		long nowLong = now.getTime();
		long recordLong = recordDate.getTime();
		long temp = Math.abs(nowLong - recordLong);
		if (temp < 60000 * 60)// 1个小时以内
		{
			result = "刚刚";
		} else if (temp >= 3600000 && temp < 3600000 * 2)// 一小时内
		{
			result = "1小时前";
		} else if (temp >= 3600000 * 2 && temp < 3600000 * 3)// 一天内
		{
			result = "2小时前";
		} else if (temp >= 3600000 * 3 && temp < 3600000 * 4) {
			result = "3小时前";
		} else if (temp >= 3600000 * 4 && temp < 3600000 * 6) {
			result = "半天前";
		} else if (temp >= 3600000 * 6 && temp < 3600000 * 24) {
			result = "24小时以内";
		} else if (temp >= 3600000 * 24 && temp < 3600000 * 24 * 2) {
			result = "1天前";
		} else if (temp >= 3600000 * 24 * 2 && temp < 3600000 * 24 * 3) {
			result = "2天前";
		} else if (temp >= 3600000 * 24 * 3 && temp < 3600000 * 24 * 4) {
			result = "3天前";
		} else if (temp >= 3600000 * 24 * 4 && temp < 3600000 * 24 * 7) {
			result = "1星期以内";
		} else if (temp >= 3600000 * 24 * 7 && temp < 3600000 * 24 * 14) {
			result = "2星期以内";
		} else if (temp >= 3600000 * 24 * 14 && temp < 3600000 * 24 * 30) {
			result = "1月以内";
		} else if (temp >= 3600000 * 24 * 30 && temp < 3600000 * 24 * 30 * 2) {
			result = "2月以内";
		} else if (temp >= 3600000 * 24 * 30 * 3
				&& temp < 3600000 * 24 * 30 * 4) {
			result = "3月以内";
		} else if (temp >= 3600000 * 24 * 30 * 4 && temp < 3600000 * 24 * 365) {
			result = "1年以内";
		} else if (temp >= 3600000 * 24 * 365 && temp < 3600000 * 24 * 365 * 2) {
			result = "2年以内";
		} else if (temp >= 3600000 * 24 * 365 * 2
				&& temp < 3600000 * 24 * 365 * 3) {
			result = "3年以内";
		} else {
			result = "3年以上";
		}
		return result;
	}

	private boolean hasNewEvent(Long merchantId) {
		boolean result = false;
		try {
			DataGridModel dgm = new DataGridModel();
			dgm.setOrder("desc");
			dgm.setSort("createDate");
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
			Map<String, Object> eqMap = new HashMap<String, Object>();
			eqMap.put("merchant.merchantId", merchantId);
			queryParams.put(Constants.CRITERIA_EQUALS, eqMap);
			Map<String, Object> geMap = new HashMap<String, Object>();
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DATE, -7);
			geMap.put("createDate", now.getTime());
			queryParams.put(Constants.CRITERIA_GE, geMap);
			List<MerchantEventEntity> list = merchantEventService
					.getListByCriteria(dgm, queryParams);
			if (list != null && !list.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除喜欢餐厅
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delLike", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delLike(HttpServletRequest request)
			throws Exception {
		String recordId = request.getParameter("recordId");
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}

			if (null != recordId && !"".equals(recordId)) {
				MerchantLikeEntity merchantLike = merchantLikeService.get(Long
						.valueOf(recordId));
				Long merchantId = merchantLike.getMerchant().getMerchantId();
				MerchantEntity merchant = merchantService.get(merchantId);
				merchant.setSupportNum(merchant.getSupportNum() - 1);
				merchantService.saveOrUpdate(merchant);
				merchantLikeService.remove(merchantLike.getRecordId());
				map.put("msg", "删除成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "删除失败");
		}
		return map;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/uploadMerchantImg", method = RequestMethod.POST)
	@ResponseBody
	public String uploadMerchantImg(HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = "";
		try {
			if (request instanceof MultipartHttpServletRequest) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("uploadImg");
				filePath = fileUploadUtil.saveFile(request.getRealPath("/"),
						file.getOriginalFilename(), file.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/uploadMerchantOwnerImg", method = RequestMethod.POST)
	@ResponseBody
	public String uploadMerchantOwnerImg(HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = "";
		try {
			if (request instanceof MultipartHttpServletRequest) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("uploadPicImg");
				filePath = fileUploadUtil.saveFile(request.getRealPath("/"),
						file.getOriginalFilename(), file.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/uploadEditorImg", method = RequestMethod.POST)
	public ModelAndView uploadEditorImg(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		String filePath = "";
		try {
			if (request instanceof MultipartHttpServletRequest) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("imgFile");
				filePath = fileUploadUtil.saveFile2(request.getRealPath("/"),
						file.getOriginalFilename(), file.getInputStream());
				if (null != filePath && !"".equals(filePath)) {
					// 上传到FTP服务器，上传到网站里会造成每次更新时没有图片
					String fileSys = null;// Constants.DEFAULT_FILE_SYS;
					if (request.getSession().getAttribute(
							Constants.FILE_SYS_DOMAIN) != null) {
						fileSys = (String) request.getSession().getAttribute(
								Constants.FILE_SYS_DOMAIN);
					}
					// 上传图片
					File tempFile = new File(request.getRealPath("/")
							+ filePath);
					if (tempFile.exists()) {
						modelAndView.addObject("url", filePath);
					}
				}
				modelAndView.addObject("error", 0);
				modelAndView.setViewName("resultForEditor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	/**
	 * 新建同名花果餐厅
	 * 
	 * @author 史中营
	 * @param other
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addMerchantFromOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMerchantFromOther(String recordId,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", Constants.RESULT_ERROR);
		if (request.getSession() == null
				|| request.getSession().getAttribute(
						Constants.USER_SESSION_INFO) == null) {
			result.put("status", Constants.RESULT_NOT_LOGIN);
		} else if (StringUtils.isNotBlank(recordId)) {
			try {
				DianPingEntity other = this.dianPingService.get(Long
						.parseLong(recordId));
				if (other != null) {
					if (this.merchantService.isMerchantExist(null,
							other.getName())) {
						result.put("status", Constants.RESULT_SAME);
					} else {
						MerchantEntity merchant = new MerchantEntity();
						merchant.setName(other.getName());
						// 外网图片有水印
						// merchant.setImageUrl(other.getPicLink());
						merchant.setDescription(other.getRecommend());
						merchant.setAddress(other.getAddress());
						merchant.setTel(other.getTel());
						merchant.setContent(other.getDescription());
						merchant.setArea(other.getRegion());
						merchant.setMerchantStyle(other.getCategory2());
						// 新建方法中会添加
						// UserEntity sessionUser = (UserEntity)
						// request.getSession().getAttribute(Constants.USER_SESSION_INFO);
						// Date now = new Date();
						// merchant.setCreateUser(sessionUser);
						// merchant.setCreateDate(now);
						// merchant.setUpdateUser(sessionUser);
						// merchant.setUpdateDate(now);
						merchant.setCityName(other.getCity());
						Map<String, String> map = this.saveOrUpdate(merchant,
								request);
						if (map.get("msg").equals("保存成功")) {
							result.put("status", Constants.RESULT_SUCCESS);
							result.put("id", map.get("merchantId"));
						}
					}
				}
			} catch (Exception e) {
				ExceptionUtil.exceptionParse(e);
			}
		} else {
			result.put("status", Constants.RESULT_FAILURE);
		}
		return result;
	}

	/**
	 * 新建或者修改餐厅--餐厅还未提交审核过
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveOrUpdate(MerchantEntity merchant,
			HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}

			boolean result = false;
			String oldUrl = "";

			// 做客户名称的重名检测
			result = merchantService.isMerchantExist(merchant.getMerchantId(),
					merchant.getName());
			if (result) {
				map.put("msg", "餐厅名称重复");
				map.put("error", "2");
				return map;
			}
			if (null != merchant.getImageUrl()
					&& !"".equals(merchant.getImageUrl())) {
				if (merchant.getMerchantId() != null) {// 更新
					if (!merchant.getImageUrl().equals(oldUrl)) {// 说明图片改变过了
						// 上传图片
						File file = new File(request.getRealPath("/")
								+ merchant.getImageUrl());
						if (file.exists()) {
							merchant.setImageUrl(fileUploadUtil.fileUpload(
									request.getRealPath("/"), file.getName(), FileUtil.getByte(file), 1, false));
						}
					}
				} else {
					// 直接上传图片
					File file = new File(request.getRealPath("/") + merchant.getImageUrl());
					if (file.exists()) {
						merchant.setImageUrl(fileUploadUtil.fileUpload( request.getRealPath("/"), file.getName(),
								FileUtil.getByte(file), 1, false));
					}
				}

			}
			// 原有餐厅信息
			MerchantEntity updateMerchant = null;
			if (null != merchant.getMerchantId()) {
				updateMerchant = merchantService.get(merchant.getMerchantId());
				if (null == updateMerchant) {
					map.put("msg", "餐厅已删除");
					map.put("error", "3");
					return map;
				}
				updateMerchant.setName(merchant.getName());
				updateMerchant.setMerchantStyle(merchant.getMerchantStyle());
				updateMerchant.setArea(merchant.getArea());
				updateMerchant.setAddress(merchant.getAddress());
				updateMerchant.setImageUrl(merchant.getImageUrl());
				updateMerchant.setDescription(merchant.getDescription());
				updateMerchant.setTel(merchant.getTel());
				updateMerchant.setPriceRegion(merchant.getPriceRegion());
				// updateMerchant.setContent(merchant.getContent());
				updateMerchant.setUpdateDate(new Date());
				updateMerchant.setUpdateUser(user);
				merchantService.saveOrUpdate(updateMerchant);
				map.put("merchantId", updateMerchant.getMerchantId().toString());
			} else {
				merchant.setCreateUser(user);
				merchant.setMerchantStatus("3");
				merchant.setCreateDate(new Date());
				merchant.setUpdateDate(new Date());
				merchant.setUpdateUser(user);
				merchantService.saveOrUpdate(merchant);
				map.put("merchantId", merchant.getMerchantId().toString());
			}

			map.put("msg", "保存成功");

		} catch (Exception e) {
			e.printStackTrace();
			// FTPFileOperator fileOperator = new FTPFileOperator();
			// fileOperator.singleDelete(merchant.getImageUrl());
			// fileOperator.logout();
			map.put("msg", "保存失败");
			// throw e;
		}
		return map;
	}

	/**
	 * 修改餐厅--餐厅信息已提交过审核
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(MerchantEntity merchant,
			HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			// 做客户名称的重名检测
			boolean result = merchantService.isMerchantExist(
					merchant.getMerchantId(), merchant.getName());
			if (result) {
				map.put("msg", "餐厅名称重复");
				map.put("error", "2");
				return map;
			}

			if (null != merchant.getImageUrl()
					&& !"".equals(merchant.getImageUrl())) {
				// 直接上传图片
				File file = new File(request.getRealPath("/")
						+ merchant.getImageUrl());
				if (file.exists()) {
					merchant.setImageUrl(fileUploadUtil.fileUpload( request.getRealPath("/"), file.getName(), FileUtil.getByte(file), 1, false));
				}
			}
			MerchantEntity updateMerchant = merchantService.get(merchant
					.getMerchantId());
			if (updateMerchant.getUser() == null) {
				MerchantVerifyEntity verify = new MerchantVerifyEntity();
				verify.setName(merchant.getName());
				verify.setMerchantStyle(merchant.getMerchantStyle());
				verify.setArea(merchant.getArea());
				verify.setAddress(merchant.getAddress());
				verify.setImageUrl(merchant.getImageUrl());
				verify.setDescription(merchant.getDescription());
				verify.setTel(merchant.getTel());
				verify.setPriceRegion(merchant.getPriceRegion());
				verify.setContent(merchant.getContent());
				verify.setMerchantStatus("0");
				verify.setCreateUser(user);
				verify.setCreateDate(new Date());
				// verify.setUpdateDate(new Date());
				// updateMerchant.setUpdateUser(user);
				verify.setMerchant(updateMerchant);
				merchantVerifyService.saveOrUpdate(verify);
			} else if (updateMerchant.getUser() != null
					&& updateMerchant.getUser().getUserId().longValue() == user
							.getUserId().longValue()) {
				updateMerchant.setName(merchant.getName());
				updateMerchant.setMerchantStyle(merchant.getMerchantStyle());
				updateMerchant.setArea(merchant.getArea());
				updateMerchant.setAddress(merchant.getAddress());
				updateMerchant.setImageUrl(merchant.getImageUrl());
				updateMerchant.setDescription(merchant.getDescription());
				updateMerchant.setTel(merchant.getTel());
				updateMerchant.setPriceRegion(merchant.getPriceRegion());
				updateMerchant.setContent(merchant.getContent());
				updateMerchant.setUpdateDate(new Date());
				updateMerchant.setUpdateUser(user);
				merchantService.saveOrUpdate(updateMerchant);
			} else if (updateMerchant.getUser() != null
					&& updateMerchant.getUser().getUserId().longValue() != user
							.getUserId().longValue()) {
				map.put("msg", "您不是餐厅掌柜，无权修改该餐厅信息");
				map.put("error", "3");
				return map;
			}
			map.put("msg", "操作成功");

		} catch (Exception e) {
			e.printStackTrace();
			// FTPFileOperator fileOperator = new FTPFileOperator();
			// fileOperator.singleDelete(merchant.getImageUrl());
			// fileOperator.logout();
			map.put("msg", "操作失败");
			// throw e;
		}
		return map;
	}

	/**
	 * 更新餐厅详细信息
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateContent(HttpServletRequest request)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String merchantId = request.getParameter("merchantId");
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			MerchantEntity merchant = merchantService.get(Long
					.valueOf(merchantId));
			merchant.setContent(request.getParameter("content"));
			merchant.setUpdateUser(user);
			merchant.setUpdateDate(new Date());
			merchantService.saveOrUpdate(merchant);

			map.put("msg", "操作成功");

		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "操作失败");
			// throw e;
		}
		return map;
	}

	/**
	 * 删除餐厅
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> del(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			String merchantId = request.getParameter("merchantId");
			if (null != merchantId && !"".equals(merchantId)) {
				MerchantEntity merchant = merchantService.get(Long
						.valueOf(merchantId));
				String imageUrl = merchant.getImageUrl();
				merchantService.delMerchant(merchant);
				FTPFileOperator fileOperator = new FTPFileOperator();
				fileOperator.singleDelete(imageUrl);
				fileOperator.logout();
				map.put("msg", "删除成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "删除失败");
		}
		return map;
	}

	/**
	 * 获取餐厅信息
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMerchant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getMerchantById(HttpServletRequest request)
			throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			String merchantId = request.getParameter("merchantId");
			MerchantEntity merchant = merchantService.get(Long .valueOf(merchantId));
			map.put("name", merchant.getName());
			map.put("merchantStyle", merchant.getMerchantStyle());
			map.put("cityName", merchant.getCityName());
			map.put("area", merchant.getArea());
			map.put("address", merchant.getAddress());
			map.put("imageUrl", merchant.getImageUrl());
			map.put("description", merchant.getDescription());
			map.put("tel", merchant.getTel());
			map.put("priceId", merchant.getPriceRegion() != null ? merchant
					.getPriceRegion().getPriceId().toString() : "");
			map.put("content", merchant.getContent());
			map.put("merchantId", merchantId);
			map.put("ownerId", merchant.getUser() != null ? merchant.getUser()
					.getUserId().toString() : "");
			map.put("merchantStatus", merchant.getMerchantStatus());
			MerchantOwnerEntity owner = merchantOwnerService.getMerchantOwnerByUserIdAndMerchantId2(user.getUserId(),
							merchant.getMerchantId());
			if (null != owner) {
				map.put("linkMan", owner.getLinkMan());
				map.put("merchantTel", owner.getMerchantTel());
				map.put("picUrl", owner.getPicUrl());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 新建或者修改餐厅--餐厅还未提交审核过
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/saveMerchantOwner", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveMerchantOwner(MerchantOwnerEntity owner,
			HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String merchantId = request.getParameter("merchantId");
			String isOpen = request.getParameter("isOpen");
			UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}

			// 直接上传图片
			File file = new File(request.getRealPath("/") + owner.getPicUrl());
			if (file.exists()) {
				owner.setPicUrl(fileUploadUtil.fileUpload(request.getRealPath("/"),file.getName(), FileUtil.getByte(file), 1, false));
			}
			MerchantEntity merchant = new MerchantEntity();
			merchant.setMerchantId(Long.valueOf(merchantId));
			owner.setIsPass("0");
			owner.setUser(user);
			owner.setMerchant(merchant);
			owner.setCreateDate(new Date());
			if (null != isOpen && isOpen.equals("0")) {
				owner.setIsOpen("0");
			}
			merchantOwnerService.saveOrUpdate(owner);
			map.put("msg", "信息已提交，请耐心等待审核！");

		} catch (Exception e) {
			e.printStackTrace();
			FTPFileOperator fileOperator = new FTPFileOperator();
			fileOperator.singleDelete(owner.getPicUrl());
			fileOperator.logout();
			map.put("msg", "申请失败");
			// throw e;
		}
		return map;
	}

	/**
	 * 获取餐厅信息
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyMerchant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> verifyMerchantById(HttpServletRequest request)
			throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		try {
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			}
			String merchantId = request.getParameter("merchantId");
			MerchantEntity merchant = merchantService.get(Long
					.valueOf(merchantId));
			MerchantVerifyEntity verifyEntity = new MerchantVerifyEntity();
			verifyEntity.setName(merchant.getName());
			verifyEntity.setMerchantStyle(merchant.getMerchantStyle());
			verifyEntity.setCityName(merchant.getCityName());
			verifyEntity.setArea(merchant.getArea());
			verifyEntity.setAddress(merchant.getAddress());
			verifyEntity.setImageUrl(merchant.getImageUrl());
			verifyEntity.setDescription(merchant.getDescription());
			verifyEntity.setTel(merchant.getTel());
			verifyEntity.setPriceRegion(merchant.getPriceRegion());
			verifyEntity.setContent(merchant.getContent());
			verifyEntity.setCreateDate(new Date());
			verifyEntity.setCreateUser(user);
			verifyEntity.setMerchant(merchant);
			verifyEntity.setMerchantStatus("0");// 审核中
			merchant.setMerchantStatus("0");
			merchantService.saveOrUpdate(merchant);
			merchantVerifyService.saveOrUpdate(verifyEntity);
			MerchantOwnerEntity owner = merchantOwnerService
					.getMerchantOwnerByUserIdAndMerchantId2(user.getUserId(),
							merchant.getMerchantId());
			if (null != owner) {
				owner.setIsOpen("1");
				merchantOwnerService.update(owner);
			}
			map.put("msg", "信息已提交，请耐心等待审核！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "申请失败");
		}
		return map;
	}

	/**
	 * 
	 * 显示餐厅预览
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/merchantPreview/{merchantId}", method = RequestMethod.GET)
	public ModelAndView merchantPreview(HttpServletRequest request,
			@PathVariable("merchantId") String merchantId) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (StringUtils.isNotBlank(merchantId)) {
				DataGridModel dgm = new DataGridModel();
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("merchantId", Long.parseLong(merchantId));
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				List<MerchantEntity> list = this.merchantService
						.getListByCriteria(dgm, queryParams);
				MerchantEntity tm = null;
				if (CollectionUtils.isNotEmpty(list)) {
					tm = list.get(0);
					if (StringUtils.isNotBlank(tm.getCityName())) {
						request.getSession().setAttribute(
								Constants.USER_CITY_SESSION_INFO,
								tm.getCityName());
					} else {
						request.getSession().setAttribute(
								Constants.USER_CITY_SESSION_INFO,
								Constants.USER_DEFAULT_CITY);
						tm.setCityName(Constants.USER_DEFAULT_CITY);
						this.merchantService.saveOrUpdate(tm);
					}
					if (tm.getUser() == null || tm.getUser().getUserId() == 0) {
						tm.setUser(null);
					} else {
						tm.getUser().setUserRefs(null);
						UserEntity tue = new UserEntity();
						PropertyUtils.copyProperties(tue, tm.getUser());
						tue.setPassword(null);
						tm.setUser(tue);
					}
					tm.setCreateUser(null);
					tm.setUpdateUser(null);
					modelAndView.addObject("merchant", tm);
				} else {
					modelAndView.addObject("merchant", null);
				}

			} else {
				modelAndView.addObject("merchant", null);
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		modelAndView.setViewName("Merchant/MerchantPreview");
		return modelAndView;
	}

	/**
	 * 餐厅店长说
	 * 
	 * @author 史中营
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMerchantEventList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMerchantEventList(HttpServletRequest request,
			DataGridModel dgm, MerchantEntity merchantEntity, String searchType) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Map<String, Map<String, Object>>> queryParams = new HashMap<String, Map<String, Map<String, Object>>>();

			Map<String, Map<String, Object>> merchantParams = new HashMap<String, Map<String, Object>>();
			Map<String, Object> merchantEqualsMap = new HashMap<String, Object>();
			merchantEqualsMap.put("merchantStatus",
					Constants.MERCHANT_STATUS_PASS);
			merchantParams.put(Constants.CRITERIA_EQUALS, merchantEqualsMap);
			queryParams.put("merchant", merchantParams);

			Map<String, Map<String, Object>> eventParams = new HashMap<String, Map<String, Object>>();
			Map<String, Object> eventEqualsMap = new HashMap<String, Object>();
			eventEqualsMap.put("merchant.merchantId",
					merchantEntity.getMerchantId());
			eventEqualsMap.put("status", Constants.MERCHANT_STATUS_PASS);
			if (StringUtils.isBlank(searchType)) {
				eventEqualsMap.put("eventType", Constants.MERCHANT_OWNER_SAY);
			}
			eventParams.put(Constants.CRITERIA_EQUALS, eventEqualsMap);

			if (StringUtils.isNotBlank(searchType)
					&& searchType.equals("GroupBuyAndPromotion")) {
				Map<String, Object> eventOrMap = new HashMap<String, Object>();
				eventOrMap.put("keyName", new String[] { "eventType",
						"eventType" });
				eventOrMap.put("keyValue", new Object[] {
						Constants.MERCHANT_GROUP_BUY,
						Constants.MERCHANT_PROMOTION });
				eventParams.put(Constants.CRITERIA_OR, eventOrMap);

				Map<String, Object> eventGeMap = new HashMap<String, Object>();
				eventGeMap.put("endDate", new Date());
				eventParams.put(Constants.CRITERIA_GE, eventGeMap);
			}
			queryParams.put(Constants.RELEVANCE_NULL, eventParams);

			Map<String, Object> map = this.merchantEventService
					.getPageListByManyEntity(dgm, queryParams, null);
			List<MerchantEventEntity> list = (List<MerchantEventEntity>) map
					.get(Constants.PAGE_ROWS);
			// List<MerchantEventEntity> list =
			// this.merchantEventService.getListByCriteria(dgm, queryParams);
			if (CollectionUtils.isNotEmpty(list)) {
				for (MerchantEventEntity mee : list) {
					mee.setMerchant(null);
					mee.getCreateUser().setUserRefs(null);
					UserEntity tue = new UserEntity();
					PropertyUtils.copyProperties(tue, mee.getCreateUser());
					tue.setPassword(null);
					mee.setCreateUser(tue);
				}
				result.put("list", list);
			} else {
				result.put("list", new ArrayList<MerchantEventEntity>());
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		return result;
	}

	/**
	 * 餐厅新增店长说
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/managerWantSay", method = RequestMethod.POST)
	@ResponseBody
	public String managerWantSay(HttpServletRequest request,
			MerchantEventEntity merchantEventEntity, String merchantId) {
		if (StringUtils.isNotBlank(merchantEventEntity.getContent())
				&& StringUtils.isNotBlank(merchantId)) {
			UserEntity userSession = new UserEntity();
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_SESSION_INFO) != null) {
				userSession = (UserEntity) request.getSession().getAttribute(
						Constants.USER_SESSION_INFO);
				try {
					// merchantEventEntity.setEventType(Constants.MERCHANT_OWNER_SAY);
					merchantEventEntity.setCreateDate(new Date());
					// 页面已判断是否掌柜
					merchantEventEntity.setCreateUser(userSession);
					MerchantEntity me = this.merchantService.get(Long
							.parseLong(merchantId));
					me.setCommentNum(me.getCommentNum() + 1);
					merchantEventEntity.setMerchant(me);
					// 是店长
					if (me.getUser() != null && me.getUser().getUserId() != 0) {
						if (me.getUser().getUserId().longValue() == userSession
								.getUserId().longValue()) {
							merchantEventEntity
									.setStatus(Constants.MERCHANT_STATUS_PASS);
						} else {
							merchantEventEntity
									.setStatus(Constants.MERCHANT_STATUS_VERIFY);
						}
					} else {
						merchantEventEntity
								.setStatus(Constants.MERCHANT_STATUS_VERIFY);
					}
					if (merchantEventEntity.getEndDate() != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(merchantEventEntity.getEndDate());
						cal.set(Calendar.HOUR_OF_DAY, 23);
						cal.set(Calendar.MINUTE, 59);
						cal.set(Calendar.SECOND, 59);
						merchantEventEntity.setEndDate(cal.getTime());
					}
					this.merchantEventService.saveOrUpdate(merchantEventEntity);

					// 属性在上面已做判断，店长才会同步评论信息
					if (merchantEventEntity.getStatus().equals(
							Constants.MERCHANT_STATUS_PASS)) {
						MerchantCommentEntity mc = new MerchantCommentEntity();
						if (merchantEventEntity.getEventType().equals(
								Constants.MERCHANT_OWNER_SAY)) {
							// mc.setContent(StringUtils.replace(Constants.MERCHANT_COMMENT_OWNER_SAY,
							// "xxx", userSession.getNickName()));
							mc.setContent(Constants.MERCHANT_COMMENT_NORMAL_DYNAMIC);
							mc.setCommentType(Constants.MERCHANT_OWNER_SAY);
						} else if (merchantEventEntity.getEventType().equals(
								Constants.MERCHANT_GROUP_BUY)) {
							// mc.setContent(StringUtils.replace(Constants.MERCHANT_COMMENT_GROUP_BUY,
							// "xxx", userSession.getNickName()));
							SimpleDateFormat sdf = new SimpleDateFormat("M月d号");
							StringBuffer sb = new StringBuffer();
							sb.append(
									sdf.format(merchantEventEntity
											.getStartDate())).append(" ~ ");
							sb.append(sdf.format(merchantEventEntity
									.getEndDate()));
							sb.append("，").append(
									merchantEventEntity.getContent());
							mc.setContent(sb.toString());
							mc.setCommentType(Constants.MERCHANT_GROUP_BUY);
						} else if (merchantEventEntity.getEventType().equals(
								Constants.MERCHANT_PROMOTION)) {
							// mc.setContent(StringUtils.replace(Constants.MERCHANT_COMMENT_PROMOTION,
							// "xxx", userSession.getNickName()));
							SimpleDateFormat sdf = new SimpleDateFormat("M月d号");
							StringBuffer sb = new StringBuffer();
							sb.append(
									sdf.format(merchantEventEntity
											.getStartDate())).append(" ~ ");
							sb.append(sdf.format(merchantEventEntity
									.getEndDate()));
							sb.append("，").append(
									merchantEventEntity.getContent());
							mc.setContent(sb.toString());
							mc.setCommentType(Constants.MERCHANT_PROMOTION);
						}
						mc.setMerchant(me);
						mc.setCreateDate(new Date());
						mc.setParentId(0L);
						mc.setSender(userSession);
						this.merchantCommentService.saveOrUpdate(mc);
					}

					return Constants.RESULT_SUCCESS;
				} catch (Exception e) {
					ExceptionUtil.exceptionParse(e);
				}
			} else {
				return Constants.RESULT_NOT_LOGIN;
			}
		}
		return Constants.RESULT_FAILURE;
	}

	/**
	 * 餐厅回复评论
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/addMerchantComment", method = RequestMethod.POST)
	@ResponseBody
	public String addMerchantComment(HttpServletRequest request,
			MerchantCommentEntity merchantCommentEntity, String merchantId,
			String pid) {
		if (StringUtils.isNotBlank(merchantCommentEntity.getContent())
				&& StringUtils.isNotBlank(merchantId)) {
			UserEntity userSession = new UserEntity();
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_SESSION_INFO) != null) {
				userSession = (UserEntity) request.getSession().getAttribute(
						Constants.USER_SESSION_INFO);
				try {
					merchantCommentEntity
							.setCommentType(Constants.MERCHANT_NORMAL_SAY);
					merchantCommentEntity.setCreateDate(new Date());
					merchantCommentEntity.setSender(userSession);
					MerchantEntity me = this.merchantService.get(Long
							.parseLong(merchantId));
					me.setCommentNum(me.getCommentNum() + 1);
					merchantCommentEntity.setMerchant(me);
					// 直接评论还是回复
					if (StringUtils.isNotBlank(pid) && !pid.equals("0")) {
						MerchantCommentEntity mc = this.merchantCommentService
								.get(Long.parseLong(pid));
						UserEntity receiver = mc.getSender();

						// 回复要拷贝一份到系统通知中
						NotificationsEntity notificat = new NotificationsEntity();
						notificat
								.setContent(merchantCommentEntity.getContent());
						notificat.setCreateDate(merchantCommentEntity
								.getCreateDate());
						notificat.setIsRead(Constants.MESSAGE_NOT_READED);// 未读私信
						notificat.setType(Constants.MESSAGE_NOTIFICATIONS);// -通知
						notificat.setCategory(Constants.CATEGORY_EVALUATE);// 3
																			// 评价
						notificat.setMerchantId(me.getMerchantId());
						notificat.setMerchantName(me.getName());
						notificat.setReceiver(receiver);
						// m.setParentId(Long.parseLong(pid));
						notificat.setSender(userSession);
						this.notificationsService.saveOrUpdate(notificat);

						merchantCommentEntity.setParentId(Long.parseLong(pid));
						merchantCommentEntity.setContent("回复了 "
								+ receiver.getNickName() + " : "
								+ merchantCommentEntity.getContent());
					} else {
						// 评论有掌柜的餐厅
						if (me.getUser() != null
								&& me.getUser().getUserId() != 0) {
							// 掌柜也要同时收到通知
							NotificationsEntity messageManager = new NotificationsEntity();
							messageManager.setContent(userSession.getNickName()
									+ " 回复了你 : "
									+ merchantCommentEntity.getContent());
							messageManager.setCreateDate(merchantCommentEntity
									.getCreateDate());
							messageManager
									.setIsRead(Constants.MESSAGE_NOT_READED);
							messageManager
									.setType(Constants.MESSAGE_NOTIFICATIONS); // -通知
							messageManager.setReceiver(me.getUser());
							messageManager.setSender(userSession);
							messageManager
									.setCategory(Constants.CATEGORY_OWNER_EVALUATE);// 4-掌柜收到评价
							messageManager.setMerchantId(me.getMerchantId());
							messageManager.setMerchantName(me.getName());
							this.notificationsService
									.saveOrUpdate(messageManager);

							merchantCommentEntity.setParentId(0L);
							merchantCommentEntity.setReceiver(me.getUser());
						}
					}
					this.merchantCommentService
							.saveOrUpdate(merchantCommentEntity);

					return Constants.RESULT_SUCCESS;
				} catch (Exception e) {
					ExceptionUtil.exceptionParse(e);
				}
			} else {
				return Constants.RESULT_NOT_LOGIN;
			}
		}
		return Constants.RESULT_FAILURE;
	}

	/**
	 * 餐厅删除回复
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/deleteMerchantComment", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMerchantComment(HttpServletRequest request,
			String recordId) {
		if (StringUtils.isNotBlank(recordId)) {
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_SESSION_INFO) != null) {
				try {
					MerchantCommentEntity merchantCommentEntity = this.merchantCommentService
							.get(Long.parseLong(recordId));
					MerchantEntity me = merchantCommentEntity.getMerchant();
					if (me.getCommentNum() > 0) {
						me.setCommentNum(me.getCommentNum() - 1);
						this.merchantService.saveOrUpdate(me);
					}
					this.merchantCommentService.remove(merchantCommentEntity);

					return Constants.RESULT_SUCCESS;
				} catch (Exception e) {
					ExceptionUtil.exceptionParse(e);
				}
			} else {
				return Constants.RESULT_NOT_LOGIN;
			}
		}
		return Constants.RESULT_FAILURE;
	}

	/**
	 * 餐厅删除店长说
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/deleteMerchantEvent", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMerchantEvent(HttpServletRequest request,
			String recordId) {
		if (StringUtils.isNotBlank(recordId)) {
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_SESSION_INFO) != null) {
				try {
					this.merchantEventService.remove(Long.parseLong(recordId));

					return Constants.RESULT_SUCCESS;
				} catch (Exception e) {
					ExceptionUtil.exceptionParse(e);
				}
			} else {
				return Constants.RESULT_NOT_LOGIN;
			}
		}
		return Constants.RESULT_FAILURE;
	}

	/**
	 * 餐厅评论列表
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getMerchantCommentList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMerchantCommentList(
			HttpServletRequest request, DataGridModel dgm, String merchantId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", null);
		if (StringUtils.isNotBlank(merchantId)) {
			try {
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap
						.put("merchant.merchantId", Long.parseLong(merchantId));
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

				List<MerchantCommentEntity> list = this.merchantCommentService
						.getListByCriteria(dgm, queryParams);
				if (CollectionUtils.isNotEmpty(list)) {
					for (MerchantCommentEntity mc : list) {
						mc.setMerchant(null);
						mc.setReceiver(null);

						mc.getSender().setUserRefs(null);
						UserEntity tue = new UserEntity();
						PropertyUtils.copyProperties(tue, mc.getSender());
						tue.setPassword(null);
						mc.setSender(tue);
					}
					result.put("list", list);
				}
			} catch (Exception e) {
				ExceptionUtil.exceptionParse(e);
			}
		}
		return result;
	}

	/**
	 * 餐厅我喜欢
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/addMyFavorite", method = RequestMethod.POST)
	@ResponseBody
	public String addMyFavorite(HttpServletRequest request, String merchantId) {
		if (StringUtils.isNotBlank(merchantId)) {
			UserEntity userSession = new UserEntity();
			if (request.getSession() != null
					&& request.getSession().getAttribute(
							Constants.USER_SESSION_INFO) != null) {
				userSession = (UserEntity) request.getSession().getAttribute(
						Constants.USER_SESSION_INFO);
				try {
					MerchantEntity m = this.merchantService.get(Long
							.parseLong(merchantId));
					if (m.getUser() != null
							&& m.getUser().getUserId().longValue() == userSession
									.getUserId().longValue()) {
						return Constants.RESULT_SAME_USER;
					}

					DataGridModel dgm = new DataGridModel();
					Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

					Map<String, Object> equalsMap = new HashMap<String, Object>();
					equalsMap.put("createUser.userId", userSession.getUserId());
					equalsMap.put("merchant.merchantId",
							Long.parseLong(merchantId));
					queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

					List<MerchantLikeEntity> list = this.merchantLikeService
							.getListByCriteria(dgm, queryParams);
					if (CollectionUtils.isEmpty(list)) {
						m.setSupportNum(m.getSupportNum() + 1);
						this.merchantService.saveOrUpdate(m);
						// 增加餐厅喜欢记录表
						MerchantLikeEntity like = new MerchantLikeEntity();
						like.setMerchant(m);
						like.setCreateUser(userSession);
						like.setCreateDate(new Date());
						merchantLikeService.saveOrUpdate(like);

						// 喜欢餐厅后，评论表中添加信息
						MerchantCommentEntity merchantCommentEntity = new MerchantCommentEntity();
						merchantCommentEntity
								.setCommentType(Constants.MERCHANT_NORMAL_SAY);
						merchantCommentEntity
								.setContent(Constants.MERCHANT_COMMENT_LIKE_DYNAMIC);
						merchantCommentEntity.setCreateDate(new Date());
						merchantCommentEntity.setSender(userSession);
						m.setCommentNum(m.getCommentNum() + 1);
						merchantCommentEntity.setMerchant(m);
						if (m.getUser() != null && m.getUser().getUserId() != 0) {
							// 掌柜也要同时收到通知
							NotificationsEntity notificatManager = new NotificationsEntity();
							notificatManager.setContent(userSession
									.getNickName() + " 很喜欢你的餐厅");
							notificatManager
									.setCreateDate(merchantCommentEntity
											.getCreateDate());
							notificatManager
									.setIsRead(Constants.MESSAGE_NOT_READED);
							notificatManager
									.setType(Constants.MESSAGE_NOTIFICATIONS); // -通知
							notificatManager.setReceiver(m.getUser());
							notificatManager.setSender(userSession);
							notificatManager
									.setCategory(Constants.CATEGORY_OWNER_EVALUATE);// 4-掌柜收到评价
							notificatManager.setMerchantId(m.getMerchantId());
							notificatManager.setMerchantName(m.getName());
							this.notificationsService
									.saveOrUpdate(notificatManager);
							merchantCommentEntity.setReceiver(m.getUser());
						}
						merchantCommentEntity.setParentId(0L);
						this.merchantCommentService
								.saveOrUpdate(merchantCommentEntity);
						return Constants.RESULT_SUCCESS;
					} else {
						return "HADLIKED";
					}
				} catch (Exception e) {
					ExceptionUtil.exceptionParse(e);
				}
			} else {
				return Constants.RESULT_NOT_LOGIN;
			}
		}
		return Constants.RESULT_FAILURE;
	}

	/**
	 * 登录用户喜欢的餐厅
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getUserLikeList", method = RequestMethod.POST)
	@ResponseBody
	public List<Long> getUserLikeList(HttpServletRequest request,
			String merchantId) {
		List<Long> result = new ArrayList<Long>();
		UserEntity userSession = new UserEntity();
		if (request.getSession() != null
				&& request.getSession().getAttribute(
						Constants.USER_SESSION_INFO) != null) {
			userSession = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);

			try {
				DataGridModel dgm = new DataGridModel();
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("createUser.userId", userSession.getUserId());
				if (StringUtils.isNotBlank(merchantId)) {
					equalsMap.put("merchant.merchantId",
							Long.parseLong(merchantId));
				}
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

				List<MerchantLikeEntity> list = this.merchantLikeService
						.getListByCriteria(dgm, queryParams);

				if (CollectionUtils.isNotEmpty(list)) {
					for (MerchantLikeEntity ml : list) {
						result.add(ml.getMerchant().getMerchantId());
					}
				}
			} catch (Exception e) {
				ExceptionUtil.exceptionParse(e);
			}
		}
		return result;
	}

	/**
	 * 餐厅喜欢的用户列表
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getMerchantLikeList", method = RequestMethod.POST)
	@ResponseBody
	public List<UserEntity> getMerchantLikeList(HttpServletRequest request,
			String merchantId) {
		List<UserEntity> result = new ArrayList<UserEntity>();

		try {
			DataGridModel dgm = new DataGridModel();
			dgm.setRows(7);
			dgm.setSort("createDate");
			dgm.setOrder("desc");
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

			Map<String, Object> equalsMap = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(merchantId)) {
				equalsMap
						.put("merchant.merchantId", Long.parseLong(merchantId));
			}
			queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);

			List<MerchantLikeEntity> list = this.merchantLikeService
					.getListByCriteria(dgm, queryParams);

			if (CollectionUtils.isNotEmpty(list)) {
				for (MerchantLikeEntity ml : list) {
					ml.getCreateUser().setUserRefs(null);
					UserEntity tue = new UserEntity();
					PropertyUtils.copyProperties(tue, ml.getCreateUser());
					tue.setPassword(null);

					result.add(tue);
				}
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		return result;
	}

	/**
	 * 餐厅列表
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getSearchMerchantList", method = RequestMethod.GET)
	public String getSearchMerchantList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder result = new StringBuilder("");
		try {

			String searchValue = request.getParameter("q");
			if (searchValue == null || "".equals(searchValue.trim())) {
				return result.toString();
			}
			int page = 1;
			String pageStr = request.getParameter("page");
			if (pageStr != null && !"".equals(pageStr)) {
				page = Integer.valueOf(pageStr);
			}
			UserEntity user = (UserEntity) request.getSession().getAttribute(
					Constants.USER_SESSION_INFO);
			if (null == user) {
				;
				return result.toString();
			}
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
			DataGridModel dgm = new DataGridModel();
			Map<String, Object> likeMap = new HashMap<String, Object>();
			likeMap.put("name", searchValue);
			queryParams.put(Constants.CRITERIA_LIKE, likeMap);

			dgm.setRows(20);
			dgm.setPage(page);

			Map<String, Object> merchantData = dianPingService
					.getPageListByCriteria(dgm, queryParams);
			List<DianPingEntity> list = (List<DianPingEntity>) merchantData
					.get(Constants.PAGE_ROWS);
			if (CollectionUtils.isNotEmpty(list)) {
				for (DianPingEntity tm : list) {
					result.append(tm.getName());
					result.append("|");
					result.append(tm.getCategory2());// 菜系
					result.append("|");
					result.append(tm.getCity());// 城市
					result.append("|");
					result.append(tm.getRegion());// 区域
					result.append("|");
					result.append(tm.getBusinessArea() == null ? "" : tm
							.getBusinessArea());// 商圈
					result.append("|");
					result.append(tm.getAddress());
					result.append("|");
					result.append(tm.getTel());
					result.append("|");
					result.append(getPriceRange(tm.getAvgPrice()));
					result.append("\n");
				}

			} else {

			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		return null;
	}

	/**
	 * <option value="1">30以内</option> <option value="2"30-50</option> <option
	 * value="3">50-80</option> <option value="4">80-120</option> <option
	 * value="5">120-180</option> <option value="6">180-250</option> <option
	 * value="7">250以上</option>
	 */
	private String getPriceRange(Integer price) {
		String result = "";
		if (null != price) {
			if (price < 30) {
				result = "1";
			} else if (price >= 30 && price < 50) {
				result = "2";
			} else if (price >= 50 && price < 80) {
				result = "3";
			} else if (price >= 80 && price < 120) {
				result = "4";
			} else if (price >= 120 && price < 180) {
				result = "5";
			} else if (price >= 180 && price < 250) {
				result = "6";
			} else if (price >= 250) {
				result = "7";
			}
		}
		return result;
	}

	/**
	 * @author 史中营
	 * @param ex
	 * @param request
	 */
	@ExceptionHandler(value = Exception.class)
	public void unknowExceptionParse(Exception ex, HttpServletRequest request) {
		ExceptionUtil.exceptionParse(ex);
	}

}
