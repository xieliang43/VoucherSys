package com.voucher.pojo;

public class UserRoleVO {
	private int id;
	private int userId;
	private int roleId;
	/**
	 * 
	 */
	public UserRoleVO() {
	}
	/**
	 * @param id
	 * @param userId
	 * @param roleId
	 */
	public UserRoleVO(int id, int userId, int roleId) {
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
}
