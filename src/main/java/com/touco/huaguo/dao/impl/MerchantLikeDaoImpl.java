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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.touco.huaguo.dao.IMerchantLikeDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.MerchantLikeEntity;

/**
 * @author baozhenyu
 * 餐厅喜欢Dao实现
 *
 */
@Repository("merchantLikeDao")
public class MerchantLikeDaoImpl extends GenericDaoImpl<MerchantLikeEntity, Long>
		implements IMerchantLikeDao {

	
	public MerchantLikeDaoImpl() {
		super(MerchantLikeEntity.class);
	}

	public boolean delMerchantLike(Long recordId) {
		boolean result = false;
		Session session = null;
		Transaction tx = null;			
		try {			
				session = getHibernateTemplate().getSessionFactory().openSession();//得到session
				tx = session.beginTransaction(); //打开事务（针对读数据库）
				MerchantLikeEntity likeEntity = (MerchantLikeEntity)session.get(com.touco.huaguo.domain.MerchantLikeEntity.class,recordId);
				Long merchantId = likeEntity.getMerchant().getMerchantId();
				session.delete(likeEntity);//删除数据库记录
				String updatesql = "update MerchantEntity set supportNum=supportNum-1 where merchantId="+merchantId;
				session.createQuery(updatesql).executeUpdate();				
				tx.commit();//提交事务 
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();//回滚事务
		}finally{
			session.close(); //关闭session 
		}
		return result;
	}

}
