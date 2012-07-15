package com.voucher.entity.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	 * 密码 DigestUtils.md5Hex(DigestUtils.md5Hex(password){account})
	 */
	private String password;

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

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 
	 */
	public SysUser() {
	}

	/**
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
	 * @param remark
	 */
	public SysUser(String account, String password, String realName, Short sex,
			String email, String mobile, String officePhone, String remark) {
		this.account = account;
		this.password = password;
		this.realName = realName;
		this.sex = sex;
		this.email = email;
		this.mobile = mobile;
		this.officePhone = officePhone;
		this.remark = remark;
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
}