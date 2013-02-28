package com.touco.huaguo.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.touco.huaguo.common.ExceptionUtil;
import com.touco.huaguo.dao.IMerchantDao;
import com.touco.huaguo.dao.base.impl.GenericDaoImpl;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.domain.UserEntity;

/**
 * 
 * @author 史中营
 * 
 */
@Repository("merchantDao")
public class MerchantDaoImpl extends GenericDaoImpl<MerchantEntity, Long> implements IMerchantDao {

	public MerchantDaoImpl() {
		super(MerchantEntity.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isMerchantExist(Long merchantId, String name) {
		boolean flag = false;
		String nameString = "";
		List<MerchantEntity> list = null;
		if (merchantId != null && !"".equals(merchantId.toString())) {
			nameString = "from MerchantEntity as u where u.merchantId!=? and u.name = ? ";
			list = getHibernateTemplate().find(nameString, merchantId, name);
		} else {
			nameString = "from MerchantEntity as u where u.name = ? ";
			list = getHibernateTemplate().find(nameString, name);
		}
		if (null != list && !list.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	public boolean delMerchant(MerchantEntity merchant) {
		boolean result = false;
		Session session = null;
		Transaction tx = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();// 得到session
			tx = session.beginTransaction(); // 打开事务（针对读数据库）
			MerchantEntity entity = (MerchantEntity) session.get(com.touco.huaguo.domain.MerchantEntity.class, merchant.getMerchantId());
			session.delete(entity);// 删除数据库记录
			String delverify = "delete from MerchantVerifyEntity where merchant.merchantId=" + merchant.getMerchantId();
			String delowner = "delete from MerchantOwnerEntity where merchant.merchantId=" + merchant.getMerchantId();
			String delliker = "delete from MerchantLikeEntity where merchant.merchantId=" + merchant.getMerchantId();
			String delevent = "delete from MerchantEventEntity where merchant.merchantId=" + merchant.getMerchantId();
			String delcomment = "delete from MerchantCommentEntity where merchant.merchantId=" + merchant.getMerchantId();
			session.createQuery(delverify).executeUpdate();
			session.createQuery(delowner).executeUpdate();
			session.createQuery(delliker).executeUpdate();
			session.createQuery(delevent).executeUpdate();
			session.createQuery(delcomment).executeUpdate();
			tx.commit();// 提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();// 回滚事务
		} finally {
			session.close(); // 关闭session
		}
		return result;
	}

	public boolean delMerchantList(List<Long> merchantList) {
		boolean result = false;
		Session session = null;
		Transaction tx = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();// 得到session
			tx = session.beginTransaction(); // 打开事务（针对读数据库）
			if (null != merchantList && !merchantList.isEmpty()) {
				for (Long merchantId : merchantList) {
					MerchantEntity entity = (MerchantEntity) session.get(com.touco.huaguo.domain.MerchantEntity.class, merchantId);
					session.delete(entity);// 删除数据库记录
					String delverify = "delete from MerchantVerifyEntity where merchant.merchantId=" + merchantId;
					String delowner = "delete from MerchantOwnerEntity where merchant.merchantId=" + merchantId;
					String delliker = "delete from MerchantLikeEntity where merchant.merchantId=" + merchantId;
					String delevent = "delete from MerchantEventEntity where merchant.merchantId=" + merchantId;
					String delcomment = "delete from MerchantCommentEntity where merchant.merchantId=" + merchantId;
					session.createQuery(delverify).executeUpdate();
					session.createQuery(delowner).executeUpdate();
					session.createQuery(delliker).executeUpdate();
					session.createQuery(delevent).executeUpdate();
					session.createQuery(delcomment).executeUpdate();
				}
			}

			tx.commit();// 提交事务
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();// 回滚事务
		} finally {
			session.close(); // 关闭session
		}
		return result;
	}

	/**
	 * 条件查询店铺列表
	 * 
	 * @author 史中营
	 * @param where
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MerchantEntity> getMerchantList(String where) {
		Session session = null;
		List<MerchantEntity> list = null;
		try {
			session = this.getHibernateTemplate().getSessionFactory().openSession();
			list = session.createSQLQuery("select * from HUAGUO_MERCHANT_TBL " + where).addEntity(MerchantEntity.class).list();
			//List<Map<String, Object>> tmplist = this.getSimpleJdbcTemplate().queryForList("select viewNum/datediff(now(),createDate) perViewNum,(select count(*) from HUAGUO_MERCHANT_EVENT_TBL where merchant_id=merchantid and (eventType='1' or eventType='2') and status='1' and endDate>now()) promotionNum from HUAGUO_MERCHANT_TBL " + where);
			List<Map<String, Object>> tmplist = this.getSimpleJdbcTemplate().queryForList("select (select count(*) from HUAGUO_MERCHANT_EVENT_TBL where merchant_id=merchantid and (eventType='1' or eventType='2') and status='1' and endDate>now()) promotionNum from HUAGUO_MERCHANT_TBL " + where);
			List<Map<String, Object>> popularlist = this.getSimpleJdbcTemplate().queryForList("select merchantId from HUAGUO_MERCHANT_TBL order by viewNum/datediff(now(),createDate) desc limit 0,10");
			if (CollectionUtils.isNotEmpty(tmplist)) {
				for (int i = 0; i < tmplist.size(); i++) {
					//list.get(i).setPerViewNum(Float.parseFloat((tmplist.get(i).get("perViewNum") == null ? "0" : tmplist.get(i).get("perViewNum").toString())));
					list.get(i).setPerViewNum(0F);
					for (int j = 0; j < popularlist.size(); j++) {
						if (list.get(i).getMerchantId() == Long.parseLong(popularlist.get(j).get("merchantId").toString())) {
							list.get(i).setPerViewNum(1F);
							break;
						}
					}
					list.get(i).setPromotionNum(Integer.parseInt((tmplist.get(i).get("promotionNum") == null ? "0" : tmplist.get(i).get("promotionNum").toString())));
				}
				for (MerchantEntity tm : list) {
					if (tm.getUser() == null || tm.getUser().getUserId() == 0) {
						tm.setUser(null);
					} else {
						tm.getUser().setUserRefs(null);
						UserEntity tue = new UserEntity();
						PropertyUtils.copyProperties(tue, tm.getUser());
						tue.setPassword(null);
						tm.setUser(tue);
					}
					tm.setCreateUser(null);
					tm.setUpdateUser(null);
				}
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionParse(e);
		} finally {
			session.disconnect();
			session.close();
		}
		return list;
	}

}
