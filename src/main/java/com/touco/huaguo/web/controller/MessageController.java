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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.domain.MessageEntity;
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.services.IMessageService;
import com.touco.huaguo.web.util.GeneralUtils;

/**
 * @author shizhongying
 * 
 * 
 */
@Controller
public class MessageController {

	private IMessageService messageService;

	@Autowired
	public void setIMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 跳转页面
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public ModelAndView showMessageView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Long userId = getUserId(request);
		if (null == userId || userId == 0) {
			mav.setViewName("redirect:/login");
		} else {
			mav.setViewName("message");
		}
		return mav;
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
	 * 我的私信列表
	 * 
	 * @param request
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value = "/messages/getMyPrivateLetterList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyPrivateLetterList(
			HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", null);
		int page = 1;
		String pageStr = request.getParameter("page");
		if (pageStr != null && !"".equals(pageStr)) {
			page = Integer.valueOf(pageStr);
		}

		Long userId = getUserId(request);
		if (null == userId || userId == 0) {
			result.put("msg", "登录超时，请重新登录");
			result.put("error", "1");
		} else {
			try {
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
				Map<String, Object> equalsMap = new HashMap<String, Object>();
				//equalsMap.put("isRead", Constants.MESSAGE_NOT_READED);// 查找未读私信
				equalsMap.put("type", Constants.MESSAGE_PRIVATELETTER);// 私信
				equalsMap.put("parentId", -1L);
				
				//equalsMap.put("othersDelTag", "0");
				//equalsMap.put("ownDelTag", "0");
				
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				
				
				
				Map<String, Object> orAndMap = new HashMap<String, Object>();
				orAndMap.put("keyName", new String[] { "sender_id","othersDelTag","receiver_id","ownDelTag"});
				orAndMap.put("keyValue", new Object[] { userId, "0",userId,"0"});
				queryParams.put(Constants.CRITERIA_OR_AND, orAndMap);
				
				
				Map<String, Object> orMap = new HashMap<String, Object>();
				orMap.put("keyName", new String[] { "receiver.userId","sender.userId" });
				orMap.put("keyValue", new Object[] { userId, userId });
				queryParams.put(Constants.CRITERIA_OR, orMap);

				dgm.setOrder("desc");
				dgm.setSort("createDate");
				dgm.setRows(10);
				dgm.setPage(page);

				Map<String, Object> messageData = messageService.getPageListByCriteria(dgm, queryParams);
				List<MessageEntity> list = (List<MessageEntity>) messageData.get(Constants.PAGE_ROWS);

				List<MessageEntity> linkManList = messageService.getLinkManCount(userId, Constants.MESSAGE_PRIVATELETTER);
				Integer messageCount = messageService.getAllUnreadCountByUserId(userId);

				List<MessageEntity> privateLetterList = new ArrayList<MessageEntity>();
				if (CollectionUtils.isNotEmpty(list)) {
					for (MessageEntity tm : list) {
						MessageEntity messageEntity = new MessageEntity();
						if (tm.getSender() == null || tm.getSender().getUserId() == 0) {
							messageEntity.setSender(null);
						} else {
							tm.getSender().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, tm.getSender());
							tue.setPassword(null);
							messageEntity.setSender(tue);
						}
						if (tm.getReceiver() == null || tm.getReceiver().getUserId() == 0) {
							messageEntity.setReceiver(null);
						} else {
							tm.getReceiver().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, tm.getReceiver());
							tue.setPassword(null);
							messageEntity.setReceiver(tue);
						}
						messageEntity.setShowDate(GeneralUtils.getShowTime(tm.getCreateDate()));
						messageEntity.setContent(tm.getContent());
						messageEntity.setIsRead(tm.getIsRead());
						messageEntity.setType(tm.getType());
						messageEntity.setRecordId(tm.getRecordId());
						privateLetterList.add(messageEntity);
					}
					result.put(Constants.PAGE_ROWS, privateLetterList);
					result.put("total", messageData.get(Constants.PAGE_TOTAL));
					result.put("linkManCount", linkManList.size() == 0 ? 0 : linkManList.size());// 联系人数量
					result.put("messageCount", messageCount);
					request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT,messageCount);
				} else {
					result.put(Constants.PAGE_ROWS, new ArrayList<NotificationsEntity>());
					result.put("total", messageData.get(Constants.PAGE_TOTAL));
					result.put("messageCount", messageCount);
					request.getSession().setAttribute(Constants.PRIVATE_LETTER_COUNT,messageCount);
				}
			} catch (Exception e) {
				ExceptionUtil.exceptionParse(e);
			}
		}
		return result;
	}

	/**
	 * 单次回话的私信信息
	 * 
	 * @param request
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value = "/messages/getMessageListByCurrentUserId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMessageListByCurrentUserId(
			HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
			Long userId = getUserId(request);
			if (null == userId || userId == 0) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
			} else {
				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("type", Constants.MESSAGE_PRIVATELETTER);// 私信
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				
				
				Map<String, Object> orAndMap = new HashMap<String, Object>();
				orAndMap.put("keyName", new String[] { "sender_id","othersDelTag","receiver_id","ownDelTag"});
				orAndMap.put("keyValue", new Object[] { userId, "0",userId,"0"});
				queryParams.put(Constants.CRITERIA_OR_AND, orAndMap);
				//equalsMap.put("othersDelTag", "0");
				//equalsMap.put("ownDelTag", "0");

				Map<String, Object> orMap = new HashMap<String, Object>();
				orMap.put("keyName", new String[] { "receiver.userId","sender.userId" });
				orMap.put("keyValue", new Object[] { userId, userId });
				queryParams.put(Constants.CRITERIA_OR, orMap);
				
				Map<String, Object> orMap2 = new HashMap<String, Object>();
				orMap2.put("keyName", new String[] { "othersDelTag","ownDelTag" });
				orMap2.put("keyValue", new Object[] { "0", "0" });
				queryParams.put(Constants.CRITERIA_OR, orMap2);

				dgm.setOrder("desc");
				dgm.setSort("createDate");
				dgm.setRows(10);

				List<MessageEntity> list = messageService.getListByCriteria(dgm, queryParams);
				if (CollectionUtils.isNotEmpty(list)) {
					for (MessageEntity tm : list) {
						if (tm.getReceiver() == null
								|| tm.getReceiver().getUserId() == 0) {
							tm.setReceiver(null);
						} else {
							tm.getReceiver().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, tm.getReceiver());
							tue.setPassword(null);
							tm.setReceiver(tue);
						}
						if (tm.getSender() == null
								|| tm.getSender().getUserId() == 0) {
							tm.setSender(null);
						} else {
							tm.getSender().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, tm.getSender());
							tue.setPassword(null);
							tm.setSender(tue);
						}
						tm.setShowDate(GeneralUtils.getShowTime(tm
								.getCreateDate()));
					}
					map.put("list", list);
				} else {
					map.put("list", new ArrayList<NotificationsEntity>());
				}
			}

			return map;
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}

		return map;
	}

	/**
	 * @param userSession
	 * @param messageEntity
	 * @return
	 */
	private List<MessageEntity> checkIsExist(UserEntity userSession,
			MessageEntity messageEntity) {

		Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();
		List<MessageEntity> list = new ArrayList<MessageEntity>();
		try {
			Map<String, Object> equalsMap = new HashMap<String, Object>();
			equalsMap.put("type", Constants.MESSAGE_PRIVATELETTER);// 私信
			equalsMap.put("parentId", -1L);
			equalsMap.put("othersDelTag", "0");
			equalsMap.put("ownDelTag", "0");
			equalsMap.put("receiver.userId", messageEntity.getReceiver().getUserId());
			equalsMap.put("sender.userId", userSession.getUserId());
			queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
			DataGridModel dgm = new DataGridModel();
			list = messageService.getListByCriteria(dgm, queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 回复私信
	 * 
	 * @param request
	 * @param messageEntity
	 * @param recordId
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/messages/addPrivateLetter", method = RequestMethod.POST)
	@ResponseBody
	public String addPrivateLetter(HttpServletRequest request,
			MessageEntity messageEntity, String recordId, String pid) {

		String result = Constants.RESULT_FAILURE;
		
		String isReadStatus = request.getParameter("isReadStatus");

		if (StringUtils.isNotBlank(messageEntity.getContent())
				&& StringUtils.isNotBlank(recordId)) {
			UserEntity userSession = new UserEntity();
			if (request.getSession() != null&& request.getSession().getAttribute(
							Constants.USER_SESSION_INFO) != null) {
				userSession = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
				try {
					// 直接回复
					MessageEntity m = new MessageEntity();
					m.setType(Constants.MESSAGE_PRIVATELETTER);
					m.setCreateDate(new Date());
					m.setSender(userSession);
					if(new Long(messageEntity.getSender().getUserId()).equals(userSession.getUserId())){
						m.setReceiver(messageEntity.getReceiver());
					}else{
						m.setReceiver(messageEntity.getSender());
					}
					m.setContent(messageEntity.getContent());
					
					m.setOwnDelTag( "0");
					m.setOthersDelTag("0");
					
					if ("1".equals(isReadStatus)) {
						m.setIsRead(Constants.MESSAGE_READED);
					}else {
						m.setIsRead(Constants.MESSAGE_NOT_READED);
					}
				
					if (StringUtils.isNotBlank(pid) && !pid.equals("0")) {
						m.setParentId(Long.parseLong(pid));
					} else {
						m.setParentId(-1L);
					}

					/*List<MessageEntity> messageList = checkIsExist(userSession,m);
					if (null != messageList && !messageList.isEmpty()) {
						result = Constants.RESULT_FAILURE;
					} else {
					}*/
					messageService.saveOrUpdate(m);
					result = Constants.RESULT_SUCCESS;
				} catch (Exception e) {
					ExceptionUtil.exceptionParse(e);
					result = Constants.RESULT_ERROR;
				}
			} else {
				result = Constants.RESULT_NOT_LOGIN;
			}
		}

		return result;
	}

	/**
	 * 跳转到我的私信页面
	 * 
	 * @param request
	 * @param senderUserId
	 * @param receiverUserId
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/messages/{recordId}/{senderUserId}/{receiverUserId}", method = RequestMethod.GET)
	public ModelAndView showMyMessagesList(HttpServletRequest request,
			@PathVariable("recordId") String recordId,
			@PathVariable("senderUserId") String senderUserId,
			@PathVariable("receiverUserId") String receiverUserId) throws Exception {
		ModelAndView mav = new ModelAndView();
		Long userId = getUserId(request);
		if (null == userId || userId == 0) {
			mav.setViewName("redirect:/login");
		} else {
			mav.addObject("recordId", recordId);
			mav.addObject("senderUserId", senderUserId);
			mav.addObject("receiverUserId", receiverUserId);
			if(recordId!=null){
				MessageEntity messageEntity = messageService.get(Long.parseLong(recordId));
				List<MessageEntity> updateIsRead = new ArrayList<MessageEntity>();
				if (messageEntity != null) {
					if (new Long(-1L).equals(messageEntity.getParentId())) {
						// 如果删除的是父会话，则要删除所有子会话
						Map<String, Object> isUpdateIsReadMap = initMyMessages(request, messageEntity.getRecordId());
						List<MessageEntity> list = (List<MessageEntity>) isUpdateIsReadMap.get("messageList");
						for (MessageEntity m : list) {
							if(!Constants.MESSAGE_READED.equals(m.getIsRead())){
								m.setIsRead(Constants.MESSAGE_READED);
								m.setUpdateDate(new Date());
								updateIsRead.add(m);
							}
						}
					}else{
						if(!Constants.MESSAGE_READED.equals(messageEntity.getIsRead())){
							messageEntity.setIsRead(Constants.MESSAGE_READED);
							messageEntity.setUpdateDate(new Date());
							updateIsRead.add(messageEntity);
						}
					}
					messageService.updateII(updateIsRead);
				}
			}
			mav.setViewName("myMessages");
		}
		return mav;
	}

	/**
	 * 
	 * 加载单个回话的私信信息
	 * 
	 * @author 史中营
	 */
	@RequestMapping(value = "/initMyMessages", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initMyMessages(HttpServletRequest request,
			@RequestParam("recordId") Long recordId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long userId = getUserId(request);
			if (null == userId || userId == 0) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
			} else {
				DataGridModel dgm = new DataGridModel();
				Map<String, Map<String, Object>> queryParams = new HashMap<String, Map<String, Object>>();

				Map<String, Object> equalsMap = new HashMap<String, Object>();
				equalsMap.put("type", Constants.MESSAGE_PRIVATELETTER);// 私信
				queryParams.put(Constants.CRITERIA_EQUALS, equalsMap);
				
				Map<String, Object> orAndMap = new HashMap<String, Object>();
				orAndMap.put("keyName", new String[] { "sender_id","othersDelTag","receiver_id","ownDelTag"});
				orAndMap.put("keyValue", new Object[] { userId, "0",userId,"0"});
				queryParams.put(Constants.CRITERIA_OR_AND, orAndMap);

				Map<String, Object> orMap = new HashMap<String, Object>();
				orMap.put("keyName", new String[] { "receiver.userId","sender.userId" });
				orMap.put("keyValue", new Object[] { userId, userId });
				queryParams.put(Constants.CRITERIA_OR, orMap);

				Map<String, Object> orMap2 = new HashMap<String, Object>();
				orMap2.put("keyName", new String[] { "recordId", "parentId" });
				orMap2.put("keyValue", new Object[] { recordId, recordId });
				queryParams.put(Constants.CRITERIA_OR, orMap2);
				
				dgm.setOrder("desc");
				dgm.setSort("createDate");
				dgm.setRows(10);

				List<MessageEntity> list = messageService.getListByCriteria(dgm, queryParams);
				if (CollectionUtils.isNotEmpty(list)) {
					for (MessageEntity me : list) {
						if (me.getSender() == null || me.getSender().getUserId() == 0) {
							me.setSender(null);
						} else {
							me.getSender().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, me.getSender());
							tue.setPassword(null);
							me.setSender(tue);
						}

						if (me.getReceiver() == null || me.getReceiver().getUserId() == 0) {
							me.setReceiver(null);
						} else {
							me.getReceiver().setUserRefs(null);
							UserEntity tue = new UserEntity();
							PropertyUtils.copyProperties(tue, me.getReceiver());
							tue.setPassword(null);
							me.setReceiver(tue);
						}
						if (me.getCreateDate() != null) {
							me.setShowDate(GeneralUtils.getShowTime(me.getCreateDate()));
						} else {
							me.setShowDate(GeneralUtils.getShowTime(new Date()));
						}
					}
					map.put("messageList", list);
				}
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		}
		return map;
	}

	/**
	 * 根据ID 当前登录用户ID 删除所有关联的私信
	 * 
	 * @param request
	 * @param messageId
	 *            私信ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/messages/delPrivateLetter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delPrivateLetter(HttpServletRequest request,
			@RequestParam("messageId") Long messageId ,
			@RequestParam("isOwnOrOther") String isOwnOrOther) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (null == getUserId(request) && getUserId(request) != 0L) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			} else {
				if (null != messageId && !"".equals(messageId)) {
					Map<String, Object> isDeleteMap = initMyMessages(request,messageId);
					List<MessageEntity> list = (List<MessageEntity>) isDeleteMap.get("messageList");
					List<MessageEntity> deleteTag = new ArrayList<MessageEntity>();
					for (MessageEntity m : list) {
						if("0".equals(isOwnOrOther)){
							m.setOthersDelTag("1");
							deleteTag.add(m);
						}else if("1".equals(isOwnOrOther)){
							m.setOwnDelTag("1");
							deleteTag.add(m);
						}
					}
					messageService.updateII(deleteTag);
					map.put("success", "1");
					map.put("msg", "删除成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "删除失败");
		}
		return map;
	}

	/**
	 * 根据ID删除私信
	 * 
	 * @param request
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/messages/delMyPrivateLetter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delMyPrivateLetter(HttpServletRequest request,
			@RequestParam("messageId") Long messageId,
			@RequestParam("isOwnOrOther") String isOwnOrOther) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (null == getUserId(request) && getUserId(request) != 0L) {
				map.put("msg", "登录超时，请重新登录");
				map.put("error", "1");
				return map;
			} else {
				if (null != messageId && !"".equals(messageId)) {
					MessageEntity messageEntity = messageService.get(messageId);
					if (messageEntity != null) {
						if (new Long(-1L).equals(messageEntity.getParentId())) {
							// 如果删除的是父会话，则要删除所有子会话
							Map<String, Object> isDeleteMap = initMyMessages(request, messageEntity.getRecordId());
							List<MessageEntity> list = (List<MessageEntity>) isDeleteMap.get("messageList");
							List<MessageEntity> deleteTag = new ArrayList<MessageEntity>();
							for (MessageEntity m : list) {
								if("0".equals(isOwnOrOther)){
									m.setOwnDelTag("1");
									deleteTag.add(m);
								}else if("1".equals(isOwnOrOther)){
									m.setOthersDelTag("1");
									deleteTag.add(m);
								}
							}
							messageService.updateII(deleteTag);
						}else{
							if("0".equals(isOwnOrOther)){
								messageEntity.setOwnDelTag("1");
							}else if("1".equals(isOwnOrOther)){
								messageEntity.setOthersDelTag("1");
							}
							messageService.update(messageEntity);
						}
						map.put("msg", "删除成功");
						map.put("success", "success");
					} else {
						map.put("error", "1");
						map.put("msg", "删除失败");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "1");
			map.put("msg", "删除失败");
		}
		return map;
	}

}
