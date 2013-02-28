package com.touco.huaguo.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.IDianPingDao;
import com.touco.huaguo.domain.DianPingEntity;
import com.touco.huaguo.services.IDianPingService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * 
 * @author 史中营
 *
 */
@Service("dianPingService")
public class DianPingServiceImpl extends GenericServiceImpl<DianPingEntity, Long> implements IDianPingService {

	private IDianPingDao dianPingDao;

	@Autowired
	public void setDianPingDao(IDianPingDao dianPingDao) {
		this.dao = dianPingDao;
		this.dianPingDao = dianPingDao;
	}
	
}
