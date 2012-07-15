package com.voucher.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_MERCHANT")
public class Merchant {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="PHONE_NO")
	private String phoneNo;
	private String password;
	private String name;
	private String sex;
	private String email;
	@Column(name="TEL_NO")
	private String telNo;
	@Column(name="QQ_NO")
	private String qqNo;
	@OneToMany(mappedBy="merchant")
	private Set<Shop> shops = new HashSet<Shop>();
	@OneToOne
	@JoinColumn(name="CITY_ID")
	private Region city;
	@Column(name="CREATE_DATE")
	private Date createDate;
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
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}
	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	/**
	 * @return the qqNo
	 */
	public String getQqNo() {
		return qqNo;
	}
	/**
	 * @param qqNo the qqNo to set
	 */
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	/**
	 * @return the city
	 */
	public Region getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(Region city) {
		this.city = city;
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
	 * @return the shops
	 */
	public Set<Shop> getShops() {
		return shops;
	}
	/**
	 * @param shops the shops to set
	 */
	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}
}
