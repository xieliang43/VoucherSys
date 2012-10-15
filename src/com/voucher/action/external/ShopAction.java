package com.voucher.action.external;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BaseAction;
import com.voucher.entity.common.Distance;
import com.voucher.entity.common.Region;
import com.voucher.entity.common.ShopType;
import com.voucher.pojo.ShopPager;
import com.voucher.service.common.DistanceService;
import com.voucher.service.common.RegionService;
import com.voucher.service.common.ShopService;
import com.voucher.service.common.ShopTypeService;
import com.voucher.service.common.VoucherService;
import com.voucher.vo.AreaVO;
import com.voucher.vo.ExtShopVO;
import com.voucher.vo.JsonVO;
import com.voucher.vo.ShopVoucherInstanceVO;
import com.voucher.vo.VchInstVO;

public class ShopAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7881853394576176768L;

	private static final String NO_LIMIT = "不限";
	
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
			JsonVO jErrorVO = new JsonVO(ZERO, "系统错误，未载入商业类型！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		JsonVO jVO = new JsonVO(ONE, "商业类型", shopTypes);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}

	public void getShopAreas() throws IOException {
		if (StringUtils.isBlank(cityId)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "请输入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<Region> regions = regionService.findRegionsByParentAndType(
				Integer.valueOf(cityId), 3);
		if (null == regions || regions.isEmpty()) {
			JsonVO jErrorVO = new JsonVO(ZERO, "系统错误，未载入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<AreaVO> areas = new ArrayList<AreaVO>();
		AreaVO first = new AreaVO(0, "不限");
		areas.add(first);
		for (Region r : regions) {
			AreaVO area = new AreaVO(r.getId(), r.getName());
			areas.add(area);
		}
		JsonVO jVO = new JsonVO(ONE, "地区列表", areas);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}

	public void getShops() {
		if (StringUtils.isBlank(cityId) && StringUtils.isBlank(areaId)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "无地区输入!", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		ShopPager shopPager = new ShopPager();
		if(!StringUtils.isBlank(cityId)) {
			shopPager.setCityId(Integer.valueOf(cityId));
		}
		if(!StringUtils.isBlank(shopTypeId) && !ZERO.equals(shopTypeId)) {
			shopPager.setShopTypeId(Integer.valueOf(shopTypeId));
		}
		if(!StringUtils.isBlank(areaId) && !ZERO.equals(areaId)) {
			shopPager.setAreaId(Integer.valueOf(areaId));
		}
		if(!StringUtils.isBlank(limit)) {
			shopPager.setLimit(Integer.valueOf(limit));
		}
		if(!StringUtils.isBlank(start)) {
			shopPager.setStart(Integer.valueOf(start));
		}
		if(!StringUtils.isBlank(distance)){
			if(distance.equals(NO_LIMIT)) {
				shopPager.setDistance(Integer.MAX_VALUE);
			} else {
				shopPager.setDistance(Integer.valueOf(distance));
			}
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
		JsonVO jVO = new JsonVO(ONE, "商铺列表", extShopVO);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}

	public void getShopDistances() throws IOException {
		List<Distance> list = distanceService.getEnabledDistances();

		JsonVO jVO = new JsonVO(ONE, "距离", list);
		String json = this.convertToJson(jVO);
		this.sendJSonReturn(json);
	}
	
	public void getVouchers2() {
		if (StringUtils.isBlank(shopId)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "商店不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<ShopVoucherInstanceVO> vouchers = voucherService.getEnabledShopVouchersByShop(Integer.valueOf(shopId));
		JsonVO jVO = new JsonVO(ONE, "代金券信息", vouchers);
		String json = this.convertToJson(jVO);
		this.sendJSonReturn(json);
	}
	
	public void getVouchers() {
		if (StringUtils.isBlank(shopId)) {
			JsonVO jErrorVO = new JsonVO(ZERO, "商店不能为空！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		VchInstVO vchs = voucherService.getEnabledVouchersByShop(Integer.valueOf(shopId));
		JsonVO jVO = new JsonVO(ONE, "代金券信息", vchs);
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
