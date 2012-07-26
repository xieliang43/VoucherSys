package com.voucher.pojo;

import java.util.List;

public class ExtShopVO {
	private int total;
	private List<ShopVO> shops;

	public ExtShopVO(){}
	/**
	 * @param total
	 * @param shops
	 */
	public ExtShopVO(int total, List<ShopVO> shops) {
		this.total = total;
		this.shops = shops;
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
	 * @return the shops
	 */
	public List<ShopVO> getShops() {
		return shops;
	}
	/**
	 * @param shops the shops to set
	 */
	public void setShops(List<ShopVO> shops) {
		this.shops = shops;
	}
}
