package com.touco.huaguo.dao.base;

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
public interface IGenericDao<T,PK extends Serializable> {
	List<T> getAll()  throws Exception;
	T get(PK id)  throws Exception;
	boolean exists(PK id)  throws Exception;
	T saveOrUpdate(T object)  throws Exception;
	List<T> saveOrUpdateAll(List<T> tList) throws Exception;
	T update(T object)  throws Exception;
	void remove(T object)  throws Exception;
	void remove(PK id)  throws Exception;
	void removeAll(List<PK> idList)  throws Exception;
	List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams)  throws Exception;
	Map<String, Object> getPageListByExemple(DataGridModel dgm,T object)  throws Exception;
	Map<String, Object> getPageListByCriteria(DataGridModel dgm,Map<String, Map<String, Object>> queryParams)  throws Exception;
	Map<String, Object> getPageListByManyEntity(DataGridModel dgm,Map<String, Map<String, Map<String, Object>>> queryParams,String distinctProperty)  throws Exception;

	
	public void updateII(List<T> entity) throws Exception ;
	
	/**
	 * 生成主键ID 树型结构
	 * @author Shizhongying
	 * @param entityName
	 * @param parentid
	 * @param idFieldName
	 * @return
	 */
	public  String generatorTreeID(String entityName, String parentid,String idFieldName);
	
	public List<T> getListByCriteria(DataGridModel dgm,Map<String, Map<String, Object>> queryParams) throws Exception;

}
