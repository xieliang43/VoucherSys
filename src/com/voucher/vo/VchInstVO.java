package com.voucher.vo;

import java.util.List;

public class VchInstVO {
	private int total;
	private int rest;
	private List<VoucherInstanceVO> vouchers;
	
	public VchInstVO(){}
	
	/**
	 * @param total
	 * @param rest
	 * @param vouchers
	 */
	public VchInstVO(int total, int rest, List<VoucherInstanceVO> vouchers) {
		super();
		this.total = total;
		this.rest = rest;
		this.vouchers = vouchers;
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
	 * @return the vouchers
	 */
	public List<VoucherInstanceVO> getVouchers() {
		return vouchers;
	}
	/**
	 * @param vouchers the vouchers to set
	 */
	public void setVouchers(List<VoucherInstanceVO> vouchers) {
		this.vouchers = vouchers;
	} 
}
