package com.voucher.dao;

import java.util.List;

import com.voucher.entity.ShopType;
import com.voucher.pojo.ExtPager;

public interface ShopTypeDao {
	public ShopType findShopTypeById(int shopTypeId);
	public List<ShopType> getShopTypes();
	public List<ShopType> getShopTypes(ExtPager pager);
	public List<ShopType> getShopTypesByName(ExtPager pager, String name);
	public void deleteById(int id);
	public void saveShopType(ShopType shopType);
	public void update(ShopType shopType);
	public List<ShopType> getEnabledShopTypes();
}
