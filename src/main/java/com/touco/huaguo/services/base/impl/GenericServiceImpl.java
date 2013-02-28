/**
 * Copyright(C) 2011-2013 Touco WuXi Technology LTD. All Rights Reserved.  
 * 版权所有(C) 2011-2013 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技有限公司 
 * 
 * 网址:http://www.touco.cn
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2011-12-14上午11:09:36
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.services.base.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.dao.base.IGenericDao;
import com.touco.huaguo.services.base.IGenericService;

public class GenericServiceImpl<T, PK extends Serializable> implements IGenericService<T, PK> {

	protected IGenericDao<T, PK> dao;

	public GenericServiceImpl() {
	}

	public GenericServiceImpl(IGenericDao<T, PK> genericDao) {
		this.dao = genericDao;
	}

	public List<T> getAll() throws Exception {
		return dao.getAll();
	}

	public T get(PK id) throws Exception {
		return dao.get(id);
	}

	public boolean exists(PK id) throws Exception {
		return dao.exists(id);
	}

	public T saveOrUpdate(T object) throws Exception {
		return dao.saveOrUpdate(object);
	}

	public List<T> saveOrUpdateAll(List<T> tList) throws Exception {
		return dao.saveOrUpdateAll(tList);
	}

	public void remove(T object) throws Exception {
		dao.remove(object);
	}

	public void remove(PK id) throws Exception {
		dao.remove(id);
	}

	public void removeAll(List<PK> idList) throws Exception {
		dao.removeAll(idList);
	}

	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) throws Exception {
		return dao.findByNamedQuery(queryName, queryParams);
	}

	public Map<String, Object> getPageListByExemple(DataGridModel dgm, T object) throws Exception {
		return dao.getPageListByExemple(dgm, object);
	}

	public Map<String, Object> getPageListByCriteria(DataGridModel dgm, Map<String, Map<String, Object>> queryParams) throws Exception {
		return dao.getPageListByCriteria(dgm, queryParams);
	}

	public T update(T object) throws Exception {
		return dao.update(object);
	}

	public Map<String, Object> getPageListByManyEntity(DataGridModel dgm, Map<String, Map<String, Map<String, Object>>> queryParams, String distinctProperty) throws Exception {
		return dao.getPageListByManyEntity(dgm, queryParams, distinctProperty);
	}

	/**
	 * @author Shizhongying
	 * @date 2012/01/05 生成树形结构主键ID (non-Javadoc)
	 * @see com.touco.ccmp.services.sys.IDeptService#generatorTreeID(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String generatorTreeID(String entityName, String parentid, String idFieldName) {
		return dao.generatorTreeID(entityName, parentid, idFieldName);
	}

	public List<T> getListByCriteria(DataGridModel dgm, Map<String, Map<String, Object>> queryParams) throws Exception {
		return dao.getListByCriteria(dgm, queryParams);
	}

	public void updateII(List<T> entity) throws Exception {
		dao.updateII(entity);
	}

}
