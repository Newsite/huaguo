/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.domain.sysmodule.UserEntity.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2011-12-12下午02:27:16
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 餐厅动态
 * 
 * @author Administrator
 */
@Entity
@Table(name = "HUAGUO_MERCHANT_EVENT_TBL")
public class MerchantEventEntity implements Serializable {
	// Fields
	private static final long serialVersionUID = -802721951376542325L;

	private Long recordId;

	private MerchantEntity merchant;// 对应餐厅

	private String eventType;// 动态类型:0-店长说(餐厅动态),1-团购优惠,2-促销活动

	private String content;

	private String eventlink;

	private UserEntity createUser;

	private Date createDate;
	
	private Date verifyDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;// 审核状态 0-审核中,1-已通过,2-未通过,3-未提交审核

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recordId")
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "merchant_id")
	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	@Column(name = "eventType", length = 1)
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Column(name = "content", length = 500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "eventlink", length = 255)
	public String getEventlink() {
		return eventlink;
	}

	public void setEventlink(String eventlink) {
		this.eventlink = eventlink;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "createuser_id")
	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}

	@Column(name = "createDate", length = 255)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "verifyDate", length = 255)
	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	@Column
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "status", length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
