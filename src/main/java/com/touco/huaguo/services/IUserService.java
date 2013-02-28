/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.services.IUserService.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-21下午12:35:14
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services;

import java.util.List;

import com.touco.huaguo.domain.UserEntity;
import com.touco.huaguo.domain.UserRef;
import com.touco.huaguo.services.base.IGenericService;

/**
 * @author shizhongying
 *
 */
public interface IUserService extends IGenericService<UserEntity, Long> {

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

	public UserEntity getUserByEmail(String email);

	UserEntity checkUserIsExist(String email, String pwd);
	
	public boolean checkUserEmail(Long userId,String email, String regType);
	boolean checkUserNickName(Long userId,String nickName, String regType);

	UserEntity getUserNickName(String screenName, String regType);

	/**
	 * @param userId
	 * @param refId
	 * @param string , Long refId, String apptype
	 * @return
	 */
	List<UserRef> getUserRefsByUserId(Long userId);

	UserRef checkUserRefNickName(String screenName2, String regType);

	/**
	 * @param refId
	 */
	Boolean removeUserRef(Long refId);

	Boolean txRemoveAll(List<Long> list);

}
