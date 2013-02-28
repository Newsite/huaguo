/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.dao.admin.impl.AdminMerchantVerifyDaoImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-27下午1:48:31
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.dao.admin.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.dao.admin.IAdminMerchantVerifyDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.MerchantVerifyEntity;

/**
 * @author shizhongying
 * 
 *
 */
@Repository("adminMerchantVerifyDao")
public class AdminMerchantVerifyDaoImpl extends GenericDaoImpl<MerchantVerifyEntity, Long> implements
		IAdminMerchantVerifyDao {

	private static Logger logger = Logger.getLogger(AdminMerchantVerifyDaoImpl.class);
	
	public AdminMerchantVerifyDaoImpl() {
		super(MerchantVerifyEntity.class);
	}


	

}
