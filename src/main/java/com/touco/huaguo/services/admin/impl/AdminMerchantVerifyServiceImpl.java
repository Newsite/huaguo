/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.services.admin.impl.AdminMerchantVerifyServiceImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-27上午10:27:11
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.admin.IAdminMerchantVerifyDao;
import com.touco.huaguo.domain.MerchantVerifyEntity;
import com.touco.huaguo.services.admin.IAdminMerchantVerifyService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;


@Service("adminMerchantVerifyService")
public class AdminMerchantVerifyServiceImpl extends
		GenericServiceImpl<MerchantVerifyEntity, Long> implements
		IAdminMerchantVerifyService {

	
	private IAdminMerchantVerifyDao adminMerchantVerifyDao;
	
	@Autowired
	public void setIAdminMerchantVerifyDao(IAdminMerchantVerifyDao adminMerchantVerifyDao) {
		this.dao = adminMerchantVerifyDao;
		this.adminMerchantVerifyDao = adminMerchantVerifyDao;
	}
	

}
