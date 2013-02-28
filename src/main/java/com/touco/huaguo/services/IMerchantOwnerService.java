package com.touco.huaguo.services;

import com.touco.huaguo.domain.MerchantOwnerEntity;
import com.touco.huaguo.services.base.IGenericService;

/**
 * 
 * @author 史中营
 *
 */
public interface IMerchantOwnerService extends IGenericService<MerchantOwnerEntity, Long> {

	MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId(Long ownerId,
			Long merchantId);
	
	MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId2(Long ownerId,
			Long merchantId);

}
