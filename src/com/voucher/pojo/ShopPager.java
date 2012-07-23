package com.voucher.pojo;

/**
 * Shop的分页请求对象
 * 
 */
public class ShopPager {
	private Integer cityId;
	private Integer shopTypeId;
	private Integer areaId;
	private Integer limit;
	private Integer start;
	private Integer distance;
	private String keyword;
	private Double latitude;
	private Double longitude;
	
	public ShopPager(){
		
	}
	/**
	 * @param cityId
	 * @param shopTypeId
	 * @param areaId
	 * @param limit
	 * @param start
	 * @param distance
	 * @param keyword
	 * @param lattitude
	 * @param longitude
	 */
	public ShopPager(Integer cityId, Integer shopTypeId, Integer areaId,
			Integer limit, Integer start, Integer distance, String keyword,
			Double latitude, Double longitude) {
		this.cityId = cityId;
		this.shopTypeId = shopTypeId;
		this.areaId = areaId;
		this.limit = limit;
		this.start = start;
		this.distance = distance;
		this.keyword = keyword;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the shopTypeId
	 */
	public Integer getShopTypeId() {
		return shopTypeId;
	}
	/**
	 * @param shopTypeId the shopTypeId to set
	 */
	public void setShopTypeId(Integer shopTypeId) {
		this.shopTypeId = shopTypeId;
	}
	/**
	 * @return the areaId
	 */
	public Integer getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}
	/**
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the lattitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param lattitude the lattitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
