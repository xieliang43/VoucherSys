package com.voucher.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.voucher.constants.WebConstants;
import com.voucher.entity.Shop;
import com.voucher.entity.Voucher;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.pojo.VoucherVO;
import com.voucher.service.ShopService;
import com.voucher.service.VoucherService;
import com.voucher.util.JackJson;

public class VoucherAction extends BaseAction implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3834209328822135526L;
	
	private int start;
	private int limit;
	private String dir;
	private String sort;
	
	private String id;
	private String name;
	private String price;
	private String quantity;
	private String startDate;
	private String endDate;
	private String deadTime;
	private String enabled;
	private String image;
	private String description;
	private String shopId;
	private String shopName;
	
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	
	private ShopService shopService;
	private VoucherService voucherService;
	
	private Map<String, Object> session;
	
	public String initVoucher() {
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return Action.NONE;
		}
		Map<String, Object> shopMap = getShopService().getAllEnabledShopsByCurrentMerchant(merchant);
		session.put("vchShopMap", JackJson.fromObjectToJson(shopMap));
		return SUCCESS;
	}

	public void loadAll() {
		SysUser merchant = (SysUser) session.get(WebConstants.CURRENT_USER);
		if(merchant == null) {
			this.sendExtReturn(new ExtReturn(false, "账号不能为空！"));
			return;
		}
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<VoucherVO> list = voucherService.getCurrentMerchantVouchersByShopName(pager, merchant, getShopName());
		int total = voucherService.getCurrentMerchantTotalCount(merchant);
		
		sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(name)) {
			this.sendExtReturn(new ExtReturn(false, "店名不能为空！"));
			return;
		}
		if (StringUtils.isBlank(price)) {
			this.sendExtReturn(new ExtReturn(false, "价格不能为空！"));
			return;
		}
		if (StringUtils.isBlank(quantity)) {
			this.sendExtReturn(new ExtReturn(false, "数量不能为空！"));
			return;
		}
		if (StringUtils.isBlank(startDate)) {
			this.sendExtReturn(new ExtReturn(false, "起始时间不能为空！"));
			return;
		}
		if (StringUtils.isBlank(endDate)) {
			this.sendExtReturn(new ExtReturn(false, "结束不能为空！"));
			return;
		}
		if (StringUtils.isBlank(shopId)) {
			this.sendExtReturn(new ExtReturn(false, "商店不能为空！"));
			return;
		}
		if (StringUtils.isBlank(getUploadFileName())) {
			this.sendExtReturn(new ExtReturn(false, "图像不能为空！"));
			return;
		}
		Voucher voucher = new Voucher(name, Double.valueOf(price), Integer.valueOf(quantity), new Date(startDate), new Date(endDate), deadTime, Short.valueOf(enabled), image, description);
		Shop shop = shopService.findShopById(Integer.valueOf(shopId));
		voucher.setShop(shop);
//		if(StringUtils.isBlank(id)) {
//			voucher.setCreateDate(new Date());
//			voucherService.saveVoucher(voucher);
//		} else {
//			voucher.setId(Integer.valueOf(id));
//			voucherService.updateVoucher(voucher);
//		}
		sendExtReturn(new ExtReturn(true, "保存成功！"));
	}
	
	public void delete() {
		if (StringUtils.isBlank(id)) {
			this.sendExtReturn(new ExtReturn(false, "ID不能为空！"));
			return;
		}
		voucherService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the deadTime
	 */
	public String getDeadTime() {
		return deadTime;
	}

	/**
	 * @param deadTime the deadTime to set
	 */
	public void setDeadTime(String deadTime) {
		this.deadTime = deadTime;
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
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
}
