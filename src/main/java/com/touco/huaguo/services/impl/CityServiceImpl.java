package com.touco.huaguo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.ICityDao;
import com.touco.huaguo.domain.CityEntity;
import com.touco.huaguo.services.ICityService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * 
 * @author 史中营
 *
 */
@Service("cityService")
public class CityServiceImpl extends GenericServiceImpl<CityEntity, String> implements ICityService {

	private ICityDao cityDao;

	@Autowired
	public void setCityDao(ICityDao cityDao) {
		this.dao = cityDao;
		this.cityDao = cityDao;
	}
	
	public List<CityEntity> findAll(String parentId)
	{
		return cityDao.findAll(parentId);
	}
}
