package com.voucher.vo;

public class UserVO {
	private int id;
	private String token;
	private String phoneNo;
	
	/**
	 * @param resultCode
	 * @param resultInfo
	 * @param id
	 * @param token
	 */
	public UserVO(int id, String phoneNo, String token) {
		this.id = id;
		this.phoneNo = phoneNo;
		this.token = token;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	
	
}
