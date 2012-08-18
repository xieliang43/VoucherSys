package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ShopPager;

public interface ShopDao {
	public Shop findShopById(int shopId);
	public void update(Shop shop);
	public void create(Shop shop);
	public List<Shop> getShops();
	public List<Shop> getCurrentMerchantShops(SysUser merchant);
	public List<Shop> getShopsByCityId(int cityId);
	public List<Shop> getAllShops(ExtPager pager, SysUser merchant);
	public List<Shop> getAllShopsByShopName(ExtPager pager, SysUser merchant, String shopName);
	public List<Shop> getShopsByShopPager(ShopPager shopPager);
	public int getTotalEnabledShops(ShopPager shopPager);
	public void deleteById(int id);
	public List<Shop> getCurrentMerchantShopsByShopName(SysUser merchant,
			String shopName);
	public List<Shop> getShopsByPager(ExtPager pager);
	public List<Shop> getShopsByShopName(ExtPager pager, String shopName);
	public List<Shop> getTotalCountByShopName(String shopName);
	public List<Shop> getShopsByAreaId(int rId);
}
