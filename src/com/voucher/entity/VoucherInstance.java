package com.voucher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="T_VOUCHER_INSTANCE")
public class VoucherInstance {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="V_KEY")
	private String vchKey;
	@ManyToOne
	@JoinColumn(name="VOUCHER_ID")
	private Voucher voucher;
	@Column(name="IS_BOUGHT")
	private short isBought;
	@Version
	private int version;
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
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * @return the vchKey
	 */
	public String getVchKey() {
		return vchKey;
	}
	/**
	 * @param vchKey the vchKey to set
	 */
	public void setVchKey(String vchKey) {
		this.vchKey = vchKey;
	}
}
