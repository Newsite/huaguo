package com.touco.huaguo.dao.impl;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.IMerchantDao;
import com.touco.huaguo.dao.IMerchantVerifyDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.MerchantVerifyEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("merchantVerifyDao")
public class MerchantVerifyDaoImpl extends GenericDaoImpl<MerchantVerifyEntity, Long> implements IMerchantVerifyDao{

	public MerchantVerifyDaoImpl() {
		super(MerchantVerifyEntity.class);
	}

}
