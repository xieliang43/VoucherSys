package com.voucher.pojo;

public class ShopVoucherInstanceVO {
	private int total;
	private int rest;
	private ShopVoucherVO shopVoucherVO;
	
	public ShopVoucherInstanceVO(){}
	/**
	 * @param total
	 * @param rest
	 * @param shopVoucherVO
	 */
	public ShopVoucherInstanceVO(int total, int rest,
			ShopVoucherVO shopVoucherVO) {
		this.total = total;
		this.rest = rest;
		this.shopVoucherVO = shopVoucherVO;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the rest
	 */
	public int getRest() {
		return rest;
	}
	/**
	 * @param rest the rest to set
	 */
	public void setRest(int rest) {
		this.rest = rest;
	}
	/**
	 * @return the shopVoucherVO
	 */
	public ShopVoucherVO getShopVoucherVO() {
		return shopVoucherVO;
	}
	/**
	 * @param shopVoucherVO the shopVoucherVO to set
	 */
	public void setShopVoucherVO(ShopVoucherVO shopVoucherVO) {
		this.shopVoucherVO = shopVoucherVO;
	}
}
