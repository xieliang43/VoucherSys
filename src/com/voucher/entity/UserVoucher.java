package com.voucher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_USER_VOUCHER")
public class UserVoucher {
	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User user;
	@OneToOne
	@JoinColumn(name="VCH_INST_ID")
	private VoucherInstance voucherInstance;
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	public UserVoucher(){}
	/**
	 * @param user
	 * @param voucher
	 * @param createDate
	 */
	public UserVoucher(User user, VoucherInstance voucherInstance, Date createDate) {
		super();
		this.user = user;
		this.voucherInstance = voucherInstance;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the voucherInstance
	 */
	public VoucherInstance getVoucherInstance() {
		return voucherInstance;
	}
	/**
	 * @param voucherInstance the voucherInstance to set
	 */
	public void setVoucherInstance(VoucherInstance voucherInstance) {
		this.voucherInstance = voucherInstance;
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
}
