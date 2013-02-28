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
 * 餐厅
 * 
 * @author Administrator
 */
@Entity
@Table(name = "HUAGUO_MERCHANT_TBL")
public class MerchantEntity implements Serializable {
	// Fields
	private static final long serialVersionUID = -802721951376542325L;

	private Long merchantId;

	private String name;

	private String imageUrl;// 餐厅图片

	private String description;// 简介(140字)

	private String address;// (35个字)

	private String tel;

	private UserEntity user;// 掌柜

	private String content;

	private String area;

	private String merchantStyle;// 菜系

	private Long supportNum = new Long(0);// 喜欢的人数

	private Long commentNum = new Long(0);// 评论数

	private UserEntity createUser;

	private Date createDate;

	private UserEntity updateUser;

	private Date updateDate; // 上线时间

	private Float lat;

	private Float lng;

	// private CityEntity city;// 用户城市站点切换

	private String cityName;// 写死

	private PriceRegionEntity priceRegion;

	private Long viewNum = new Long(0);// 喜欢的人数 ？？《答：浏览店铺的人数》

	private String recommendStatus = "0";// 是否推荐:0-否(默认),1-是
	private String merchantStatus = "0";// 审核状态 0-审核中,1-已通过,2-未通过 ,3--未提交审核

	private String isMerchantOwner;// 是否是掌柜

	private String isOpen = "0";// 是否已上线

	private Float perViewNum = new Float(0);// 店铺上线日起每天的浏览量

	private Integer promotionNum;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "merchantId")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "owner_id")
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

	@Column(name = "tel", length = 100)
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

	@Column(name = "supportNum", columnDefinition = "int DEFAULT 0")
	public Long getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Long supportNum) {
		this.supportNum = supportNum;
	}

	/**
	 * 评价数量
	 * 
	 * @return
	 */
	@Column(name = "commentNum", columnDefinition = "int DEFAULT 0")
	public Long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "createuser_id")
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
	@JoinColumn(name = "updateuser_id")
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

	// @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,
	// optional = true)
	// @JoinColumn(name = "city_id")
	// public CityEntity getCity() {
	// return city;
	// }
	//
	// public void setCity(CityEntity city) {
	// this.city = city;
	// }

	@Column
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "price_id")
	public PriceRegionEntity getPriceRegion() {
		return priceRegion;
	}

	public void setPriceRegion(PriceRegionEntity priceRegion) {
		this.priceRegion = priceRegion;
	}

	@Column(columnDefinition = "int DEFAULT 0")
	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

	@Column(columnDefinition = "varchar(1) DEFAULT '0'")
	public String getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}

	@Column(name = "merchantStatus", columnDefinition = "varchar(1) DEFAULT '0'")
	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	@Transient
	public String getIsMerchantOwner() {
		return isMerchantOwner;
	}

	public void setIsMerchantOwner(String isMerchantOwner) {
		this.isMerchantOwner = isMerchantOwner;
	}

	@Column(name = "isOpen", length = 1)
	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	@Transient
	public Float getPerViewNum() {
		return perViewNum;
	}

	public void setPerViewNum(Float perViewNum) {
		this.perViewNum = perViewNum;
	}

	@Transient
	public Integer getPromotionNum() {
		return promotionNum;
	}

	public void setPromotionNum(Integer promotionNum) {
		this.promotionNum = promotionNum;
	}

}
