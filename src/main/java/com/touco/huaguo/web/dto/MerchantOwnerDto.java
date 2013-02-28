/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.dto.MerchantVerifyDto.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-27下午3:36:01
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.web.dto;

import java.io.Serializable;
import java.util.Date;

public class MerchantOwnerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2213906622528903877L;
	
	private Long ownerId; 
	
	private Long merchantId;

	private String nickName;// 昵称

	private String email;

	private String merchantName;

	private Date verifyDate;

	private String merchantStatus;// 审核状态 0-审核中,1-已通过,2-未通过

	private Date createDate; // 上线时间
	
	private String isPass;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	
}
