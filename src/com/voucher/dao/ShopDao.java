package com.voucher.dao;

import java.util.List;

import com.voucher.entity.Shop;

public interface ShopDao {
	public Shop findShopById(int shopId);
	public void update(Shop shop);
	public void create(Shop shop);
	public List<Shop> getShops();
	public List<Shop> getShopsByCityId(int cityId);
}
