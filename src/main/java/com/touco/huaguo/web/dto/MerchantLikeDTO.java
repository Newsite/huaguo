package com.touco.huaguo.web.dto;

public class MerchantLikeDTO 
{
	private Long recordId;
	
	private String merchantName;
	
	private String merchantStyle;//菜系
	
	private Long supportNum;//喜欢人数
	
	private String isNew;//是否有更新
	
	private String dateShow;//更新时间
	
	private String imageUrl;//餐厅图片
	
	private Long merchantId;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantStyle() {
		return merchantStyle;
	}

	public void setMerchantStyle(String merchantStyle) {
		this.merchantStyle = merchantStyle;
	}

	public Long getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(Long supportNum) {
		this.supportNum = supportNum;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getDateShow() {
		return dateShow;
	}

	public void setDateShow(String dateShow) {
		this.dateShow = dateShow;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}	

	
	
}
