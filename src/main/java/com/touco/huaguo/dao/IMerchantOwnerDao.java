package com.touco.huaguo.dao;

import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.domain.MerchantOwnerEntity;

/**
 * 
 * @author 史中营
 *
 */
public interface IMerchantOwnerDao extends IGenericDao<MerchantOwnerEntity, Long>{

	MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId(Long ownerId,
			Long merchantId);
	
	MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId2(Long ownerId,
			Long merchantId);

}
