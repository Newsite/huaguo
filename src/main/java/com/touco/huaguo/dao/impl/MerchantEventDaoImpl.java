package com.touco.huaguo.dao.impl;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.IMerchantEventDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEventEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("merchantEventDao")
public class MerchantEventDaoImpl extends GenericDaoImpl<MerchantEventEntity, Long> implements IMerchantEventDao{

	public MerchantEventDaoImpl() {
		super(MerchantEventEntity.class);
	}

}
