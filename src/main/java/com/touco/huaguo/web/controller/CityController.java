package com.touco.huaguo.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.domain.CityEntity;
import com.touco.huaguo.services.ICityService;

@Controller
@RequestMapping("city")
public class CityController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private ICityService cityService;

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	/**
	 * 城市区域列表
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/getCityAreaList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCityAreaList(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cityName = Constants.USER_DEFAULT_CITY;
			if (request.getSession() != null && request.getSession().getAttribute(Constants.USER_CITY_SESSION_INFO) != null) {
				cityName = (String) request.getSession().getAttribute(Constants.USER_CITY_SESSION_INFO);
			}else{
				request.getSession().setAttribute(Constants.USER_CITY_SESSION_INFO, Constants.USER_DEFAULT_CITY);
			}
			
			dgm.setSort("sortNo");
			dgm.setOrder("asc");
			
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

			Map<String, Object> likesMap = new HashMap<String, Object>();
			likesMap.put("areaName", cityName);
			queryParams.put(Constants.CRITERIA_LIKE, likesMap);

			List<CityEntity> list = this.cityService.getListByCriteria(dgm, queryParams);
			if (CollectionUtils.isNotEmpty(list)) {
				CityEntity sessionCity = list.get(0);
				
				queryParams.clear();
				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("parentId", sessionCity.getCityId());
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				
				list = this.cityService.getListByCriteria(dgm, queryParams);
				if (CollectionUtils.isNotEmpty(list)) {
					map.put("list", list);
					return map;
				} else {
					map.put("list", new ArrayList<CityEntity>());
				}
			} else {
				map.put("list", new ArrayList<CityEntity>());
			}
			return map;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
			map.put("list", new ArrayList<CityEntity>());
		}

		return map;
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
	
	
	/**
	 * 查找所有区域
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllList")
	@ResponseBody         
	public List<CityEntity> getAllList() throws Exception{
	    return cityService.findAll("0001");
	}	
	
	/**
	 * 查找所有区域
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllListById")
	@ResponseBody         
	public List<CityEntity> getAllListByParentId(HttpServletRequest request) throws Exception{
		String id =request.getParameter("id");
	    return cityService.findAll(id);
	}

}
