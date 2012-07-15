package com.voucher.service;

import java.util.List;
import java.util.Map;

import com.voucher.entity.Shop;

public interface ShopService {
	public Shop findShopById(int shopId);
	public void update(Shop shop);
	public void save(Shop shop);
	public List<Shop> getShops();
	public Map<Integer, Shop> getShopsByPosition(int cityId, double longitude, double lattitude);
}
