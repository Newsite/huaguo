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

public class MerchantDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2213906622528903877L;
	
	private Long verifyMerchantId; 
	
	private Long merchantId;
	
	

	private String nickName;// 昵称

	private String email;

	private String merchantName;

	private Date verifyDate;

	private String verifyStatus;// 审核状态 0-审核中,1-已通过,2-未通过

	private Date createDate; // 上线时间

	private String merchantStyle;// 菜系
	
	private String area;

	private String cityName;// 用户城市站点切换

	private Long supportNum;

	private Long viewNum;// 喜欢

	private String createUser;

	private String manager; // 掌柜
	
	private Long managerId;// 掌柜ID
	

	private Date onLineDate; // 上线时间

	private String recommendStatus;// 是否推荐:0-否(默认),1-是

	
	
	public Long getVerifyMerchantId() {
		return verifyMerchantId;
	}

	public void setVerifyMerchantId(Long verifyMerchantId) {
		this.verifyMerchantId = verifyMerchantId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getMerchantStyle() {
		return merchantStyle;
	}

	public void setMerchantStyle(String merchantStyle) {
		this.merchantStyle = merchantStyle;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Long supportNum) {
		this.supportNum = supportNum;
	}

	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Date getOnLineDate() {
		return onLineDate;
	}

	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}

	public String getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	
	

}
