package com.voucher.pojo;

import java.util.Date;

public class MerchantVO {
	private int id;
	private String account;
	private String password;
	private String realName;
	private Short sex;
	private String email;
	private String mobile;
	private String officePhone;
	private Short errorCount;
	private Date lastLoginTime;
	private String lastLoginIp;
	private String qqNo;
	private int cityId;
	private String remark;
	private Date createDate;
	
	public MerchantVO(){
		
	}
	
	/**
	 * @param id
	 * @param account
	 * @param password
	 * @param realName
	 * @param sex
	 * @param email
	 * @param mobile
	 * @param officePhone
	 * @param errorCount
	 * @param lastLoginTime
	 * @param lastLoginIp
	 * @param qqNo
	 * @param cityId
	 * @param remark
	 * @param createDate
	 */
	public MerchantVO(int id, String account, String password, String realName,
			Short sex, String email, String mobile, String officePhone,
			Short errorCount, Date lastLoginTime, String lastLoginIp,
			String qqNo, int cityId, String remark, Date createDate) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.realName = realName;
		this.sex = sex;
		this.email = email;
		this.mobile = mobile;
		this.officePhone = officePhone;
		this.errorCount = errorCount;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.qqNo = qqNo;
		this.cityId = cityId;
		this.remark = remark;
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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the sex
	 */
	public Short getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Short sex) {
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the officePhone
	 */
	public String getOfficePhone() {
		return officePhone;
	}

	/**
	 * @param officePhone the officePhone to set
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	/**
	 * @return the errorCount
	 */
	public Short getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount the errorCount to set
	 */
	public void setErrorCount(Short errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
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
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
