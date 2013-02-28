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
import javax.persistence.Transient;

/**
 * @author Administrator
 * 通知
 */
@Entity
@Table(name = "HUAGUO_NOTIFICATIONS_TBL")
public class NotificationsEntity implements Serializable {
	// Fields
	private static final long serialVersionUID = -2031326951376542325L;

	private Long recordId;		
	
	private UserEntity sender;//发送者
	
	private UserEntity receiver;//接受者 当前登录用户
	
	private String content;
	
	private Date createDate;
	
	private Date updateDate;
	
	private String isRead;//0-未读，1-已读
	
	private String type ; // 0 -通知，  1-私信
	
	private String showDate;//
	
	private Long parentId;
	
	//以为部分为后台审核发布信息
	private String category; //0--餐厅信息审核  1-- 动态审核 2--掌柜审核 3 评价 4 掌柜收到评价
	private Long merchantId;
	
	private String merchantName;
	
	private String isVerify;//1 -- 通过 2--拒绝
	//评论类型:0-店长说(餐厅动态),1-团购优惠,2-促销活动,3-普通评论
	private String commentType;				
	
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
	@JoinColumn(name="sender_id")
	public UserEntity getSender() {
		return sender;
	}

	public void setSender(UserEntity sender) {
		this.sender = sender;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)   
	@JoinColumn(name="receiver_id")
	public UserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = receiver;
	}
	
	@Column(name = "content", length = 500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "createDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	@Column(name = "updateDate")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "isRead",length=1)
	public String getIsRead() {
		return isRead;
	}

	@Column(name = "isRead",length=1)
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return
	 */
	@Column(name = "type",length=1)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	@Column(name = "parentId",columnDefinition = "varchar(1) DEFAULT '0'")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	
	@Column(name = "category",length=1)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "merchant_id")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@Column(name = "merchant_name")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Column(name = "isVerify",length=1)
	public String getIsVerify() {
		return isVerify;
	}

	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}

	@Column(name = "commentType",length=1)
	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	
	
	
	
	
}
