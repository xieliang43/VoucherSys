package com.voucher.entity.sys;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色模块表
 */
@Entity
@Table(name="T_SYS_ROLE_MODULE")
public class SysRoleModule implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色模块ID
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * 角色ID
	 */
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private SysRole role;

	/**
	 * 模块ID
	 */
	@ManyToOne
	@JoinColumn(name="MODULE_ID")
	private SysModule module;

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

	/**
	 * @return the module
	 */
	public SysModule getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(SysModule module) {
		this.module = module;
	}

}