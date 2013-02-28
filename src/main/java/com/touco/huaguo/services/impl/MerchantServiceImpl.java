package com.touco.huaguo.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.dao.IMerchantDao;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.services.IMerchantService;
import com.touco.huaguo.services.base.impl.GenericServiceImpl;

/**
 * 
 * @author 史中营
 * 
 */
@Service("merchantService")
public class MerchantServiceImpl extends GenericServiceImpl<MerchantEntity, Long> implements IMerchantService {

	private IMerchantDao merchantDao;

	@Autowired
	public void setMerchantDao(IMerchantDao merchantDao) {
		this.dao = merchantDao;
		this.merchantDao = merchantDao;
	}

	public boolean isMerchantExist(Long merchantId, String name) {

		return merchantDao.isMerchantExist(merchantId, name);
	}

	public boolean delMerchant(MerchantEntity merchant) {
		return merchantDao.delMerchant(merchant);
	}

	public boolean delMerchantList(List<Long> merchantList) {
		return merchantDao.delMerchantList(merchantList);
	}

	/**
	 * 查询店铺列表
	 * 
	 * @author 史中营
	 */
	public List<MerchantEntity> getMerchantList(DataGridModel dgm, Map<String, Map<String, Object>> queryParams) {
		StringBuffer where = new StringBuffer();
		Map<String, Object> equalsMap = queryParams.get(Constants.CRITERIA_EQUALS);

		where.append(" where merchantStatus='").append(Constants.MERCHANT_STATUS_PASS).append("'");
		if (equalsMap.get("recommendStatus") != null && StringUtils.isNotEmpty(equalsMap.get("recommendStatus").toString()) && !equalsMap.get("recommendStatus").equals("0")) {
			where.append(" and recommendStatus='").append(equalsMap.get("recommendStatus")).append("'");
		}
		where.append(" and cityName='").append(equalsMap.get("cityName")).append("'");

		Map<String, Object> likesMap = queryParams.get(Constants.CRITERIA_LIKE);
		if (likesMap.get("name") != null && StringUtils.isNotBlank(likesMap.get("name").toString())) {
			where.append(" and name like '%").append(likesMap.get("name")).append("%'");
		}
		if (likesMap.get("merchantStyle") != null && StringUtils.isNotBlank(likesMap.get("merchantStyle").toString())) {
			where.append(" and merchantStyle like '%").append(likesMap.get("merchantStyle")).append("%'");
		}
		if (likesMap.get("area") != null && StringUtils.isNotBlank(likesMap.get("area").toString())) {
			where.append(" and area like '%").append(likesMap.get("area")).append("%'");
		}

		if (StringUtils.isNotBlank(dgm.getSort())) {
			if (dgm.getSort().equals("viewNum")) {
				where.append(" order by viewNum/datediff(now(),createDate)");
			} else if (dgm.getSort().equals("supportNum")) {
				where.append(" order by supportNum/datediff(now(),createDate)");
			} else {
				where.append(" order by ").append(dgm.getSort());
			}
		}

		if (StringUtils.isNotBlank(dgm.getOrder())) {
			where.append(" ").append(dgm.getOrder());
		}

		if (dgm.getPage() != 0 && dgm.getRows() != 0) {
			where.append(" limit ").append((dgm.getPage() - 1) * dgm.getRows()).append(",").append(dgm.getRows());
		}
		return this.merchantDao.getMerchantList(where.toString());
	}
}
