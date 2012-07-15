package com.voucher.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.voucher.entity.ShopType;
import com.voucher.pojo.ExtGridReturn;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtReturn;
import com.voucher.service.ShopTypeService;


public class ShopTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3681326786866136892L;
	
	private ShopTypeService shopTypeService;
	
	private int start;
	private int limit;
	/**
	 * 大写的ASC or DESC
	 */
	private String dir;
	/**
	 * 排序的字段
	 */
	private String sort;
	
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
			sendExtReturn(new ExtReturn(false, "名称不能为空！"));
			return;
		}
		ShopType shopType = new ShopType(name, Short.valueOf(enabled), description);
		if(StringUtils.isBlank(getId())) {
			shopType.setCreateDate(new Date());
			shopTypeService.saveShopType(shopType);
		} else {
			shopType.setId(Integer.valueOf(id));
			shopTypeService.update(shopType);
		}
	}

	public void delete() {
		if (StringUtils.isBlank(id)) {
			sendExtReturn(new ExtReturn(false, "主键不能为空！"));
			return;
		}
		
		shopTypeService.deleteById(Integer.valueOf(id));
		sendExtReturn(new ExtReturn(true, "删除成功！"));
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
