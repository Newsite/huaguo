/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.services.impl.UserServiceImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-21下午12:37:42
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IUserDao;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.domain.UserRef;
import com.touco.huaguo.services.IUserService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * @author shizhongying
 * 
 */
@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<UserEntity, Long> implements IUserService {

	private IUserDao userDao;

	@Autowired
	public void setIUserDao(IUserDao userDao) {
		this.dao = userDao;
		this.userDao = userDao;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#getUserEmail(java.lang.String,
	 *      java.lang.String)
	 * @param email
	 * @param regType
	 * @return
	 */
	public boolean checkUserEmail(String email, String regType) {
		return userDao.checkUserEmail(email, regType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#checkUserNickName(java.lang.String,
	 *      java.lang.String)
	 * @param nickName
	 * @param regType
	 * @return
	 */
	public boolean checkUserNickName(String nickName, String regType) {
		return userDao.checkUserNickName(nickName, regType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#checkUserIsExist(java.lang.String,
	 *      java.lang.String)
	 * @param email
	 * @param pwd
	 * @return
	 */
	public UserEntity checkUserIsExist(String email, String pwd) {
		return userDao.checkUserIsExist(email, pwd);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#getUserEmail(java.lang.String,
	 *      java.lang.String)
	 * @param email
	 * @param regType
	 * @return
	 */
	public UserEntity getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	public boolean checkUserEmail(Long userId, String email, String regType) {
		return userDao.checkUserEmail(userId, email, regType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#checkUserNickName(java.lang.Long,
	 *      java.lang.String, java.lang.String)
	 * @param userId
	 * @param nickName
	 * @param regType
	 * @return
	 */
	public boolean checkUserNickName(Long userId, String nickName, String regType) {
		return userDao.checkUserNickName(userId, nickName, regType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#getUserNickName(java.lang.String,
	 *      java.lang.String)
	 * @param screenName
	 * @param regType
	 * @return
	 */
	public UserEntity getUserNickName(String screenName, String regType) {
		return userDao.getUserNickName(screenName, regType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#getUserRefsByUserIdAndRefsId(java.lang.Long,
	 *      java.lang.Long, java.lang.String)
	 * @param userId
	 * @param refId
	 *            --TODO
	 * @param apptype
	 *            --todo
	 * @return
	 */
	public List<UserRef> getUserRefsByUserId(Long userId) {
		return userDao.getUserRefsByUserId(userId);
	}

	public UserRef checkUserRefNickName(String screenName2, String regType) {
		return userDao.checkUserRefNickName(screenName2, regType);
	}

	/**
	 * 取消绑定 (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#removeUserRef(java.lang.Long)
	 * @param refId
	 * @return
	 */
	public Boolean removeUserRef(Long refId) {
		return userDao.removeUserRef(refId);
	}

	/**
	 * 后台删除删除关联信息 (non-Javadoc)
	 * 
	 * @see com.touco.huaguo.services.IUserService#txRemoveAll(java.util.List)
	 * @param list
	 * @return
	 */
	public Boolean txRemoveAll(List<Long> list) {
		return userDao.txRemoveAll(list);
	}
}
