package com.touco.huaguo.services;

import java.util.List;
import java.util.Map;

import com.touco.huaguo.common.DataGridModel;
import com.touco.huaguo.domain.MerchantEntity;
import com.touco.huaguo.services.base.IGenericService;

/**
 * 
 * @author 史中营
 * 
 */
public interface IMerchantService extends IGenericService<MerchantEntity, Long> {
	/**
	 * 餐厅名称是否重复
	 **/
	public boolean isMerchantExist(Long merchantId, String name);

	/**
	 * 条件查询餐厅列表
	 * 
	 * @author 史中营
	 * @param dgm
	 * @param queryParams
	 * @return
	 */
	public List<MerchantEntity> getMerchantList(DataGridModel dgm, Map<String, Map<String, Object>> queryParams);

	public boolean delMerchant(MerchantEntity merchant);

	public boolean delMerchantList(List<Long> merchantList);
}
