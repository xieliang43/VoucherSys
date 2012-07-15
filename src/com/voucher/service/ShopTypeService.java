package com.voucher.service;

import java.util.List;

import com.voucher.entity.ShopType;
import com.voucher.pojo.ExtPager;

public interface ShopTypeService {
	public ShopType findShopTypeById(int shopTypeId);
	public List<ShopType> getShopTypes();
	public int getTotalCount();
	public List<ShopType> getShopTypesByName(ExtPager pager, String name);
	public void deleteById(int id);
	public void saveShopType(ShopType shopType);
	public void update(ShopType shopType);
}
