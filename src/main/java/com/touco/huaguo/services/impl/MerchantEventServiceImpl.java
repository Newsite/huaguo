package com.touco.huaguo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IMerchantEventDao;
import com.touco.huaguo.domain.MerchantEventEntity;
import com.touco.huaguo.services.IMerchantEventService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * 
 * @author 史中营
 *
 */
@Service("merchantEventService")
public class MerchantEventServiceImpl extends GenericServiceImpl<MerchantEventEntity, Long> implements IMerchantEventService {

	private IMerchantEventDao merchantEventDao;

	@Autowired
	public void setMerchantEventDao(IMerchantEventDao merchantEventDao) {
		this.dao = merchantEventDao;
		this.merchantEventDao = merchantEventDao;
	}

}
