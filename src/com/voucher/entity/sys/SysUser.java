package com.voucher.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.voucher.entity.Shop;

/**
 * 用户表
 */
@Entity
@Table(name="T_SYS_USER")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * 账号
	 */
	private String account;

	/**
	 */
	private String password;
	
	@Column(name="EXPENSE_PASSWORD")
	private String expensePassword;

	/**
	 * 用户真实姓名
	 */
	@Column(name="REAL_NAME")
	private String realName;

	/**
	 * 性别 0:男 1:女
	 */
	private Short sex;

	/**
	 * 电子邮件地址
	 */
	private String email;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 办公电话
	 */
	@Column(name="OFFICE_PHONE")
	private String officePhone;

	/**
	 * 密码错误次数
	 */
	@Column(name="ERROR_COUNT")
	private Short errorCount;

	/**
	 * 上次登录时间
	 */
	@Column(name="LAST_LOGIN_TIME")
	private Date lastLoginTime;

	/**
	 * 上次登录IP地址
	 */
	@Column(name="LAST_LOGIN_IP")
	private String lastLoginIp;
	
	@Column(name="QQ_NO")
	private String qqNo;
	
	@OneToMany(mappedBy="merchant")
	private Set<Shop> shops = new HashSet<Shop>();
	
	@Column(name="CITY_ID")
	private int cityId;

	/**
	 * 备注
	 */
	private String remark;
	
	@Column(name="CREATE_DATE")
	private Date createDate;

	/**
	 * 
	 */
	public SysUser() {
	}

	/**
	 * 
	 */
	public SysUser(String account, String password, String expensePassword, String realName, Short sex,
			String email, String mobile, String officePhone, String qqNo, String remark) {
		this.account = account;
		this.password = password;
		this.expensePassword = expensePassword;
		this.realName = realName;
		this.sex = sex;
		this.email = email;
		this.mobile = mobile;
		this.officePhone = officePhone;
		this.qqNo = qqNo;
		this.remark = remark;
		this.errorCount = 0;
	}

	/**
	 * @return 用户ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param userId
	 *            用户ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 账号
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return 密码 DigestUtils.md5Hex(DigestUtils.md5Hex(password){account})
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            密码 DigestUtils.md5Hex(DigestUtils.md5Hex(password){account})
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 用户真实姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            用户真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return 性别 0:男 1:女
	 */
	public Short getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            性别 0:男 1:女
	 */
	public void setSex(Short sex) {
		this.sex = sex;
	}

	/**
	 * @return 电子邮件地址
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            电子邮件地址
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 办公电话
	 */
	public String getOfficePhone() {
		return officePhone;
	}

	/**
	 * @param officePhone
	 *            办公电话
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	/**
	 * @return 密码错误次数
	 */
	public Short getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount
	 *            密码错误次数
	 */
	public void setErrorCount(Short errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * @return 上次登录时间
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime
	 *            上次登录时间
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return 上次登录IP地址
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp
	 *            上次登录IP地址
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
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the expensePassword
	 */
	public String getExpensePassword() {
		return expensePassword;
	}

	/**
	 * @param expensePassword the expensePassword to set
	 */
	public void setExpensePassword(String expensePassword) {
		this.expensePassword = expensePassword;
	}
}