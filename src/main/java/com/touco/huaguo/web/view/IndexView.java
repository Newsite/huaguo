package com.touco.huaguo.web.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.ExceptionUtil;

/**
 * @author 史中营
 */
@Controller
public class IndexView {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	/**
	 * 
	 * 显示index页面
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView adminAccountList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/**
	 * 
	 * 显示index页面
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "faq", method = RequestMethod.GET)
	public ModelAndView faq(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("faq");
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
	
	
	/**
	 *  花果管理员后台页面
	 * @return
	 */
	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public ModelAndView adminView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/adminLogin");
		return mav;
	}
	
}
