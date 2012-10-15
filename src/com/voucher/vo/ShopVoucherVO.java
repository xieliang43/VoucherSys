package com.voucher.vo;

public class ShopVoucherVO {
	private int id;
	private String image;
	private String useRule;
	private String endDate;
	private double price;
	private String description;
	
	public ShopVoucherVO(){
		
	}
	
	/**
	 * @param id
	 * @param image
	 * @param useRule
	 * @param endDate
	 * @param price
	 * @param description
	 */
	public ShopVoucherVO(int id, String image, String useRule, String endDate,
			double price, String description) {
		this.id = id;
		this.image = image;
		this.useRule = useRule;
		this.endDate = endDate;
		this.price = price;
		this.description = description;
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
	 * @return the useRule
	 */
	public String getUseRule() {
		return useRule;
	}
	/**
	 * @param useRule the useRule to set
	 */
	public void setUseRule(String useRule) {
		this.useRule = useRule;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
}
