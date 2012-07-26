package com.voucher.service;

import java.util.List;
import java.util.Map;

import com.voucher.entity.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ExtShopVO;
import com.voucher.pojo.ShopPager;
import com.voucher.pojo.ShopVO;

public interface ShopService {
	public Shop findShopById(int shopId);
	public void update(Shop shop);
	public void save(Shop shop);
	public List<ShopVO> getShops();
	public Map<Integer, Shop> getShopsByPosition(int cityId, double longitude, double lattitude);
	public List<ShopVO> getCurrentMerchantShopsByShopName(ExtPager pager, SysUser merchant, String shopName);
	public int getTotalCount();
	public int getCurrentMerchantTotalCount(SysUser merchant);
	public Map<String, Object> getAllEnabledShopsByCurrentMerchant(SysUser merchant);
	public ExtShopVO getNearbyShops(ShopPager shopPager);
}
