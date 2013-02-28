/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.dao.IMessageDao.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午11:25:51
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.dao;

import java.util.List;

import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.domain.MessageEntity;

/**
 * @author shizhongying
 * 
 * 
 */

public interface IMessageDao extends IGenericDao<MessageEntity, Long> {
	
	
	/**
	 * 根据当前用户Id 查找所有通知
	 * 
	 * @param userId
	 *            用户ID
	 * @param isRead
	 *            0-未读，1-已读
	 * @return
	 */
	public List<MessageEntity> getAllMessageByUserId(Long userId, String isRead,String noticeOrLetter);

	/**
	 * 根据当前用户Id 查找所有未读通知
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Integer getAllUnreadCountByUserId(Long userId);
	
	public List<MessageEntity> getLinkManCount(Long userId,String isNoticeOrPrivateLetter);
}
