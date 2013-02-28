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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Administrator 用户
 */
@Entity
@Table(name = "HUAGUO_USER_TBL")
public class UserEntity implements Serializable {
	// Fields
	private static final long serialVersionUID = -8032726951376542325L;

	private Long userId;

	private String email;

	private String password;

	private String nickName;// 昵称

	private Set<UserRef> userRefs = new HashSet<UserRef>();

	private Date createDate;

	private Date updateDate;

	private String regType;// 注册类型(0-站内注册,1-外部注册)

	private String imageUrl;// 头像

	private String gender;  //性别,m--男，f--女,n--未知  //跟微博同
	
	private String delTag; //1:禁用,0:启用   状态
	
	private String trueName;
	
	private String webPage;//个人主页
	
	private CityEntity province=null;

	private CityEntity city=null;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "email", length = 50, nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "nickName", length = 50)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Set<UserRef> getUserRefs() {
		return userRefs;
	}

	public void setUserRefs(Set<UserRef> userRefs) {
		this.userRefs = userRefs;
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

	@Column(name = "regType", length = 1)
	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	@Column(name = "imageUrl", length = 255)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(length = 1)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column(name = "delTag", length = 1)
	public String getDelTag() {
		return delTag;
	}

	public void setDelTag(String delTag) {
		this.delTag = delTag;
	}

	@Column(name = "trueName", length = 255)
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
	@Column(name = "webPage", length = 255)
	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "province_id", nullable = true)
	public CityEntity getProvince() {
		return province;
	}

	public void setProvince(CityEntity province) {
		this.province = province;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", nullable = true)
	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}
	
	
}
