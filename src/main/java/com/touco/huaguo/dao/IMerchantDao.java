package com.touco.huaguo.dao;

import java.util.List;

import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.domain.MerchantEntity;

/**
 * 
 * @author 史中营
 * 
 */
public interface IMerchantDao extends IGenericDao<MerchantEntity, Long> {
	public boolean isMerchantExist(Long merchantId, String name);

	/**
	 * 条件查询店铺列表
	 * 
	 * @author 史中营
	 * @param where
	 * @return
	 */
	public List<MerchantEntity> getMerchantList(String where);

	public boolean delMerchant(MerchantEntity merchant);

	public boolean delMerchantList(List<Long> merchantList);
}
