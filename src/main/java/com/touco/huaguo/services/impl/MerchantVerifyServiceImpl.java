package com.touco.huaguo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IMerchantVerifyDao;
import com.touco.huaguo.domain.MerchantVerifyEntity;
import com.touco.huaguo.services.IMerchantVerifyService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;
/**
 * 
 * @author 史中营
 *
 */

@Service("merchantVerifyService")
public class MerchantVerifyServiceImpl extends GenericServiceImpl<MerchantVerifyEntity, Long> implements IMerchantVerifyService {

	private IMerchantVerifyDao merchantVerifyDao;

	@Autowired
	public void setMerchantVerifyDao(IMerchantVerifyDao merchantVerifyDao) {
		this.dao = merchantVerifyDao;
		this.merchantVerifyDao = merchantVerifyDao;
	}

}
