/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.dao.impl.MessageDaoImpl.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-20上午11:28:44
 * <p>
 * 部门: 产品部
 * <p>
 */
/**
 * 
 */
package com.touco.huaguo.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.touco.huaguo.dao.ITokenDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.TokenEntity;

/**
 * @author baozhenyu
 * 密码找回Dao实现
 *
 */
@Repository("tokenDao")
public class TokenDaoImpl extends GenericDaoImpl<TokenEntity, Long>
		implements ITokenDao {

	
	public TokenDaoImpl() {
		super(TokenEntity.class);
	}


	public TokenEntity getTokenByString(String token)
	{
		TokenEntity entity = null;
		String  queryString= "from TokenEntity as u where u.tokenString = ? and u.isUse='0' order by u.createDate desc";
		List<TokenEntity> list = getHibernateTemplate().find(queryString, token);
		if(null!=list&&!list.isEmpty())
		{
			entity = list.get(0);
		}
		return entity;
	}


	@Override
	public boolean updateTokenUse(String email)
	{
		boolean success = false;
		Session session = null;
		Transaction tx = null;		
		try 
		{
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.getTransaction();
			tx.begin();
			String  queryString= "update TokenEntity as u set u.isUse='1' where u.email='"+email+"'";
			session.createQuery(queryString).executeUpdate();
			tx.commit();
			success = true;
		} catch (Exception e) {
			tx.rollback();
		} finally {
			session.close();
		}
		return success;
	}


	

}
