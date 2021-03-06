package com.voucher.action.common;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.voucher.action.BasePagerAction;
import com.voucher.constants.WebConstants;
import com.voucher.entity.common.Region;
import com.voucher.entity.common.Shop;
import com.voucher.entity.common.ShopType;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.common.RegionService;
import com.voucher.service.common.ShopService;
import com.voucher.service.common.ShopTypeService;
import com.voucher.util.JackJson;
import com.voucher.vo.AreaVO;
import com.voucher.vo.ShopVO;

public class CmnMerchantShopAction extends BasePagerAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8322966890273058929L;

	private String id;
	private String shopName;
	private String shopAddress;
	private String telNo;
	private String image;
	private String description;
	private String shopTypeId;
	private String cityId;
	private String areaId;
	
	private String []shopIds;
	
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	
	private ShopTypeService shopTypeService;
	private ShopService shopService;
	private RegionService regionService;

	private Map<String, Object> session;
	
	public String initShop() {
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			this.sendExtReturn(new ExtReturn(FALSE, "账号不能为空！"));
			return NONE;
		}
		Map<String, Object> shopTypeMap = shopTypeService.getAllEnabledShopTypes();
		Map<String, Object> shopCityMap = regionService.getAllEnabledCities();
		Map<String, Object> shopAreaMap = regionService.getAllEnabledDistricts();
		session.put("shopTypeMap", JackJson.fromObjectToJson(shopTypeMap));
		session.put("shopCityMap", JackJson.fromObjectToJson(shopCityMap));
		session.put("shopAreaMap", JackJson.fromObjectToJson(shopAreaMap));
		return SUCCESS;
	}
	
	public void loadAreaByCity() {
		if (StringUtils.isBlank(getCityId())) {
			sendExtReturn(new ExtReturn(FALSE, "城市不能为空！"));
			return;
		}
		List<AreaVO> list = regionService.getAreasByParent(Integer.valueOf(cityId));
		sendExtGridReturn(new ExtGridReturn(list.size(), list));
	}
	
	public void loadAll() {
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			this.sendExtReturn(new ExtReturn(FALSE, "账号不能为空！"));
			return;
		}
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<ShopVO> list = shopService.getCurrentMerchantShopsByShopName(pager, merchant, shopName);
		int total;
		if(StringUtils.isBlank(shopName)) {
			total = shopService.getCurrentMerchantTotalCount(merchant);
		} else {
			total = shopService.getCurrentMerchantTotalCountByShopName(merchant, shopName);
		}
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(getShopName())) {
			sendExtReturn(new ExtReturn(FALSE, "商店名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getShopTypeId())) {
			sendExtReturn(new ExtReturn(FALSE, "类型不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getCityId())) {
			sendExtReturn(new ExtReturn(FALSE, "城市不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getShopAddress())) {
			sendExtReturn(new ExtReturn(FALSE, "地址不能为空！"));
			return;
		}
		
		ShopType shopType = shopTypeService.findShopTypeById(Integer.valueOf(shopTypeId));
		Region city = regionService.findRegionById(Integer.valueOf(cityId));
		Region area = null;
		if (!StringUtils.isBlank(getAreaId())) {
			area = regionService.findRegionById(Integer.valueOf(areaId));
		}
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			sendExtReturn(new ExtReturn(FALSE, "用户不能为空！"));
			return;
		}
		Shop shop = new Shop(shopName, shopAddress, getTelNo(), description, shopType);
		
		if(StringUtils.isBlank(id)) {
			if(StringUtils.isBlank(uploadFileName)) {
				this.sendExtReturn(new ExtReturn(FALSE, "图片不能为空！"));
				return;
			}
			
			final String imageFileName = buildFileName(uploadFileName.trim());
			uploadShopImage(merchant, upload, imageFileName);
			shop.setImage(imageFileName);
			shop.setCreateDate(new Date());
			shop.setMerchant(merchant);
			shop.setCity(city);
			shop.setArea(area);
			shopService.save(shop);
		} else {
			Shop oldShop = shopService.findById(Shop.class, Integer.valueOf(id));
			if(oldShop == null) {
				sendExtReturn(new ExtReturn(FALSE, "商店不能为空！"));
				return;
			}
			if(StringUtils.isBlank(uploadFileName)) {
				shop.setImage(oldShop.getImage());
			} else {
				final String imageFileName = buildFileName(uploadFileName.trim());
				shop.setImage(imageFileName);
				uploadShopImage(merchant, upload, imageFileName);
				deleteShopImage(merchant, oldShop.getImage());
			}
			if(area == null) {
				shop.setArea(oldShop.getArea());
			} else {
				shop.setArea(area);
			}
			shop.setId(Integer.valueOf(id));
			shop.setCity(city);
			shopService.update(shop);
		}
		sendExtReturn(new ExtReturn(TRUE, "保存成功！"));
	}
	
	public void delete() {
		if(shopIds == null || shopIds.length == 0) {
			sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		for(String shopId : shopIds) {
			if (!StringUtils.isBlank(shopId)) {
				shopService.deleteShopById(Integer.valueOf(shopId));
			}
		}
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
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

	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * @return the upload
	 */
	public File getUpload() {
		return upload;
	}

	/**
	 * @param upload the upload to set
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * @return the uploadContentType
	 */
	public String getUploadContentType() {
		return uploadContentType;
	}

	/**
	 * @param uploadContentType the uploadContentType to set
	 */
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	/**
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @return the shopIds
	 */
	public String [] getShopIds() {
		return shopIds;
	}

	/**
	 * @param shopIds the shopIds to set
	 */
	public void setShopIds(String [] shopIds) {
		this.shopIds = shopIds;
	}
}
