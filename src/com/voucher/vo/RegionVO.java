package com.voucher.vo;

import java.util.Date;

public class RegionVO {
	private int id;
	private String name;
	private int type;
	private int parentId;
	private String regionPrefix;
	private short enabled;
	private Date createDate;
	
	public RegionVO(){
		
	}
	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param parentName
	 * @param regionPrefix
	 * @param createDate
	 */
	public RegionVO(int id, String name, int type, int parentId,
			String regionPrefix, short enabled, Date createDate) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.parentId = parentId;
		this.regionPrefix = regionPrefix;
		this.setEnabled(enabled);
		this.createDate = createDate;
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
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the regionPrefix
	 */
	public String getRegionPrefix() {
		return regionPrefix;
	}
	/**
	 * @param regionPrefix the regionPrefix to set
	 */
	public void setRegionPrefix(String regionPrefix) {
		this.regionPrefix = regionPrefix;
	}
	/**
	 * @return the enabled
	 */
	public short getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(short enabled) {
		this.enabled = enabled;
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
}
