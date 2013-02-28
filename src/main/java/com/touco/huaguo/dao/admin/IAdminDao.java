package com.touco.huaguo.dao.admin;

import java.util.List;

import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.domain.AdminEntity;

/**
 * 
 * 
 */
public interface IAdminDao extends IGenericDao<AdminEntity, Long> {

	/**
	 * 根据要查找的字段和字段值返回结果
	 * 
	 * @author 史中营
	 * @param paramName
	 * @param value
	 * @return List<AdministratorProfileEntity>
	 */
	List<AdminEntity> findByNamedParam(String paramName, String value);
}
