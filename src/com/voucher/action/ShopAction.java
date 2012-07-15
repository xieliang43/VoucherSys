package com.voucher.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.Merchant;
import com.voucher.entity.Region;
import com.voucher.entity.Shop;
import com.voucher.entity.ShopType;
import com.voucher.pojo.AreaVO;
import com.voucher.pojo.JsonVO;
import com.voucher.pojo.ObjectTokenVO;
import com.voucher.service.MerchantService;
import com.voucher.service.RegionService;
import com.voucher.service.ShopService;
import com.voucher.service.ShopTypeService;
import com.voucher.util.Base64Encoder;
import com.voucher.util.MD5;


public class ShopAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7881853394576176768L;
	
	//merchant info
	private String phoneNo;
	private String password;
	private String cityId;
	private String qqNo;
	private String telNo;
	
	//shop info
	private String shopId;
	private String shopTypeId;
	private String areaId;
	private String shopName;
	private String shopAddress;
	private String description;
	private File shopLogo;
	
	private double longitude;
	private double lattitude;
	
	private String token;
	
	private MerchantService merchantService;
	private RegionService regionService;
	private ShopService shopService;
	private ShopTypeService shopTypeService;
	
	public void shopRegister() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(cityId)) {
			JsonVO jErrorVO = new JsonVO("0", "请选择城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		Merchant existMerchant = merchantService.findMerchantByPhoneNo(phoneNo);
		if(existMerchant != null) {
			JsonVO jErrorVO = new JsonVO("0", "此号码商家已注册！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String newPwd = MD5.getInstance().encrypt(password);
		Region city = regionService.getRegionById(Integer.valueOf(cityId));
		Merchant merchant = new Merchant();
		merchant.setPhoneNo(phoneNo);
		merchant.setPassword(newPwd);
		merchant.setCity(city);
		merchant.setQqNo(qqNo);
		merchantService.save(merchant);
		
		String tokenInfo = Base64Encoder.encode(merchant.getId() + merchant.getPhoneNo() + merchant.getPassword());
		// FIXME: shop id
		ObjectTokenVO utVO = new ObjectTokenVO(tokenInfo, merchant.getId(), 1, merchant.getCity().getId());
		JsonVO jVO = new JsonVO("1", "注册成功！", utVO);
		
		String json = this.convertToJson(jVO);
		getHttpServletResponse().getWriter().println(json);
	}

	public void updateShopInfo() throws IOException {
		Shop shop = shopService.findShopById(Integer.valueOf(shopId));
		if(shop == null) {
			this.sendErrorResoponse("Cannot find shop by id: " + shopId);
			return;
		}
		ShopType shopType = shopTypeService.findShopTypeById(Integer.valueOf(shopTypeId));
		if(shopType == null) {
			this.sendErrorResoponse("Cannot find ShopType by id: " + shopTypeId);
			return;
		}
		shop.setDescription(description);
		shop.setShopAddress(shopAddress);
		shop.setShopType(shopType);
		shop.setShopName(shopName);
		shop.setImage(shopLogo.getAbsolutePath());
		
		shopService.update(shop);
		Merchant merchant = shop.getMerchant();
		String tokenInfo = Base64Encoder.encode(merchant.getId() + merchant.getPhoneNo() + merchant.getPassword());
		ObjectTokenVO utVO = new ObjectTokenVO(tokenInfo, merchant.getId(), shop.getId(), merchant.getCity().getId());
		JsonVO jVO = new JsonVO("1", "Update shop Successfully", utVO);
		
		String json = this.convertToJson(jVO);
		getHttpServletResponse().getWriter().println(json);
	}
	
	public void merchantLogin() throws IOException {
		if(StringUtils.isBlank(phoneNo)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入电话号码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		if(StringUtils.isBlank(password)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入密码！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		Shop shop = shopService.findShopById(Integer.valueOf(shopId));
		if(shop == null) {
			JsonVO jErrorVO = new JsonVO("0", "电话或密码输入错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		ShopType shopType = shopTypeService.findShopTypeById(Integer.valueOf(shopTypeId));
		if(shopType == null) {
			JsonVO jErrorVO = new JsonVO("0", "未找到商业类型！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		} 
		Merchant merchant = merchantService.findMerchantByPhoneAndPassword(phoneNo, password);
		if(merchant == null) {
			JsonVO jErrorVO = new JsonVO("0", "电话或密码输入错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		String merchantInfo = merchant.getId() + merchant.getPhoneNo() + merchant.getPassword();
		String merchantTokenInfo = Base64Encoder.decode(merchantInfo);
		ObjectTokenVO utVO = new ObjectTokenVO(merchantTokenInfo, merchant.getId(), merchant.getCity().getId());
		JsonVO jVO = new JsonVO("1", "登陆成功！", utVO);
		
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void getShopTypes() throws IOException {
		List<ShopType> shopTypes = shopTypeService.getShopTypes();
		if(null == shopTypes || shopTypes.isEmpty()) {
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
		if(StringUtils.isBlank(cityId)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<Region> regions = regionService.findRegionsByParentAndType(Integer.valueOf(cityId), 3);
		if(null == regions || regions.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "系统错误，未载入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		List<AreaVO> areas = new ArrayList<AreaVO>();
		for(Region r : regions) {
			AreaVO area = new AreaVO(r.getId(), r.getName());
			areas.add(area);
		}
		JsonVO jVO = new JsonVO("1", "地区列表", areas);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void getShops() throws IOException {
		List<Shop> shops = shopService.getShops();
		if(null == shops || shops.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "系统错误，未载入商铺！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		JsonVO jVO = new JsonVO("1", "商铺列表", shops);
		String json = this.convertToJson(jVO);
		sendJSonReturn(json);
	}
	
	public void getShopDistances() throws IOException {
		if(StringUtils.isBlank(cityId)) {
			JsonVO jErrorVO = new JsonVO("0", "请输入城市！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		Map<Integer, Shop> map = shopService.getShopsByPosition(Integer.valueOf(cityId), longitude, lattitude);
		if(map == null || map.isEmpty()) {
			JsonVO jErrorVO = new JsonVO("0", "未知系统错误！", null);
			String json = this.convertToJson(jErrorVO);
			sendJSonReturn(json);
			return;
		}
		JsonVO jVO = new JsonVO("1", "地区距离", map);
		String json = this.convertToJson(jVO);
		getHttpServletResponse().getWriter().println(json);
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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the qqNo
	 */
	public String getQqNo() {
		return qqNo;
	}

	/**
	 * @param qqNo the qqNo to set
	 */
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
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
	 * @return the shopId
	 */
	public String getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the lattitude
	 */
	public double getLattitude() {
		return lattitude;
	}

	/**
	 * @param lattitude the lattitude to set
	 */
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
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
	 * @return the shopLogo
	 */
	public File getShopLogo() {
		return shopLogo;
	}

	/**
	 * @param shopLogo the shopLogo to set
	 */
	public void setShopLogo(File shopLogo) {
		this.shopLogo = shopLogo;
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
	 * @return the merchantService
	 */
	public MerchantService getMerchantService() {
		return merchantService;
	}

	/**
	 * @param merchantService the merchantService to set
	 */
	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
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
}
