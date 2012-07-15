package com.voucher.pojo;

public class ObjectTokenVO {
	private String token;
	private Object object;
	private int userId;
	private int shopId;
	private int cityId;
	/**
	 * @param token
	 * @param object
	 */
	public ObjectTokenVO(String token, Object object) {
		this.token = token;
		this.object = object;
	}
	/**
	 * @param token
	 * @param userId
	 * @param cityId
	 */
	public ObjectTokenVO(String token, int userId, int cityId) {
		this.token = token;
		this.userId = userId;
		this.cityId = cityId;
	}
	/**
	 * @param token
	 * @param userId
	 * @param shopId
	 * @param cityId
	 */
	public ObjectTokenVO(String token, int userId, int shopId, int cityId) {
		this.token = token;
		this.userId = userId;
		this.shopId = shopId;
		this.cityId = cityId;
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
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
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
	 * @return the shopId
	 */
	public int getShopId() {
		return shopId;
	}
	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
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
	
}
