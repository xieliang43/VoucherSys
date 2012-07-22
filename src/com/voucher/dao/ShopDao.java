package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;

public interface ShopDao {
	public Shop findShopById(int shopId);
	public void update(Shop shop);
	public void create(Shop shop);
	public List<Shop> getShops();
	public List<Shop> getCurrentMerchantShops(SysUser merchant);
	public List<Shop> getShopsByCityId(int cityId);
	public List<Shop> getAllShops(ExtPager pager, SysUser merchant);
	public List<Shop> getAllShopsByShopName(ExtPager pager, SysUser merchant, String shopName);
}
