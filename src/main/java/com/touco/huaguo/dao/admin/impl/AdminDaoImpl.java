package com.touco.huaguo.dao.admin.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.touco.huaguo.dao.admin.IAdminDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.AdminEntity;

/**
 * @author Administrator
 *
 */
@Repository("adminDao")
public class AdminDaoImpl extends GenericDaoImpl<AdminEntity, Long> implements IAdminDao {


	public AdminDaoImpl() {
		super(AdminEntity.class);
	}

	/**
	 * 根据要查找的字段和字段值返回结果
	 * 
	 * @param paramName
	 * @param value
	 * @return List<AdministratorProfileEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AdminEntity> findByNamedParam(String paramName, String value) {
		StringBuffer sb = new StringBuffer("from AdminEntity a");
		sb.append(" where a.").append(paramName);
		sb.append(" = '").append(value).append("'");
		return this.getHibernateTemplate().find(sb.toString());
	}
}
