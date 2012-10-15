package com.voucher.vo;

import java.util.Date;

public class ShopVO {
	private int id;
	private String shopName;
	private String merchantName;
	private String shopAddress;
	private String image;
	private String telNo;
	private String description;
	private int shopTypeId;
	private Date createDate;
	private int distance;
	private int cityId;
	private int areaId;
	
	public ShopVO(){
		
	}
	/**
	 * @param id
	 * @param shopName
	 * @param shopAddress
	 * @param image
	 * @param description
	 * @param shopTypeId
	 * @param createDate
	 */
	public ShopVO(int id, String shopName, String merchantName, String shopAddress, String telNo, String image,
			String description, int shopTypeId, int distance, int cityId, int areaId, Date createDate) {
		super();
		this.id = id;
		this.shopName = shopName;
		this.merchantName = merchantName;
		this.shopAddress = shopAddress;
		this.telNo = telNo;
		this.image = image;
		this.description = description;
		this.shopTypeId = shopTypeId;
		this.distance = distance;
		this.setCityId(cityId);
		this.setAreaId(areaId);
		this.createDate = createDate;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * @return the shopAddress
	 */
	public String getShopAddress() {
		return shopAddress;
	}
	/**
	 * @param shopAddress the shopAddress to set
	 */
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the shopTypeId
	 */
	public int getShopTypeId() {
		return shopTypeId;
	}
	/**
	 * @param shopTypeId the shopTypeId to set
	 */
	public void setShopTypeId(int shopTypeId) {
		this.shopTypeId = shopTypeId;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the areaId
	 */
	public int getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}
	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
