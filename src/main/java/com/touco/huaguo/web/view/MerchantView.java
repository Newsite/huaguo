package com.touco.huaguo.web.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.common.StringUtil;
import com.touco.huaguo.domain.DianPingEntity;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.services.IDianPingService;
import com.touco.huaguo.services.IMerchantService;

/**
 * @author 史中营
 */
@Controller
@RequestMapping("MerchantView")
public class MerchantView {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private IMerchantService merchantService;
	@Autowired
	private IDianPingService dianPingService;

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	/**
	 * 
	 * 显示餐厅发现页面
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/MerchantFound", method = RequestMethod.GET)
	public ModelAndView MerchantFound(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Merchant/MerchantFound");
		return modelAndView;
	}

	/**
	 * 
	 * 显示餐厅主页面
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/MerchantIndex/{merchantId}", method = RequestMethod.GET)
	public ModelAndView MerchantIndex(HttpServletRequest request, @PathVariable("merchantId") String merchantId) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (StringUtils.isNotBlank(merchantId)) {
				DataGridModel dgm = new DataGridModel();
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("merchantStatus", Constants.MERCHANT_STATUS_PASS);
				equalsMap.put("merchantId", Long.parseLong(merchantId));
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				List<MerchantEntity> list = this.merchantService.getListByCriteria(dgm, queryParams);
				MerchantEntity tm = null;
				if (CollectionUtils.isNotEmpty(list)) {
					tm = list.get(0);
					if (StringUtils.isNotBlank(tm.getCityName())) {
						request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, tm.getCityName());
					} else {
						request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, Constants.USER_DEFAULT_CITY);
						tm.setCityName(Constants.USER_DEFAULT_CITY);
//						this.merchantService.saveOrUpdate(tm);
					}
					// 浏览时，增加点击数
					tm.setViewNum(tm.getViewNum() + 1);
					this.merchantService.saveOrUpdate(tm);
					
					if (tm.getUser() == null || tm.getUser().getUserId() == 0) {
						tm.setUser(null);
					} else {
						tm.getUser().setUserRefs(null);
						UserEntity tue = new UserEntity();
						PropertyUtils.copyProperties(tue, tm.getUser());
						tue.setNickName(StringUtil.getCutString(tue.getNickName(), 10));
						tue.setPassword(null);
						tm.setUser(tue);
					}
					
					if (tm.getUpdateUser() == null || tm.getUpdateUser().getUserId() == 0) {
						tm.setUpdateUser(null);
					} else {
						tm.getUpdateUser().setUserRefs(null);
						UserEntity tue = new UserEntity();
						PropertyUtils.copyProperties(tue, tm.getUpdateUser());
						tue.setPassword(null);
						tm.setUpdateUser(tue);
					}
					tm.setCreateUser(null);
//					tm.setUpdateUser(null);
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
		modelAndView.setViewName("Merchant/MerchantIndex");
		return modelAndView;
	}

	/**
	 * 
	 * 显示餐厅主页面(外网数据)
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/MerchantOther/{recordId}", method = RequestMethod.GET)
	public ModelAndView MerchantOther(HttpServletRequest request, @PathVariable("recordId") String recordId) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (StringUtils.isNotBlank(recordId)) {
				DataGridModel dgm = new DataGridModel();
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("recordId", Long.parseLong(recordId));
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				List<DianPingEntity> list = this.dianPingService.getListByCriteria(dgm, queryParams);
				DianPingEntity tm = null;
				if (CollectionUtils.isNotEmpty(list)) {
					tm = list.get(0);
					if (StringUtils.isNotBlank(tm.getCity())) {
						request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, tm.getCity());
					} else {
						request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, Constants.USER_DEFAULT_CITY);
						tm.setCity(Constants.USER_DEFAULT_CITY);
					}
					
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
		modelAndView.setViewName("Merchant/merchantOther");
		return modelAndView;
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
