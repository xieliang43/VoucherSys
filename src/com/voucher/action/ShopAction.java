package com.voucher.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.Distance;
import com.voucher.entity.Region;
import com.voucher.entity.ShopType;
import com.voucher.pojo.AreaVO;
import com.voucher.pojo.ExtShopVO;
import com.voucher.pojo.JsonVO;
import com.voucher.pojo.ShopPager;
import com.voucher.pojo.VchInstVO;
import com.voucher.service.DistanceService;
import com.voucher.service.RegionService;
import com.voucher.service.ShopService;
import com.voucher.service.ShopTypeService;
import com.voucher.service.VoucherService;

public class ShopAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7881853394576176768L;

	private String cityId;
	private String shopId;
	private String shopTypeId;
	private String areaId;
	private String shopName;
	private String keyword;
	private String distance;
	private String longitude;
	private String latitude;
	private String start;
	private String limit;

	private RegionService regionService;
	private ShopService shopService;
	private ShopTypeService shopTypeService;
	private DistanceService distanceService;
	private VoucherService voucherService;

	public void getShopTypes() throws IOException {
		List<ShopType> shopTypes = shopTypeService.getShopTypes();
		if (null == shopTypes || shopTypes.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "系统错误，未载入商业类型！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		JsonVO jVO = new JsonVO("1", "商业类型", shopTypes);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}

	public void getShopAreas() throws IOException {
		if (StringUtils.isBlank(cityId)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<Region> regions = regionService.findRegionsByParentAndType(
				Integer.valueOf(cityId), 3);
		if (null == regions || regions.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "系统错误，未载入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<AreaVO> areas = new ArrayList<AreaVO>();
		for (Region r : regions) {
			AreaVO area = new AreaVO(r.getId(), r.getName());
			areas.add(area);
		}
		JsonVO jVO = new JsonVO("1", "地区列表", areas);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}

	public void getShops() {
		if (StringUtils.isBlank(cityId) && StringUtils.isBlank(areaId)) {
			JsonVO jErrorVO = new JsonVO("0", "无地区输入!", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		ShopPager shopPager = new ShopPager();
		if(!StringUtils.isBlank(cityId)) {
			shopPager.setCityId(Integer.valueOf(cityId));
		}
		if(!StringUtils.isBlank(shopTypeId)) {
			shopPager.setShopTypeId(Integer.valueOf(shopTypeId));
		}
		if(!StringUtils.isBlank(areaId)) {
			shopPager.setAreaId(Integer.valueOf(areaId));
		}
		if(!StringUtils.isBlank(limit)) {
			shopPager.setLimit(Integer.valueOf(limit));
		}
		if(!StringUtils.isBlank(start)) {
			shopPager.setStart(Integer.valueOf(start));
		}
		if(!StringUtils.isBlank(distance)) {
			shopPager.setDistance(Integer.valueOf(distance));
		} else {
			shopPager.setDistance(Integer.MAX_VALUE);
		}
		if(!StringUtils.isBlank(keyword)) {
			shopPager.setKeyword(keyword);
		}
		if(!StringUtils.isBlank(latitude)) {
			shopPager.setLatitude(Double.valueOf(latitude));
		}
		if(!StringUtils.isBlank(longitude)) {
			shopPager.setLongitude(Double.valueOf(longitude));
		}
		
		ExtShopVO extShopVO = shopService.getNearbyShops(shopPager);
		JsonVO jVO = new JsonVO("1", "商铺列表", extShopVO);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}

	public void getShopDistances() throws IOException {
		List<Distance> list = distanceService.getEnabledDistances();

		JsonVO jVO = new JsonVO("1", "距离", list);
		String json = this.convertToJson(jVO);
		this.sendJSonReturn(json);
	}
	
	public void getVouchers() {
		if (StringUtils.isBlank(shopId)) {
			JsonVO jErrorVO = new JsonVO("0", "商店不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		VchInstVO vchs = voucherService.getEnabledVouchersByShop(Integer.valueOf(shopId));
		JsonVO jVO = new JsonVO("1", "代金券信息", vchs);
		String json = this.convertToJson(jVO);
		this.sendJSonReturn(json);
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the shopId
	 */
	public String getShopId() {
		return shopId;
	}

	/**
	 * @param shopId
	 *            the shopId to set
	 */
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the lattitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param lattitude
	 *            the lattitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the shopTypeId
	 */
	public String getShopTypeId() {
		return shopTypeId;
	}

	/**
	 * @param shopTypeId
	 *            the shopTypeId to set
	 */
	public void setShopTypeId(String shopTypeId) {
		this.shopTypeId = shopTypeId;
	}

	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService
	 *            the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * @return the shopService
	 */
	public ShopService getShopService() {
		return shopService;
	}

	/**
	 * @param shopService
	 *            the shopService to set
	 */
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	/**
	 * @return the shopTypeService
	 */
	public ShopTypeService getShopTypeService() {
		return shopTypeService;
	}

	/**
	 * @param shopTypeService
	 *            the shopTypeService to set
	 */
	public void setShopTypeService(ShopTypeService shopTypeService) {
		this.shopTypeService = shopTypeService;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * @return the distanceService
	 */
	public DistanceService getDistanceService() {
		return distanceService;
	}

	/**
	 * @param distanceService
	 *            the distanceService to set
	 */
	public void setDistanceService(DistanceService distanceService) {
		this.distanceService = distanceService;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the voucherService
	 */
	public VoucherService getVoucherService() {
		return voucherService;
	}

	/**
	 * @param voucherService the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
}
