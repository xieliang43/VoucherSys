package com.voucher.pojo;

public class AreaVO {
	private int id;
	private String name;
	private String cityPrefix;
	
	public AreaVO() {
		
	}
	/**
	 * @param id
	 * @param name
	 */
	public AreaVO(int id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * @param id
	 * @param name
	 * @param cityPrefix
	 */
	public AreaVO(int id, String name, String cityPrefix) {
		this.id = id;
		this.name = name;
		this.cityPrefix = cityPrefix;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the cityPrefix
	 */
	public String getCityPrefix() {
		return cityPrefix;
	}
	/**
	 * @param cityPrefix the cityPrefix to set
	 */
	public void setCityPrefix(String cityPrefix) {
		this.cityPrefix = cityPrefix;
	}
}
