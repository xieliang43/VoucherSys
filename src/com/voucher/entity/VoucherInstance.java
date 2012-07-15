package com.voucher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_VOUCHER_INSTANCE")
public class VoucherInstance {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="V_KEY")
	private String vKey;
	@ManyToOne
	@JoinColumn(name="VOUCHER_ID")
	private Voucher voucher;
	@Column(name="IS_BOUGHT")
	private short isBought;
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
	 * @return the voucher
	 */
	public Voucher getVoucher() {
		return voucher;
	}
	/**
	 * @param voucher the voucher to set
	 */
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
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
	/**
	 * @return the vKey
	 */
	public String getvKey() {
		return vKey;
	}
	/**
	 * @param vKey the vKey to set
	 */
	public void setvKey(String vKey) {
		this.vKey = vKey;
	}
}
