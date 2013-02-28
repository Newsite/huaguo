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

import com.touco.huaguo.dao.IMerchantLikeDao;
import com.touco.huaguo.domain.MerchantLikeEntity;
import com.touco.huaguo.services.IMerchantLikeService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * @author baozhenyu
 * 餐厅喜欢Service
 *
 */
@Service("merchantLikeService")
public class MerchantLikeServiceImpl extends GenericServiceImpl<MerchantLikeEntity , Long> implements IMerchantLikeService {

	private IMerchantLikeDao merchantLikeDao;

	@Autowired
	public void setIMerchantLikeDao(IMerchantLikeDao merchantLikeDao) {
		this.dao = merchantLikeDao;
		this.merchantLikeDao=merchantLikeDao;
	}
	
	public boolean delMerchantLike(Long recordId)
	{
		return merchantLikeDao.delMerchantLike(recordId);
	}
}
