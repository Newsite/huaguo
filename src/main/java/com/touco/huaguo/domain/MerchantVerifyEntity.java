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
 * @author Administrator
 * 餐厅审核
 */
@Entity
@Table(name = "HUAGUO_MERCHANT_VERIFY_TBL")
public class MerchantVerifyEntity implements Serializable {
	// Fields
	private static final long serialVersionUID = -802721951376542325L;

	private Long recordId;
	
	private String name;
	
	private String imageUrl;//餐厅图片
	
	private String description;
	
	private String address;
	
	private String tel;
	
	private UserEntity user;
	
	private String content;
	
	private String area;
	
	private String merchantStyle;//菜系
	
	private Long supportNum;
	
	private Long commentNum;
	
	private UserEntity createUser;
	
	private Date createDate;
	
	private UserEntity updateUser;
	
	private Date updateDate;
	
	private Float lat;
	
	private Float lng;
	
	private String cityName;// 写死
	
	
	private UserEntity verifyUser;//审核人
	
	private String merchantStatus;//审核状态   0-审核中,1-已通过,2-未通过,3-未提交审核
	
	private MerchantEntity merchant;//对应餐厅 
	
	private Date recordDate;//记录创建时间
	
	private String expendMoney;//人均消费
	
	
	private PriceRegionEntity priceRegion;
	
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
	@JoinColumn(name="owner_id")
	public UserEntity getUser() {
		return user;
	}

	
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	@Column(name = "name", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "imageUrl", length = 255)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Column(name = "description", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "address", length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "tel", length = 30)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name = "content", length = 5000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	@Column(name = "area", length = 255)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "merchantStyle", length = 100)
	public String getMerchantStyle() {
		return merchantStyle;
	}

	public void setMerchantStyle(String merchantStyle) {
		this.merchantStyle = merchantStyle;
	}
	
	@Column(name = "supportNum", length = 20)
	public Long getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Long supportNum) {
		this.supportNum = supportNum;
	}
	
	@Column(name = "commentNum", length = 20)
	public Long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)   
	@JoinColumn(name="createuser_id")
	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}
	
	@Column(name = "createDate")
	public Date getCreateDate() {
		return createDate;
	}
	
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)   
	@JoinColumn(name="updateuser_id")
	public UserEntity getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserEntity updateUser) {
		this.updateUser = updateUser;
	}
	
	@Column(name = "updateDate")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "lat")
	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}
	
	@Column(name = "lng")
	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}
	
	@Column(name = "cityName",length=20)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)   
	@JoinColumn(name="verifyuser_id")
	public UserEntity getVerifyUser() {
		return verifyUser;
	}
	
	public void setVerifyUser(UserEntity verifyUser) {
		this.verifyUser = verifyUser;
	}

	@Column(name = "merchantStatus",length=1)
	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)   
	@JoinColumn(name="merchant_id")
	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}
	
	
	@Column(name = "recordDate")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	@Column(name = "expendMoney",length=1)
	public String getExpendMoney() {
		return expendMoney;
	}

	public void setExpendMoney(String expendMoney) {
		this.expendMoney = expendMoney;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "price_id")
	public PriceRegionEntity getPriceRegion() {
		return priceRegion;
	}

	public void setPriceRegion(PriceRegionEntity priceRegion) {
		this.priceRegion = priceRegion;
	}
}
