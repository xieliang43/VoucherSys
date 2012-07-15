package com.voucher.entity.sys;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户角色表
 */
@Entity
@Table(name="T_SYS_USER_ROLE")
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户角色ID
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * 用户ID
	 */
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private SysUser user;

	/**
	 * 角色ID
	 */
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private SysRole role;

	/**
	 * @return 用户角色ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param userRoleId
	 *            用户角色ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public SysUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(SysUser user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	public SysRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(SysRole role) {
		this.role = role;
	}
	
}