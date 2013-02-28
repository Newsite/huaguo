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
import com.touco.huaguo.domain.NotificationsEntity;
import com.touco.huaguo.domain.TokenEntity;

/**
 * @author baozhenyu
 * 
 * 
 */

public interface ITokenDao extends IGenericDao<TokenEntity, Long> {
	
	/**
	 * 根据加密串查找链接数据
	 * @return
	 */
	public TokenEntity getTokenByString(String token);
	
	/**
	 * 更新该用户所有找回密码链接为已使用
	 * @param email
	 * @return
	 */
	public boolean updateTokenUse(String email);
}
