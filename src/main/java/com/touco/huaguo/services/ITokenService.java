/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.services.ITokenService.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午10:48:44
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services;

import com.touco.huaguo.domain.TokenEntity;
import com.touco.huaguo.services.base.IGenericService;


/**
 * @author baozhenyu
 * 密码找回Service接口
 *
 */
public interface ITokenService extends IGenericService<TokenEntity, Long>{

	
	/**
	 * 根据加密串查找链接数据
	 * @return
	 */
	public TokenEntity getTokenByString(String token);
	
	public boolean updateTokenUse(String email);
	
}
