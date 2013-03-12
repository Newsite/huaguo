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
 * @author shizhongying
 * 私信 
 */
@Entity
@Table(name = "HUAGUO_MESSAGE_TBL")
public class MessageEntity implements Serializable {
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
	
	private String othersDelTag;//其他人删除标志
	
	private String ownDelTag;//自己删除标志
	
	private String showDate;//
	
	private Long parentId;
	
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

	@Column(name = "parentId")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	@Column(name = "othersDelTag",length=1)
	public String getOthersDelTag() {
		return othersDelTag;
	}

	public void setOthersDelTag(String othersDelTag) {
		this.othersDelTag = othersDelTag;
	}

	@Column(name = "ownDelTag",length=1)
	public String getOwnDelTag() {
		return ownDelTag;
	}

	public void setOwnDelTag(String ownDelTag) {
		this.ownDelTag = ownDelTag;
	}

	
	
	
}
