package com.touco.huaguo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.IMerchantEventDao;
import com.touco.huaguo.dao.IMerchantOwnerDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEventEntity;
import com.touco.huaguo.domain.MerchantOwnerEntity;
import com.touco.huaguo.domain.UserEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("merchantOwnerDao")
public class MerchantOwnerDaoImpl extends GenericDaoImpl<MerchantOwnerEntity, Long> implements IMerchantOwnerDao{

	public MerchantOwnerDaoImpl() {
		super(MerchantOwnerEntity.class);
	}

	/** (non-Javadoc)
	 * @see com.touco.huaguo.dao.IMerchantOwnerDao#getMerchantOwnerByUserIdAndMerchantId(java.lang.Long, java.lang.Long)
	 * @param ownerId
	 * @param merchantId
	 * @return
	 */
	public MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId(
			Long ownerId, Long merchantId) {
			
		MerchantOwnerEntity merchantOwnerEntity =null;
		try {
			String hql = "from MerchantOwnerEntity where user.userId = " + ownerId + " and merchant.merchantId=" + merchantId+" order by createDate desc" ;
			
			List<MerchantOwnerEntity> merchantList = getHibernateTemplate().find(hql);
			if (merchantList != null && merchantList.size() > 0) {
				merchantOwnerEntity = merchantList.get(0);
			}
		} catch (Exception e) {
			
		}
		
		return merchantOwnerEntity;
	}
	
	
	public MerchantOwnerEntity getMerchantOwnerByUserIdAndMerchantId2(
			Long ownerId, Long merchantId) {
			
		MerchantOwnerEntity merchantOwnerEntity =null;
		try {
			String hql = "from MerchantOwnerEntity where isOpen='0' and user.userId = " + ownerId + " and merchant.merchantId=" + merchantId+" order by createDate desc" ;
			
			List<MerchantOwnerEntity> merchantList = getHibernateTemplate().find(hql);
			if (merchantList != null && merchantList.size() > 0) {
				merchantOwnerEntity = merchantList.get(0);
			}
		} catch (Exception e) {
			
		}
		
		return merchantOwnerEntity;
	}
}
