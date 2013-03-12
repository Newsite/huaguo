package com.touco.huaguo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.ICityDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.CityEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("cityDao")
public class CityDaoImpl extends GenericDaoImpl<CityEntity, String> implements ICityDao {

	public CityDaoImpl() {
		super(CityEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CityEntity> findAll(String parentId) {
		List<CityEntity> areaList = new ArrayList<CityEntity>();
		StringBuffer hql = new StringBuffer("from CityEntity a ");
		if (parentId == null || StringUtils.isBlank(parentId)) {
			hql.append(" where (a.parentId=0 or a.parentId is null) ");
		} else {
			hql.append(" where a.parentId='" + parentId + "' ");
		}

		hql.append(" order by a.sortNo asc ");

		areaList = getHibernateTemplate().find(hql.toString());

		return areaList;
	}

}
