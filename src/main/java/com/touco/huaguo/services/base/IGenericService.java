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
package com.touco.huaguo.services.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.touco.huaguo.common.DataGridModel;

/**
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
public interface IGenericService<T,PK extends Serializable> {

	List<T> getAll() throws Exception;
	T get(PK id) throws Exception;
	boolean exists(PK id) throws Exception;
	T saveOrUpdate(T object) throws Exception;
	List<T> saveOrUpdateAll(List<T> tList) throws Exception;
	void remove(T object) throws Exception;
	void remove(PK id) throws Exception;
	void removeAll(List<PK> idList) throws Exception;
	
	T update(T object)throws Exception;
	
	public void updateII(List<T> entity) throws Exception ;
	
	List<T> findByNamedQuery(String queryName,Map<String, Object> queryParams) throws Exception;
	Map<String, Object> getPageListByExemple(DataGridModel dgm,T object) throws Exception;
	Map<String, Object> getPageListByCriteria(DataGridModel dgm,Map<String, Map<String, Object>> queryParams)  throws Exception;
	Map<String, Object> getPageListByManyEntity(DataGridModel dgm,Map<String, Map<String, Map<String, Object>>> queryParams,String distinctProperty)  throws Exception;

	
	/**
	 * 生成树形结构主键ID
	 * @author Shizhongying
	 * @param entityName
	 * @param parentid
	 * @param idFieldName
	 * @return
	 */
	public  String generatorTreeID(String entityName, String parentid,String idFieldName);
	
	public List<T> getListByCriteria(DataGridModel dgm,Map<String, Map<String, Object>> queryParams)  throws Exception;
}
