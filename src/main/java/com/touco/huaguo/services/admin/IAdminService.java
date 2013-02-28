package com.touco.huaguo.services.admin;

import java.util.List;

import com.touco.huaguo.domain.AdminEntity;
import com.touco.huaguo.services.base.IGenericService;

/**
 * 
 * @author 史中营
 * 
 */
public interface IAdminService extends IGenericService<AdminEntity, Long> {

	/**
	 * 根据要查找的字段和字段值返回结果
	 * 
	 * @param queryName
	 * @param value
	 * @return List<AdministratorProfileEntity>
	 */
	public List<AdminEntity> findByNamedParam(String queryName, String value);
}
