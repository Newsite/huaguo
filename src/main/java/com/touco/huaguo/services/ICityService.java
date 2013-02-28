package com.touco.huaguo.services;

import java.util.List;

import com.touco.huaguo.domain.CityEntity;
import com.touco.huaguo.services.base.IGenericService;

/**
 * 
 * @author 史中营
 *
 */
public interface ICityService extends IGenericService<CityEntity, String> 
{
	public List<CityEntity> findAll(String parentId); 
}
