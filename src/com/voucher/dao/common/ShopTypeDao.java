package com.voucher.dao.common;

import java.util.List;

import com.voucher.dao.BaseDao;
import com.voucher.entity.common.ShopType;
import com.voucher.pojo.ExtPager;

public interface ShopTypeDao extends BaseDao<ShopType> {
	public List<ShopType> getShopTypes(ExtPager pager);
	public List<ShopType> getShopTypesByName(ExtPager pager, String name);
	public List<ShopType> getEnabledShopTypes();
}
