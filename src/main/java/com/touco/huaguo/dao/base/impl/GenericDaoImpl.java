package com.touco.huaguo.dao.base.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.dao.base.IGenericDao;

public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements IGenericDao<T, PK> {

	private static Logger logger = Logger.getLogger(GenericDaoImpl.class);

	private Class<T> persistentClass;
	private SimpleJdbcTemplate simpleJdbcTemplate;

	private DataSource dataSource;

	public GenericDaoImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public GenericDaoImpl(Class<T> persistentClass, SimpleJdbcTemplate simpleJdbcTemplate, DataSource dataSource) {
		this.persistentClass = persistentClass;
		this.simpleJdbcTemplate = simpleJdbcTemplate;
		this.dataSource = dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		return simpleJdbcTemplate;
	}

	public List<T> getAll() throws Exception {
		return getHibernateTemplate().loadAll(persistentClass);
	}

	public T get(PK id) throws Exception {
		T entity = null;
		if (id != null) {
			entity = (T) getHibernateTemplate().get(this.persistentClass, id);
		}

		/*
		 * if (entity == null) { logger.warn("Uh oh, '" + this.persistentClass +
		 * "' object with id '" + id + "' not found..."); throw new
		 * ObjectRetrievalFailureException(this.persistentClass, id); }
		 */

		return entity;
	}

	public boolean exists(PK id) throws Exception {
		T entity = null;
		if (id != null) {
			entity = this.get(id);
		}

		return entity != null;
	}

	public T saveOrUpdate(T object) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("object(T)" + object);
		}
		getHibernateTemplate().saveOrUpdate(object);
		getHibernateTemplate().flush();
		return object;
	}

	public List<T> saveOrUpdateAll(List<T> tList) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("List(T)" + tList);
		}
		getHibernateTemplate().saveOrUpdateAll(tList);
		getHibernateTemplate().flush();
		return tList;
	}

	public void remove(T object) throws Exception {
		getHibernateTemplate().delete(object);
		this.getHibernateTemplate().flush();
	}

	public void remove(PK id) throws Exception {
		// String sql="delete from  where id=?";
		// simpleJdbcTemplate.update(sql, id);
		getHibernateTemplate().delete(this.get(id));
		this.getHibernateTemplate().flush();
	}

	public void removeAll(List<PK> idList) throws Exception {
		List<T> tList = new ArrayList<T>();
		for (PK id : idList) {
			tList.add(this.get(id));
		}
		getHibernateTemplate().deleteAll(tList);
		this.getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) throws Exception {
		String[] params = new String[queryParams.size()];
		Object[] values = new Object[queryParams.size()];

		int index = 0;
		for (String s : queryParams.keySet()) {
			params[index] = s;
			values[index++] = queryParams.get(s);
		}

		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
	}

	/**
	 * 只�?用于单表查询且没有模糊查询条件和没有排序的情况下,或�?没有查询条件的情况下
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPageListByExemple(DataGridModel dgm, T object) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(2);
		List<T> totalList = getHibernateTemplate().findByExample(object);
		// 这里使用了findByExample，如果没有外键关联（我的hibernate配置文件没有配置主外键对应关系），用这个可以�?��很多�?
		List<T> pagelist = getHibernateTemplate().findByExample(object, (dgm.getPage() - 1) * dgm.getRows(), dgm.getRows());

		result.put(Constants.PAGE_TOTAL, totalList == null ? 0 : totalList.size());
		result.put(Constants.PAGE_ROWS, pagelist);
		return result;
	}

	/**
	 * 只�?用于单表查询和有(或没�?模糊查询条件的情况下,或�?没有查询条件的情况下(建议有模糊查询条件有此方�?
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<String, Object> getPageListByCriteria(DataGridModel dgm, Map<String, Map<String, Object>> queryParams) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(2);
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		setCriteria(criteria, queryParams, null);
		List<T> list = getHibernateTemplate().findByCriteria(criteria);
		int total = list == null ? 0 : list.size();

		if (dgm.getOrder() != null) {
			if (dgm.getOrder().equals(Constants.PAGE_ORDER_ASC)) {
				criteria.addOrder(Order.asc(dgm.getSort()));
			} else if (dgm.getOrder().equals(Constants.PAGE_ORDER_DESC)) {
				criteria.addOrder(Order.desc(dgm.getSort()));
			}
		}

		list = getHibernateTemplate().findByCriteria(criteria, (dgm.getPage() - 1) * dgm.getRows(), dgm.getRows());

		result.put(Constants.PAGE_TOTAL, total);
		result.put(Constants.PAGE_ROWS, list);

		return result;
	}

	/**
	 * 适用于实体之间存在关系的复杂查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPageListByManyEntity(DataGridModel dgm, Map<String, Map<String, Map<String, Object>>> queryParams, String distinctProperty) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(2);
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		if (null != distinctProperty && !"".equals(distinctProperty)) {
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.distinct(Projections.property(distinctProperty)));
			criteria.setProjection(proList);
		} else {
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		}
		setManyEntityRelevanceCriteria(criteria, queryParams);

		List<T> userList = getHibernateTemplate().findByCriteria(criteria);
		int total = userList == null ? 0 : userList.size();

		if (dgm.getOrder().equals(Constants.PAGE_ORDER_ASC)) {
			criteria.addOrder(Order.asc(dgm.getSort()));
		} else if (dgm.getOrder().equals(Constants.PAGE_ORDER_DESC)) {
			criteria.addOrder(Order.desc(dgm.getSort()));
		}
		userList = getHibernateTemplate().findByCriteria(criteria, (dgm.getPage() - 1) * dgm.getRows(), dgm.getRows());

		result.put(Constants.PAGE_TOTAL, total);
		result.put(Constants.PAGE_ROWS, userList);

		return result;
	}

	/**
	 * 设置多表复杂模糊查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setManyEntityRelevanceCriteria(DetachedCriteria criteria, Map<String, Map<String, Map<String, Object>>> queryParams) {
		if (queryParams.keySet() == null || queryParams.keySet().isEmpty()) {
			return;
		}

		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) == null || queryParams.get(key).isEmpty()) {
				continue;
			}

			if (!Constants.RELEVANCE_NULL.equals(key)) {
				criteria.createAlias(key, key);
				setCriteria(criteria, queryParams.get(key), key);
			} else {
				setCriteria(criteria, queryParams.get(key), null);
			}
		}
	}

	/**
	 * 设置模糊查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setCriteria(DetachedCriteria criteria, Map<String, Map<String, Object>> queryParams, String alias) {
		if (queryParams.keySet() == null || queryParams.keySet().isEmpty()) {
			return;
		}

		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) == null || queryParams.get(key).isEmpty()) {
				continue;
			}

			if (Constants.CRITERIA_LIKE.equals(key)) {
				setLikeCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_START_LIKE.equals(key)) {
				setStartLikeCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_END_LIKE.equals(key)) {
				setEndLikeCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_EQUALS.equals(key)) {
				setEqualsCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_IN.equals(key)) {
				setInCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_GT.equals(key)) {
				setGtCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_GE.equals(key)) {
				setGeCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_LT.equals(key)) {
				setLtCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_LE.equals(key)) {
				setLeCriteria(criteria, queryParams.get(key), alias);
			} else if (Constants.CRITERIA_BETWEEN.equals(key)) {
				setBetweenCriteria(criteria, queryParams.get(key), alias);
			}else if (Constants.CRITERIA_OR.equals(key)) {
				setOrCriteria(criteria, queryParams.get(key), alias);
			}else if (Constants.CRITERIA_ISNULL.equals(key)) {
				setIsNullCriteria(criteria, queryParams.get(key), alias);
			}else if (Constants.CRITERIA_OR_AND.equals(key)) {
				setOrAndCriteria(criteria, queryParams.get(key), alias);
			}
		}
	}

	/**
	 * 设置模糊查询的like条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setLikeCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null && StringUtils.isNotBlank(queryParams.get(key).toString())) {
				criteria.add(Restrictions.like(getConditionName(alias, key), queryParams.get(key).toString(), MatchMode.ANYWHERE));
			}
		}
	}

	private void setStartLikeCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null && StringUtils.isNotBlank(queryParams.get(key).toString())) {
				criteria.add(Restrictions.like(getConditionName(alias, key), queryParams.get(key).toString(), MatchMode.START));
			}
		}
	}

	/**
	 * 设置右边模糊查询的like条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setEndLikeCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null && StringUtils.isNotBlank(queryParams.get(key).toString())) {
				criteria.add(Restrictions.like(getConditionName(alias, key), queryParams.get(key).toString(), MatchMode.END));
			}
		}
	}

	/**
	 * 设置模糊查询的equals条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setEqualsCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.eq(getConditionName(alias, key), queryParams.get(key)));
			}
		}
	}
	
	
	/**
	 * 设置模糊查询的null条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setIsNullCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.isNull(getConditionName(alias, key)));
			}
		}
	}
	
	/**
	 * 设置大于查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setGtCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.gt(getConditionName(alias, key), queryParams.get(key)));
			}
		}
	}

	/**
	 * 设置大于等于查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setGeCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.ge(getConditionName(alias, key), queryParams.get(key)));
			}
		}
	}

	/**
	 * 设置小于查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setLtCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.lt(getConditionName(alias, key), queryParams.get(key)));
			}
		}
	}

	/**
	 * 设置小于等于查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setLeCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.le(getConditionName(alias, key), queryParams.get(key)));
			}
		}
	}

	/**
	 * 设置小于等于查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	@SuppressWarnings("unchecked")
	private void setBetweenCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				List<Date> dateList = (List<Date>) queryParams.get(key);
				if (dateList.size() >= 2 && dateList.get(0) != null && dateList.get(1) != null) {
					criteria.add(Restrictions.between(getConditionName(alias, key), dateList.get(0), dateList.get(1)));
				}
			}
		}
	}

	/**
	 * 设置模糊查询的in条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setInCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) {
		for (String key : queryParams.keySet()) {
			if (queryParams.get(key) != null) {
				criteria.add(Restrictions.in(getConditionName(alias, key), (String[]) queryParams.get(key)));
			}
		}
	}
	
	
	/**
	 * 设置或者查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setOrCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) 
	{
		String[] keyName = (String[])queryParams.get("keyName");
		String key1 = keyName[0];
		String key2 = keyName[1];
		Object[] keyValue = (Object[])queryParams.get("keyValue");
		Object value1 = keyValue[0];
		Object value2 = keyValue[1];
		criteria.add(Restrictions.or(Restrictions.eq(key1,value1), Restrictions.eq(key2,value2)));					
	}
	
	
	/**
	 * 设置或者查询条件
	 * 
	 * @param criteria
	 * @param queryParams
	 */
	private void setOrAndCriteria(DetachedCriteria criteria, Map<String, Object> queryParams, String alias) 
	{
		String[] keyName = (String[])queryParams.get("keyName");
		String key1 = keyName[0];
		String key2 = keyName[1];
		String key3 = keyName[2];
		String key4 = keyName[3];
		Object[] keyValue = (Object[])queryParams.get("keyValue");
		Object value1 = keyValue[0];
		Object value2 = keyValue[1];
		Object value3 = keyValue[2];
		Object value4 = keyValue[3];
		//Criterion cri1 = Restrictions.and(Restrictions.eq(key1,value1), Restrictions.eq(key2,value2));
		//Criterion cri2 = Restrictions.and(Restrictions.eq(key3,value3), Restrictions.eq(key4,value4));
		//criteria.add(Restrictions.or(cri1, cri2));
		Integer[] userIds = {Integer.valueOf(value1.toString()), Integer.valueOf(value3.toString())};
		Type[] types = {Hibernate.INTEGER,Hibernate.INTEGER};
		criteria.add(Restrictions.sqlRestriction(" ((sender_id=? and ownDelTag='0') or (receiver_id=? and othersDelTag='0'))", userIds, types));
//		criteria.add(Restrictions.or(
//                Restrictions.and(
//                      Restrictions.eq(key1, value1),
//                      Restrictions.eq(key2,value2)
//                ),
//                Restrictions.and(
//                      Restrictions.eq(key3, value3),
//                      Restrictions.eq(key4, value4)
//                )
//            )); 
	}
	
	
	/**
	 * 设置查询条件名称
	 * 
	 * @param alias
	 * @param filedName
	 * @return
	 */
	private String getConditionName(String alias, String filedName) {
		return alias != null ? alias + "." + filedName : filedName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.touco.ccmp.dao.base.IGenericDao#update(java.lang.Object)
	 */
	public T update(T object) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("object(T)" + object);
		}
		getHibernateTemplate().update(object);
		getHibernateTemplate().flush();
		return object;
	}

	/**
	 * 生成树形结构主键ID(non-Javadoc)
	 * 
	 * @see com.touco.ccmp.dao.base.IGenericDao#generatorTreeID(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String generatorTreeID(String entityName, String parentid, String idFieldName) {
		return generatorTreeID(entityName, parentid, idFieldName, "parentId");
	}

	/**
	 * @param entityName
	 * @param parentid
	 * @param idFieldName
	 * @param parentFieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String generatorTreeID(String entityName, String parentid, String idFieldName, String parentFieldName) {
		int i = 1;
		i = "0".equals(parentid) ? 0 : i;
		String hsqlstr = "select max(substring(e." + idFieldName + "," + (parentid.length() + i) + ",4)) as tt from " + entityName + " as e where e." + parentFieldName + "='" + parentid + "'";

		Object modno = "1";
		List<T> result = null;
		try {
			result = getHibernateTemplate().find(hsqlstr);
		} catch (Exception e) {
		}
		if (result != null && result.size() > 0) {
			if (result.get(0) != null && !result.get(0).toString().equals("")) {
				modno = result.get(0).toString();
			}
		}
		modno = String.valueOf(new Integer(modno.toString()).intValue() + 1);
		return (parentid.equals("0") ? "" : parentid) + genTreeNo(modno.toString());
	}

	/**
	 * @param maxTreeNo
	 * @return
	 */
	private static String genTreeNo(String maxTreeNo) {
		if (maxTreeNo.length() > 4)
			return maxTreeNo;
		String tmp = "";
		for (int i = 0; i < 4 - maxTreeNo.toCharArray().length; i++) {
			tmp += "0";
		}
		return tmp.concat(maxTreeNo);
	}

	/**
	 * 批量更新 shizhongying
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void updateII(List<T> entity) throws Exception {
		for (int i = 0; i < entity.size(); i++) {
			this.update((T) entity.get(i));
		}
	}

	@SuppressWarnings({ "unchecked" })
	public List<T> getListByCriteria(DataGridModel dgm, Map<String, Map<String, Object>> queryParams) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		setCriteria(criteria, queryParams, null);

		if (dgm != null && dgm.getOrder() != null) {
			if (dgm.getOrder().equals(Constants.PAGE_ORDER_ASC)) {
				criteria.addOrder(Order.asc(dgm.getSort()));
			} else if (dgm.getOrder().equals(Constants.PAGE_ORDER_DESC)) {
				criteria.addOrder(Order.desc(dgm.getSort()));
			}
		}

		List<T> list = getHibernateTemplate().findByCriteria(criteria, (dgm.getPage() - 1) * dgm.getRows(), dgm.getRows());

		return list;
	}

}
