package com.voucher.service.common;

import java.util.List;
import java.util.Map;

import com.voucher.entity.common.ShopType;
import com.voucher.pojo.ExtPager;
import com.voucher.service.BaseService;

public interface ShopTypeService extends BaseService<ShopType> {
	public List<ShopType> getShopTypes();
	public int getTotalCount();
	public List<ShopType> getShopTypesByName(ExtPager pager, String name);
	public Map<String, Object> getAllEnabledShopTypes();
	public void deleteShopTypeById(int id);
	public ShopType findShopTypeById(int id);
}
