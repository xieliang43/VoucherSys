package com.voucher.entity.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色表
 */
@Entity
@Table(name="T_SYS_ROLE")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * 角色名称
	 */
	@Column(name="ROLE_NAME")
	private String roleName;

	/**
	 * 角色描述
	 */
	@Column(name="ROLE_DESC")
	private String roleDesc;
	
	public SysRole(){
		
	}

	/**
	 * @param roleName
	 * @param roleDesc
	 */
	public SysRole(String roleName, String roleDesc) {
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}

	/**
	 * @return 角色ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param roleId
	 *            角色ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return 角色描述
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc
	 *            角色描述
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}