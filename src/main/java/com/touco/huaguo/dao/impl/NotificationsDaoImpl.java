/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.dao.impl.MessageDaoImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午11:28:44
 * <p>
 * 部门: 产品部
 * <p>
 */
/**
 * 
 */
package com.touco.huaguo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.dao.INotificationsDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.NotificationsEntity;

/**
 * @author shizhongying
 * 通知Dao实现
 *
 */
@Repository("notificationsDao")
public class NotificationsDaoImpl extends GenericDaoImpl<NotificationsEntity, Long>
		implements INotificationsDao {

	
	public NotificationsDaoImpl() {
		super(NotificationsEntity.class);
	}

	/** (non-Javadoc)
	 * @see com.touco.huaguo.dao.IMessageDao#getAllMessageByUserId(java.lang.Long, java.lang.String)
	 * @param userId
	 * @param isRead
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NotificationsEntity> getAllMessageByUserId(Long userId, String isRead,String noticeOrLetter) {
		String hql = " from MessageEntity as m where m.receiver.userId = ?  and m.isRead = ? and m.type= ?";
		List<NotificationsEntity> list = getHibernateTemplate().find(hql,userId,isRead,noticeOrLetter);
		if(list != null && list.size() >0){
			return list;
		}
		
		return null;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.touco.huaguo.dao.IMessageDao#getAllUnreadCountByUserId(java.lang.Long)
	 * @param userId
	 * @return
	 */
	public Integer getAllUnreadCountByUserId(Long userId) {
		String hql = "from MessageEntity as m where m.receiver.userId = ? and m.isRead = ? and m.type= ?";
		List<NotificationsEntity> list = getHibernateTemplate().find(hql,userId,Constants.MESSAGE_NOT_READED,Constants.MESSAGE_PRIVATELETTER);
		return list.size() == 0 ? 0 : list.size();
	}

	/**
	 * 根据登录用户查找 有多少联系人
	 *  (non-Javadoc)
	 * @see com.touco.huaguo.dao.IMessageDao#getLinkManCount(com.touco.huaguo.common.DataGridModel, java.lang.String)
	 * @param dgm
	 * @param isNoticeOrPrivateLetter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NotificationsEntity> getLinkManCount(Long userId,String isNoticeOrPrivateLetter) {
		String hql= "SELECT DISTINCT m.receiver.userId from MessageEntity as m where ( m.receiver.userId <> ? and m.sender.userId = ? ) and m.type= ?  order by createDate desc" ;
		String hql2= "SELECT DISTINCT m.sender.userId from MessageEntity as m where ( m.receiver.userId = ? and m.sender.userId <> ? ) and m.type= ?  order by createDate desc" ;
		List<NotificationsEntity> list = getHibernateTemplate().find(hql,userId,userId,Constants.MESSAGE_PRIVATELETTER);
		List<NotificationsEntity> list2 = getHibernateTemplate().find(hql2,userId,userId,Constants.MESSAGE_PRIVATELETTER);
		return trimList(list ,list2 );
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List trimList(List list, List list2) {
		if(list!=null&&list2!=null){
			int listLen = list.size();
			for (int i = listLen - 1; i >= 0; i--) {
				Object o = list.get(i);
				if (list2.indexOf(o) == -1) {
					list2.add(0, o);
				}
			}
			return list2;
		}
		return new ArrayList();
	}


}
