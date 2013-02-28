package com.touco.huaguo.dao;

import java.util.List;

import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.domain.CityEntity;

/**
 * 
 * @author 史中营
 * 
 */
public interface ICityDao extends IGenericDao<CityEntity, String>
{
	public List<CityEntity> findAll(String parentId);
}
