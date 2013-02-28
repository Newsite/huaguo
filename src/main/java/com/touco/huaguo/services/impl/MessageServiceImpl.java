/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.services.impl.MessageServiceImp.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午11:22:08
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IMessageDao;
import com.touco.huaguo.domain.MessageEntity;
import com.touco.huaguo.services.IMessageService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * @author shizhongying
 * 通知Service
 *
 */
@Service("messageService")
public class MessageServiceImpl extends GenericServiceImpl<MessageEntity , Long> implements IMessageService {

	private IMessageDao messageDao;
	
	
	@Autowired
	public void setMessageDao(IMessageDao messageDao) {
		this.dao = messageDao;
		this.messageDao = messageDao;
	}

	/** 
	 * 
	 * (non-Javadoc)
	 * @see com.touco.huaguo.services.IMessageService#getAllMessageByUserId(java.lang.Long, java.lang.String)
	 * @param userId
	 * @param isRead
	 * @return
	 */
	@Override
	public List<MessageEntity> getAllMessageByUserId(Long userId, String isRead,String noticeOrLetter) {
		return messageDao.getAllMessageByUserId(userId, isRead ,noticeOrLetter);
	}

	/**
	 * 根据登录ID 查找未读读私信条数 
	 * (non-Javadoc)
	 * @see com.touco.huaguo.services.IMessageService#getAllUnreadCountByUserId(java.lang.Long)
	 * @param userId
	 * @return
	 */
	@Override
	public Integer getAllUnreadCountByUserId(Long userId) {
		return messageDao.getAllUnreadCountByUserId(userId);
	}

	/**
	 * 根据登录ID 查找私信联系人
	 *  (non-Javadoc)
	 * @see com.touco.huaguo.services.IMessageService#getLinkManCount(java.lang.Long, java.lang.String)
	 * @param userId
	 * @param isNoticeOrPrivateLetter
	 * @return
	 */
	@Override
	public List<MessageEntity> getLinkManCount(Long userId, String isNoticeOrPrivateLetter) {
		return messageDao.getLinkManCount(userId,isNoticeOrPrivateLetter);
	}

	

}
