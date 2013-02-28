/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.dao.impl.UserDaoImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-21下午12:40:45
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.IUserDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.domain.UserRef;

/**
 * @author shizhongying 用户Dao 实现类
 * 
 */
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<UserEntity, Long> implements
		IUserDao {

	public UserDaoImpl() {
		super(UserEntity.class);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.dao.IUserDao#getUserEmail(java.lang.String,
	 *      java.lang.String)
	 * @param email
	 *            邮箱
	 * @param regType
	 *            注册类型 0--站内 1--微博
	 * @return
	 */
	public boolean checkUserEmail(String email, String regType) {

		Boolean result = false;
		StringBuffer hql = new StringBuffer("from UserEntity where email = '"
				+ email + "' and regType='" + regType + "'");
		List<?> list = getHibernateTemplate().find(hql.toString());
		if (list.size() > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.dao.IUserDao#checkUserNickName(java.lang.String,
	 *      java.lang.String)
	 * @param nickName
	 * @param regType
	 * @return
	 */
	public boolean checkUserNickName(String nickName, String regType) {
		Boolean result = false;
		StringBuffer hql = new StringBuffer(
				"from UserEntity where delTag='0' and nickName = '" + nickName
						+ "' and regType='" + regType + "'");
		List<?> list = getHibernateTemplate().find(hql.toString());
		if (list.size() > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.dao.IUserDao#txSave(com.touco.huaguo.domain.UserEntity)
	 * @param saveUser
	 * @return
	 */
	public Long txSave(UserEntity saveUser) {
		Session session = null;
		Transaction tx = null;
		Long result = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();// 得到session
			tx = session.beginTransaction(); // 打开事务（针对读数据库）
			session.saveOrUpdate(saveUser);
			result = saveUser.getUserId();
			tx.commit();// 提交事务
		} catch (Exception e) {
			tx.rollback();// 回滚事务
			return result;
		} finally {
			session.close(); // 关闭session
		}
		return result;
	}

	/**
	 * 登录 (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.dao.IUserDao#checkUserIsExist(java.lang.String,
	 *      java.lang.String)
	 * @param email
	 * @param pwd
	 * @return
	 */
	public UserEntity checkUserIsExist(String email, String pwd) {
		UserEntity user = null;
		try {
			StringBuffer hql = new StringBuffer("from UserEntity u where delTag=0");
			if (StringUtils.isNotBlank(email)) {
				hql.append(" and u.email='" + email + "'");
			}
			if (StringUtils.isNotBlank(pwd)) {
				hql.append(" and u.password='" + pwd + "'");
			}
			List<UserEntity> userList = getHibernateTemplate().find(hql.toString());
			if (userList != null && userList.size() > 0) {
				user = userList.get(0);
			}
		} catch (Exception e) {

		}
		return user;
	}

	/** (non-Javadoc)
	 * @see com.touco.huaguo.dao.IUserDao#getUserByEmail(java.lang.String)
	 * @param email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserEntity getUserByEmail(String email) {
		UserEntity result = null;
		String eamilHql = "from UserEntity as u where u.email = ? and regType='0'";
		List<UserEntity> list = getHibernateTemplate().find(eamilHql,email);
		if (null != list && !list.isEmpty()) {
			result = list.get(0);
		}
		return result;
	}
	
	public boolean checkUserEmail(Long userId,String email, String regType) {

		Boolean result = false;
		StringBuffer hql = new StringBuffer("from UserEntity where userId!="+userId+" and email = '"
				+ email + "' and regType='" + regType + "'");
		List<?> list = getHibernateTemplate().find(hql.toString());
		if (list.size() > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public boolean checkUserNickName(Long userId, String nickName,
			String regType) {
		Boolean result = false;
		StringBuffer hql = new StringBuffer(
				"from UserEntity where userId!="+userId+" and nickName = '" + nickName
						+ "' and regType='" + regType + "'");
		List<?> list = getHibernateTemplate().find(hql.toString());
		if (list.size() > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 根据昵称查找用户信息
	 * (non-Javadoc)
	 * @see com.touco.huaguo.dao.IUserDao#getUserNickName(java.lang.String, java.lang.String)
	 * @param screenName 昵称
	 * @param regType  站外用户
	 * @return 
	 */
	public UserEntity getUserNickName(String screenName, String regType) {
		UserEntity entity = null;
		String  hql =  "from UserEntity where  nickName = '" + screenName + "' and regType='" + regType + "'" ;
		List<UserEntity> list = getHibernateTemplate().find(hql);
		if (null != list && !list.isEmpty()) {
			entity = list.get(0);
		}
		return entity;
	}

	/**
	 * 查看是否有绑定微博
	 *  (non-Javadoc)
	 * @see com.touco.huaguo.dao.IUserDao#getUserRefsByUserIdAndRefsId(java.lang.Long, java.lang.Long, java.lang.String)
	 * @param userId
	 * @param refId
	 * @param regType 1:新浪  2:腾讯
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserRef> getUserRefsByUserId(Long userId) {
		String hql = "from UserRef ur where ur.user.userId="+userId;
		List<UserRef> list = getHibernateTemplate().find(hql);
		if (null != list && !list.isEmpty()) {
			return list;
		}
		return Collections.emptyList();
	}

	/** (non-Javadoc)
	 * @see com.touco.huaguo.dao.IUserDao#checkUserRefNickName(java.lang.String, java.lang.String)
	 * @param screenName2
	 * @param regType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserRef checkUserRefNickName(String screenName2, String regType) {
		UserRef result = null;
		String hql = "from UserRef ur where ur.nickname = '" + screenName2 + "' and ur.apptype='" + regType + "'";
		List<UserRef> list = getHibernateTemplate().find(hql);
		if (list.size() > 0) {
			result = list.get(0);
		} 
		return result;
	}


	/**
	 *  取消绑定 
	 * (non-Javadoc)
	 * @see com.touco.huaguo.dao.IUserDao#removeUserRef(java.lang.Long)
	 * @param refId
	 * @return
	 */
	@Override
	public Boolean removeUserRef(Long refId) {
		Boolean result = false;
		int i = getSimpleJdbcTemplate().update("delete from HUAGUO_USERREF_TBL where refId=?", refId);
		if(i>0){
			result = true;
		}
		return result;
	}

	/** (non-Javadoc)
	 * @see com.touco.huaguo.dao.IUserDao#txRemoveAll(java.util.List)
	 * @param list
	 */
	public Boolean txRemoveAll(List<Long> list) {
		Session session = null;
		Transaction tx = null;
		Boolean result = false;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();// 得到session
			tx = session.beginTransaction(); // 打开事务（针对读数据库）
			for (Long userId  :list) {
				UserEntity entity = (UserEntity)session.get(com.touco.huaguo.domain.UserEntity.class, userId);
				session.delete(entity);//删除数据库记录
			
				String delverify = "delete from UserRef where user.userId="+userId;
				session.createQuery(delverify).executeUpdate();
				tx.commit();//提交事务 
				result = true;
			}
			//result = saveUser.getUserId();
		} catch (Exception e) {
			tx.rollback();// 回滚事务
		} finally {
			session.close(); // 关闭session
		}
		return result;
	}

}
