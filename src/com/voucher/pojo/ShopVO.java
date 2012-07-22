package com.voucher.pojo;

import java.util.Date;

public class ShopVO {
	private int id;
	private String shopName;
	private String shopAddress;
	private String image;
	private String description;
	private int shopTypeId;
	private Date createDate;
	private int distance;
	
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
	public ShopVO(int id, String shopName, String shopAddress, String image,
			String description, int shopTypeId, int distance, Date createDate) {
		super();
		this.id = id;
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.image = image;
		this.description = description;
		this.shopTypeId = shopTypeId;
		this.distance = distance;
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
}
