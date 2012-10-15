package com.voucher.action.sys;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.action.BasePagerAction;
import com.voucher.entity.common.ShopType;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.common.ShopTypeService;


public class SysShopTypeAction extends BasePagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3681326786866136892L;
	
	private ShopTypeService shopTypeService;
	
	private String id;
	private String name;
	private String enabled;
	private String description;
	
	public void loadAll() {
		ExtPager pager = new ExtPager(limit, start, dir, sort);
		List<ShopType> list = shopTypeService.getShopTypesByName(pager, name);
		int total = shopTypeService.getTotalCount();
		
		this.sendExtGridReturn(new ExtGridReturn(total, list));
	}
	
	public void save() {
		if (StringUtils.isBlank(getName())) {
			sendExtReturn(new ExtReturn(FALSE, "名称不能为空！"));
			return;
		}
		ShopType shopType = new ShopType(name, Short.valueOf(enabled), description);
		if(StringUtils.isBlank(getId())) {
			shopType.setCreateDate(new Date());
			shopTypeService.save(shopType);
		} else {
			shopType.setId(Integer.valueOf(id));
			shopTypeService.update(shopType);
		}
		sendExtReturn(new ExtReturn(TRUE, "保存成功！"));
	}

	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(FALSE, "主键不能为空！"));
			return;
		}
		
		shopTypeService.deleteShopTypeById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(TRUE, "删除成功！"));
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

}
