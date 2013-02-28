package com.touco.huaguo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IMerchantCommentDao;
import com.touco.huaguo.domain.MerchantCommentEntity;
import com.touco.huaguo.services.IMerchantCommentService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * 
 * @author 史中营
 *
 */
@Service("merchantCommentService")
public class MerchantCommentServiceImpl extends GenericServiceImpl<MerchantCommentEntity, Long> implements IMerchantCommentService {

	private IMerchantCommentDao merchantCommentDao;

	@Autowired
	public void setMerchantCommentDao(IMerchantCommentDao merchantCommentDao) {
		this.dao = merchantCommentDao;
		this.merchantCommentDao = merchantCommentDao;
	}

}
