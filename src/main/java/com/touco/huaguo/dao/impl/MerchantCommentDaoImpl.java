package com.touco.huaguo.dao.impl;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.IMerchantCommentDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantCommentEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("merchantCommentDao")
public class MerchantCommentDaoImpl extends GenericDaoImpl<MerchantCommentEntity, Long> implements IMerchantCommentDao{

	public MerchantCommentDaoImpl() {
		super(MerchantCommentEntity.class);
	}

}
