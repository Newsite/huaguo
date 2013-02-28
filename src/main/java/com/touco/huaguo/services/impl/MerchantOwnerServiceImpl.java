package com.touco.huaguo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IMerchantOwnerDao;
import com.touco.huaguo.domain.MerchantOwnerEntity;
import com.touco.huaguo.services.IMerchantOwnerService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

@Service("merchantOwnerService")
public class MerchantOwnerServiceImpl extends GenericServiceImpl<MerchantOwnerEntity, Long> implements IMerchantOwnerService {

	private IMerchantOwnerDao merchantOwnerDao;

	@Autowired
	public void setMerchantOwnerDao(IMerchantOwnerDao merchantOwnerDao) {
		this.dao = merchantOwnerDao;
		this.merchantOwnerDao = merchantOwnerDao;
	}

	/** (non-Javadoc)
	 * @see com.touco.huaguo.services.IMerchantOwnerService#getMerchantOwnerByUserIdAndMerchantId(java.lang.Long, java.lang.Long)
	 * @param ownerId
	 * @param merchantId
	 * @return
	 */
	public MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId(Long ownerId, Long merchantId) {
		return merchantOwnerDao.getMerchantOwnerByUserIdAndMerchantId(ownerId,merchantId);
	}
	
	
	/** (non-Javadoc)
	 * @see com.touco.huaguo.services.IMerchantOwnerService#getMerchantOwnerByUserIdAndMerchantId(java.lang.Long, java.lang.Long)
	 * @param ownerId
	 * @param merchantId
	 * @return
	 */
	public MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId2(Long ownerId, Long merchantId) {
		return merchantOwnerDao.getMerchantOwnerByUserIdAndMerchantId2(ownerId,merchantId);
	}
}
