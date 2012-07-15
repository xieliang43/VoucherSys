package com.voucher.pojo;

public class RoleModuleVO {
	private int roleId;
	private int moduleId;
	
	public RoleModuleVO() {
		
	}
	/**
	 * @param roleId
	 * @param moduleId
	 */
	public RoleModuleVO(int roleId, int moduleId) {
		this.roleId = roleId;
		this.moduleId = moduleId;
	}
	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the moduleId
	 */
	public int getModuleId() {
		return moduleId;
	}
	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
}
