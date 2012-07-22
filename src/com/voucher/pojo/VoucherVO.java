package com.voucher.pojo;

import java.util.Date;


public class VoucherVO {
	private int id;
	private String name;
	private double price;
	private int quantity;
	private int restQty;
	private Date startDate;
	private Date endDate;
	private String deadTime;
	private short enabled;
	private String image;
	private String description;
	private Date createDate;
	private int shopId;
	
	public VoucherVO() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param price
	 * @param quantity
	 * @param restQty
	 * @param startDate
	 * @param endDate
	 * @param deadTime
	 * @param enabled
	 * @param image
	 * @param description
	 * @param createDate
	 * @param shopId
	 */
	public VoucherVO(int id, String name, double price, int quantity,
			int restQty, Date startDate, Date endDate, String deadTime,
			short enabled, String image, String description, Date createDate,
			int shopId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.restQty = restQty;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deadTime = deadTime;
		this.enabled = enabled;
		this.image = image;
		this.description = description;
		this.createDate = createDate;
		this.shopId = shopId;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the restQty
	 */
	public int getRestQty() {
		return restQty;
	}

	/**
	 * @param restQty the restQty to set
	 */
	public void setRestQty(int restQty) {
		this.restQty = restQty;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the deadTime
	 */
	public String getDeadTime() {
		return deadTime;
	}

	/**
	 * @param deadTime the deadTime to set
	 */
	public void setDeadTime(String deadTime) {
		this.deadTime = deadTime;
	}

	/**
	 * @return the enabled
	 */
	public short getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(short enabled) {
		this.enabled = enabled;
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
	 * @return the shopId
	 */
	public int getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
}
