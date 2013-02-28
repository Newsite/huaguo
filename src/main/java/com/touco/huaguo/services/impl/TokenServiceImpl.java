/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.services.impl.MerchantLikeServiceImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午11:22:08
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.ITokenDao;
import com.touco.huaguo.domain.TokenEntity;
import com.touco.huaguo.services.ITokenService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * @author baozhenyu
 * 密码找回Service
 *
 */
@Service("tokenService")
public class TokenServiceImpl extends GenericServiceImpl<TokenEntity , Long> implements ITokenService {

	private ITokenDao tokenDao;

	@Autowired
	public void setITokenDao(ITokenDao tokenDao) {
		this.dao = tokenDao;
		this.tokenDao=tokenDao;
	}

	public TokenEntity getTokenByString(String token) 
	{
		return tokenDao.getTokenByString(token);
	}

	public boolean updateTokenUse(String email)
	{
		return tokenDao.updateTokenUse(email);
	}
}
