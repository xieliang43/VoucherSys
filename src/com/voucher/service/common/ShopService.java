package com.voucher.service.common;

import java.util.List;
import java.util.Map;

import com.voucher.entity.common.Shop;
import com.voucher.entity.sys.SysUser;
import com.voucher.pojo.ExtPager;
import com.voucher.pojo.ShopPager;
import com.voucher.service.BaseService;
import com.voucher.vo.ExtShopVO;
import com.voucher.vo.ShopVO;

public interface ShopService extends BaseService<Shop> {
	public List<ShopVO> getShops();
	public Map<Integer, Shop> getShopsByPosition(int cityId, double longitude, double lattitude);
	public List<ShopVO> getCurrentMerchantShopsByShopName(ExtPager pager, SysUser merchant, String shopName);
	public int getTotalCount();
	public int getCurrentMerchantTotalCount(SysUser merchant);
	public Map<String, Object> getAllEnabledShopsByCurrentMerchant(SysUser merchant);
	public ExtShopVO getNearbyShops(ShopPager shopPager);
	public int getCurrentMerchantTotalCountByShopName(SysUser merchant,
			String shopName);
	public List<ShopVO> getShopsByShopName(ExtPager pager, String shopName);
	public int getTotalCountByShopName(String shopName);
	public List<Shop> getShopsByRegion(int rId);
	public void deleteShopById(int id);
	public Shop findShopById(int id);
}
