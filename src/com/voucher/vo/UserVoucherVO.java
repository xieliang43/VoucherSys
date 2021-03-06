package com.voucher.vo;


public class UserVoucherVO {
	private int viId;
	private String shopName;
	private String image;
	private String vchNo;
	private String useRule;
	private String endDate;
	private double price;
	private short isUsed;
	private short isActive;
	
	public UserVoucherVO(){
		
	}
	/**
	 * @param viId
	 * @param image
	 * @param vchNo
	 * @param endDate
	 * @param price
	 * @param isBought
	 * @param isActive
	 */
	public UserVoucherVO(int viId, String shopName, String image, String vchNo, String useRule,
			String endDate, double price, short isUsed, short isActive) {
		this.viId = viId;
		this.shopName = shopName;
		this.image = image;
		this.vchNo = vchNo;
		this.useRule = useRule;
		this.endDate = endDate;
		this.price = price;
		this.isUsed = isUsed;
		this.isActive = isActive;
	}
	/**
	 * @return the viid
	 */
	public int getViId() {
		return viId;
	}
	/**
	 * @param viid the viid to set
	 */
	public void setViId(int viId) {
		this.viId = viId;
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
	 * @return the vchNo
	 */
	public String getVchNo() {
		return vchNo;
	}
	/**
	 * @param vchNo the vchNo to set
	 */
	public void setVchNo(String vchNo) {
		this.vchNo = vchNo;
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
	 * @return the isUsed
	 */
	public short getIsUsed() {
		return isUsed;
	}
	/**
	 * @param isUsed the isUsed to set
	 */
	public void setIsUsed(short isUsed) {
		this.isUsed = isUsed;
	}
	/**
	 * @return the isActive
	 */
	public short getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(short isActive) {
		this.isActive = isActive;
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
}
