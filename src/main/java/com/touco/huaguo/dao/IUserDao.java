package com.touco.huaguo.dao;


import java.util.List;

import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.domain.UserRef;

public interface IUserDao extends IGenericDao<UserEntity, Long> 
{
	/**
	 * @param email
	 * @param regType
	 * @return
	 */
	boolean checkUserEmail(String email, String regType);
	
	/**
	 * @param nickName
	 * @param regType
	 * @return
	 */
	boolean checkUserNickName(String nickName, String regType);
	

	
	/**
	 * @param email
	 * @return
	 */
	UserEntity getUserByEmail(String email) ;

	/**
	 * @param email
	 * @param pwd
	 * @return
	 */
	UserEntity checkUserIsExist(String email, String pwd);

	/**
	 * @param saveUser
	 * @return
	 */
	Long txSave(UserEntity saveUser);
	
	/**
	 * @param userId
	 * @param email
	 * @param regType
	 * @return
	 */
	boolean checkUserEmail(Long userId,String email, String regType);
	
	/**
	 * @param userId
	 * @param nickName
	 * @param regType
	 * @return
	 */
	public boolean checkUserNickName(Long userId,String nickName, String regType);

	/**
	 * @param screenName
	 * @param regType
	 * @return
	 */
	UserEntity getUserNickName(String screenName, String regType);

	/**
	 * @param userId
	 * @return
	 */
	List<UserRef> getUserRefsByUserId(Long userId);

	/**
	 * @param screenName2
	 * @param regType
	 * @return
	 */
	UserRef checkUserRefNickName(String screenName2, String regType);

	/**
	 * @param refId
	 * @return
	 */
	Boolean removeUserRef(Long refId);

	Boolean txRemoveAll(List<Long> list);
}
