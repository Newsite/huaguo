package com.touco.huaguo.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.dao.admin.IAdminDao;
import com.touco.huaguo.domain.AdminEntity;
import com.touco.huaguo.services.admin.IAdminService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * 
 *
 */
@Service("adminService")
public class AdminServiceImpl extends GenericServiceImpl<AdminEntity, Long> implements IAdminService {

	private IAdminDao adminDao;

	@Autowired
	public void setIAdminDao(IAdminDao adminDao) {
		this.dao = adminDao;
		this.adminDao = adminDao;
	}
	
	/**
	 * 根据要查找的字段和字段值返回结果
	 * 
	 * @author 史中营
	 * @param queryName
	 * @param value
	 * @return List<AdministratorProfileEntity>
	 */
	@Override
	public List<AdminEntity> findByNamedParam(String queryName, String value) {
		return adminDao.findByNamedParam(queryName, value);
	}
}
