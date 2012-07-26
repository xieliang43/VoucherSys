package com.voucher.pojo;


public class VoucherInstanceVO {
	private int viid;
	private String image;
	private String vchNo;
	private String endDate;
	private double price;
	private short isBought;
	
	public VoucherInstanceVO(){}
	/**
	 * @param viid
	 * @param image
	 * @param vchNo
	 * @param endDate
	 * @param price
	 * @param isBought
	 */
	public VoucherInstanceVO(int viid, String image, String vchNo,
			String endDate, double price, short isBought) {
		super();
		this.viid = viid;
		this.image = image;
		this.vchNo = vchNo;
		this.endDate = endDate;
		this.price = price;
		this.isBought = isBought;
	}
	/**
	 * @return the viid
	 */
	public int getViid() {
		return viid;
	}
	/**
	 * @param viid the viid to set
	 */
	public void setViid(int viid) {
		this.viid = viid;
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
	 * @return the isBought
	 */
	public short getIsBought() {
		return isBought;
	}
	/**
	 * @param isBought the isBought to set
	 */
	public void setIsBought(short isBought) {
		this.isBought = isBought;
	}
}
