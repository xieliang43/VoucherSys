package com.voucher.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.constants.WebConstants;
import com.voucher.entity.Region;
import com.voucher.entity.Shop;
import com.voucher.entity.ShopType;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.AreaVO;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.ShopVO;
import com.voucher.service.RegionService;
import com.voucher.service.ShopService;
import com.voucher.service.ShopTypeService;
import com.voucher.util.JackJson;

public class MerchantShopAction extends BaseAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8322966890273058929L;

	private int start;
	private int limit;
	private String dir;
	private String sort;
	
	private String id;
	private String shopName;
	private String shopAddress;
	private String telNo;
	private String image;
	private String description;
	private String shopTypeId;
	private String cityId;
	private String areaId;
	
	private ShopTypeService shopTypeService;
	private ShopService shopService;
	private RegionService regionService;

	private Map<String, Object> session;
	
	public String initShop() {
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return NONE;
		}
		Map<String, Object> shopTypeMap = shopTypeService.getAllEnabledShopTypes();
		Map<String, Object> shopCityMap = regionService.getAllEnabledCities();
		Map<String, Object> shopAreaMap = regionService.getAllEnabledDistrictsByCity(merchant.getCityId());
		session.put("shopTypeMap", JackJson.fromObjectToJson(shopTypeMap));
		session.put("shopCityMap", JackJson.fromObjectToJson(shopCityMap));
		session.put("shopAreaMap", JackJson.fromObjectToJson(shopAreaMap));
		return SUCCESS;
	}
	
	public void loadAreaByCity() {
		if (StringUtils.isBlank(getCityId())) {
			sendExtReturn(new ExtReturn(false, "城市不能为空！"));
			return;
		}
		List<AreaVO> list = regionService.getAreasByParent(Integer.valueOf(cityId));
		sendExtGridReturn(new ExtGridReturn(list.size(), list));
	}
	
	public void loadAll() {
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return;
		}
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<ShopVO> list = shopService.getCurrentMerchantShopsByShopName(pager, merchant, shopName);
		int total = shopService.getCurrentMerchantTotalCount(merchant);
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(getShopName())) {
			sendExtReturn(new ExtReturn(false, "商店名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getShopTypeId())) {
			sendExtReturn(new ExtReturn(false, "类型不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getCityId())) {
			sendExtReturn(new ExtReturn(false, "城市不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getAreaId())) {
			sendExtReturn(new ExtReturn(false, "区不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getShopAddress())) {
			sendExtReturn(new ExtReturn(false, "地址不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getImage())) {
			sendExtReturn(new ExtReturn(false, "图片不能为空！"));
			return;
		}
		
		ShopType shopType = shopTypeService.getShopTypeById(Integer.valueOf(shopTypeId));
		Region city = regionService.getRegionById(Integer.valueOf(cityId));
		Region area = regionService.getRegionById(Integer.valueOf(areaId));
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		Shop shop = new Shop(shopName, shopAddress, image, telNo, description, shopType);
		
		if(StringUtils.isBlank(id)) {
			shop.setCreateDate(new Date());
			shop.setMerchant(merchant);
			shop.setCity(city);
			shop.setArea(area);
			shopService.save(shop);
		} else {
			shop.setId(Integer.valueOf(id));
			shop.setCity(city);
			shop.setArea(area);
			shopService.update(shop);
		}
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		shopService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the shopAddress
	 */
	public String getShopAddress() {
		return shopAddress;
	}

	/**
	 * @param shopAddress the shopAddress to set
	 */
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the shopTypeId
	 */
	public String getShopTypeId() {
		return shopTypeId;
	}

	/**
	 * @param shopTypeId the shopTypeId to set
	 */
	public void setShopTypeId(String shopTypeId) {
		this.shopTypeId = shopTypeId;
	}

	/**
	 * @return the shopTypeService
	 */
	public ShopTypeService getShopTypeService() {
		return shopTypeService;
	}

	/**
	 * @param shopTypeService the shopTypeService to set
	 */
	public void setShopTypeService(ShopTypeService shopTypeService) {
		this.shopTypeService = shopTypeService;
	}

	/**
	 * @return the shopService
	 */
	public ShopService getShopService() {
		return shopService;
	}

	/**
	 * @param shopService the shopService to set
	 */
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
