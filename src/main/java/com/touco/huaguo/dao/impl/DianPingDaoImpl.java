package com.touco.huaguo.dao.impl;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.IDianPingDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.DianPingEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("dianPingDao")
public class DianPingDaoImpl extends GenericDaoImpl<DianPingEntity, Long> implements IDianPingDao {

	public DianPingDaoImpl() {
		super(DianPingEntity.class);
	}

}
